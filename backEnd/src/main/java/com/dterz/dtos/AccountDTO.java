package com.dterz.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {

    private long id;

    private String description;

    private BigDecimal calculatedBalance;

    private Date lastTransaction;

    private Set<UserDTO> users;
}
