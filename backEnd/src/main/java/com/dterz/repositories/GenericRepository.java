package com.dterz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T> extends JpaRepository<T, Long>,
        QuerydslPredicateExecutor<T> {

    /**
     * Deletes an object based on its id
     *
     * @param id the id of the Object to delete
     */
    default void delete(Long id) {
        findById(id).ifPresent(this::delete);
    }
}
