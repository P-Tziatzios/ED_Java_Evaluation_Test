package com.dterz.repositories;

import com.dterz.model.Transaction;
import com.dterz.model.TransanctionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends GenericRepository<Transaction> {

    /**
     * Returns a Page of Transactions for a User according to the criteria provided
     *
     * @param userId      the id of the User
     * @param pageRequest sort and filter criteria for the requested Transactions
     * @return Page<Transaction>
     */
    Page<Transaction> findByUserId(long userId, Pageable pageRequest);

    /**
     * Returns a Page of Transactions for an Account according to the criteria provided
     *
     * @param accountId   the id of the Account
     * @param pageRequest sort and filter criteria for the requested Transactions
     * @return Page<Transaction>
     */
    Page<Transaction> findByAccountId(long accountId, Pageable pageRequest);

    /**
     * Returns a Page of Transactions for an User according to the criteria provided filtered by Description
     *
     * @param userId      the id of the User
     * @param description the input for filtering the Transactions by description
     * @param pageRequest sort and filter criteria for the requested Transactions
     * @return Page<Transaction>
     */
    Page<Transaction> findByUserIdAndDescriptionContaining(Long userId, String description, Pageable pageRequest);

    /**
     * Returns a List of Transactions based on its Transaction type
     *
     * @param type      the type of the Transactions requested as an ENUM
     * @param accountId the id of the Account
     * @return List<Transaction>
     */
    List<Transaction> findByTypeAndAccount_Id(TransanctionType type, long accountId);

    /**
     * Returns a List of all Transaction id's
     *
     * @return List<Long>
     */
    @Query("SELECT id from Transaction")
    List<Long> findAllIds();
}

