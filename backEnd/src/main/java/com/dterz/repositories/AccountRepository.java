package com.dterz.repositories;

import com.dterz.model.Account;
import com.dterz.model.QAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends GenericRepository<Account> {

    /**
     * Returns a Page of Accounts according to the criteria provided
     *
     * @param userId      the id of the User
     * @param pageRequest sort and filter criteria for the requested Accounts
     * @return Page<Account>
     */
    default Page<Account> findByUserId(long userId, Pageable pageRequest) {
        QAccount account = QAccount.account;
        return findAll(account.users.any().id.eq(userId), pageRequest);
    }

    /**
     * Gets an Account by its description
     *
     * @param description the description to find matching Account
     * @return Account
     */
    Account findByDescription(String description);
}
