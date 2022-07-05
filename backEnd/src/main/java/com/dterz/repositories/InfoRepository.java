package com.dterz.repositories;

import com.dterz.model.Info;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoRepository extends GenericRepository<Info> {

    /**
     * Finds an Info based on its component
     *
     * @param component the component description of the Object
     */
    Info findByComponent(String component);
}
