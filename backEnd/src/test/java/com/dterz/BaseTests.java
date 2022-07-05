package com.dterz;

import com.dterz.model.*;
import com.dterz.repositories.AccountRepository;
import com.dterz.repositories.InfoRepository;
import com.dterz.repositories.TransactionsRepository;
import com.dterz.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    TransactionsRepository transactionsRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    InfoRepository infoRepository;

    Account account = new Account(1L, "testAccount", new Date(), new HashSet<>(), new ArrayList<>());
    User user = new User(1L, "spring", "", "spring", "spring", "", true, new ArrayList<>(), new HashSet<>(), new HashSet<>());
    List<Transaction> income = new ArrayList<>();
    List<Transaction> expences = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();
    Transaction t1 = new Transaction(1L, BigDecimal.valueOf(100), new Date(), "description", TransanctionType.INCOME, user, account);
    Transaction t2 = new Transaction(2L, BigDecimal.valueOf(75), new Date(), "description", TransanctionType.EXPENCE, user, account);
    Info info = new Info(1L, "Backend", "1.0.0");

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        populateTestObjects();
    }

    public void populateTestObjects() {
        income.add(t1);
        expences.add(t2);
        user.getTransactions().add(t1);
        user.getTransactions().add(t2);
        user.getAccounts().add(account);
        account.getUsers().add(user);
        account.getTransactions().add(t1);
        account.getTransactions().add(t2);
        accounts.add(account);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
