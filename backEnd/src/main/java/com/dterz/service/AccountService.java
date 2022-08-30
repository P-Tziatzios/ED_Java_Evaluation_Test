package com.dterz.service;

import com.dterz.dtos.AccountDTO;
import com.dterz.mappers.AccountMapper;
import com.dterz.model.Account;
import com.dterz.model.Transaction;
import com.dterz.model.TransanctionType;
import com.dterz.model.User;
import com.dterz.repositories.AccountRepository;
import com.dterz.repositories.TransactionsRepository;
import com.dterz.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper mapper;
    private final UserRepository userRepository;
    private final TransactionsRepository transactionsRepository;

    /**
     * Gets all Accounts currently in the System.
     * The parameter is optional and if provided the accounts get filtered by User
     *
     * @param pageRequest number of active page and number of items per page
     * @param username    the username
     * @return ResponseEntity<Map < String, Object>>
     */
    public Map<String, Object> getAll(PageRequest pageRequest, String username) {
        User user = userRepository.findByUserName(username);
        Page<Account> page;
        if (user != null) {
            page = accountRepository.findByUserId(user.getId(), pageRequest);
        } else {
            page = accountRepository.findAll(pageRequest);
        }
        List<Account> acountList = page.getContent();
        Map<String, Object> response = new HashMap<>();
        List<AccountDTO> accountDTOS = mapper.entityListToDTOList(acountList);
        for (AccountDTO accountDTO : accountDTOS) {
          calculateBalance(accountDTO, 0d);
        }
        response.put("accounts", accountDTOS);
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        return response;
    }

    /**
     * Calculates the current balance of an Account based on it transactions
     *
     * @param account the Account we need the Balance for
     * @param initialValue initial balance
     */
    private void calculateBalance(Object account, Double initialValue) {
      final long start = System.currentTimeMillis();
      double balance = Objects.isNull(initialValue) ? 0 : initialValue;
      List<Long> transactions = transactionsRepository.findAllIds();
      AccountDTO accountDTO = (AccountDTO) account;

      for (Long transactId : transactions) {
        Optional<Transaction> transaction =
            transactionsRepository.findById(transactId);

        if (transaction.isPresent() &&
            transaction.get().getAccount().getId() == accountDTO.getId()) {
          balance = calculateNetBalance(balance, transaction.get().getType(),
              transaction.get().getAmount().doubleValue());
        }
      }

      accountDTO.setCalculatedBalance(BigDecimal.valueOf(balance));
      log.info("calcBalance took {} ms for Account {}", (System.currentTimeMillis() - start),
          accountDTO.getId());
    }

    /**
     * Gets an Acount by its id
     *
     * @param accountId the id of the Account to get
     * @return AccountDTO
     */
    public AccountDTO getAccountById(long accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        AccountDTO accountDTO = mapper.entityToDto(account);
        calculateBalance(accountDTO, null);
        return accountDTO;
    }

    /**
     * Creates and returns a new empty Account Object
     *
     * @return AccountDTO
     */
    public AccountDTO draftAccount() {
        Account account = new Account();
        return mapper.entityToDto(account);
    }

    /**
     * Updates the Account with the data from the Front end
     *
     * @param accountDTO the updated Account
     * @return AccountDTO
     */
    public AccountDTO updateAccount(AccountDTO accountDTO) {
        Optional<Account> accountOpt = accountRepository.findById(accountDTO.getId());
        Account account;
        if (accountOpt.isPresent()) {
            account = accountOpt.get();
            mapper.dtoToEntity(accountDTO, account);
        } else {
            account = mapper.dtoToEntity(accountDTO);
        }
        accountRepository.save(account);
        return mapper.entityToDto(account);
    }

  /**
   * Retrieves all transactions based on account id
   *
   * @param accountId the accountId
   * @return List<Transaction>
   */
  public List<Transaction> getTransactionsByAccountId(long accountId) {
    Optional<Account> accountOpt = accountRepository.findById(accountId);
    Account account;
    List<Transaction> transactions = new ArrayList<>();
    if (accountOpt.isPresent()) {
      account = accountOpt.get();
      transactions = account.getTransactions();
    }
    return transactions;
  }

  /**
   * Calculates the remaining budget based on the transaction type and the initial budget
   *
   * @param initialBudget the starting budget
   * @param transanctionType the transaction type
   * @param amount the amount to be appended or deducted
   * @return the new calculated balance
   */
  private double calculateNetBalance(double initialBudget, TransanctionType transanctionType,
      double amount) {

    if (TransanctionType.INCOME.equals(transanctionType)) {
      initialBudget =  initialBudget + amount;
    } else if (TransanctionType.EXPENCE.equals(transanctionType)) {
      initialBudget =  initialBudget - amount;
    }
    return initialBudget;
  }
}
