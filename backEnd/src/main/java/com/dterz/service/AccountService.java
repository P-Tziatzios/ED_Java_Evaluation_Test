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

    ArrayList<Transaction> accountTransactions = new ArrayList<Transaction>();

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
            Calcbalance2(accountDTO, 0d);
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
     * @param Logarsmos    the Account we need the Balance for
     * @param initialValue initial balance
     */
    private void Calcbalance2(Object Logarsmos, Double initialValue) {
        final long start = System.currentTimeMillis();
        Double zero = BigDecimal.ZERO.doubleValue();
        List<Long> transaction = transactionsRepository.findAllIds();

        int next = 0;
        for (next = 0; next < transaction.size(); next++) {
            transactionsRepository.findById(transaction.get(next)).get();
            if (transactionsRepository.findById(transaction.get(next)).get().getAccount().getId() == ((AccountDTO) Logarsmos).getId())
                accountTransactions.add(transactionsRepository.findById(transaction.get(next)).get());
        }
        for (int y = 0; y < accountTransactions.size(); y++) {
            if (TransanctionType.INCOME.equals(accountTransactions.get(y).getType())) {
                zero = zero + accountTransactions.get(y).getAmount().doubleValue();
            }
        }
        for (int x = 0; x < accountTransactions.size(); x++)
            if (TransanctionType.EXPENCE.equals(accountTransactions.get(x).getType())) {
                zero = zero - accountTransactions.get(x).getAmount().doubleValue();
            }
        accountTransactions.sort(new Comparator<Transaction>() {

            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getDate().compareTo(o1.getDate());
            }

        });
        ((AccountDTO) Logarsmos).setCalculatedBalance(BigDecimal.valueOf(zero));
        accountTransactions.clear();
        log.info("calcBalance took {} ms for Account {}", (System.currentTimeMillis() - start), ((AccountDTO) Logarsmos).getId());
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
        Calcbalance2(accountDTO, null);
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
}
