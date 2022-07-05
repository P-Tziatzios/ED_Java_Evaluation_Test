package com.dterz;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dterz.dtos.TransactionDTO;
import com.dterz.model.Transaction;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionsControllerTests extends BaseTests {

    @Test
    @WithMockUser("spring")
    public void testCreateDraftTransaction() throws Exception {
        when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(account));
        mockMvc.perform(get("/api/transactions/_draft/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("spring")
    public void testUpdate() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUserName("spring");
        transactionDTO.setAccountName("testAccount");
        transactionDTO.setAmount(BigDecimal.valueOf(999));
        when(transactionsRepository.findById(1L)).thenReturn(Optional.of(t1));
        when(userRepository.findByUserName("spring")).thenReturn(user);
        when(accountRepository.findByDescription("testAccount")).thenReturn(account);
        this.mockMvc.perform(put("/api/transactions/")
                        .content(asJsonString(transactionDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(999))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("spring")
    public void testGetById() throws Exception {
        when(transactionsRepository.findById(2L)).thenReturn(Optional.of(t2));
        this.mockMvc.perform(get("/api/transactions/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(BigDecimal.valueOf(75)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("spring")
    public void testGetAllForUser() throws Exception {
        List<Transaction> all = new ArrayList<>();
        all.addAll(income);
        all.addAll(expences);
        Page<Transaction> pagedResponse = new PageImpl<>(all);
        when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
        when(transactionsRepository.findByUserId(user.getId(), PageRequest.of(0, 5, Sort.by("date").ascending()))).thenReturn(pagedResponse);
        this.mockMvc.perform(get("/api/transactions/getAllForUser/")
                        .param("userID", user.getUserName())
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("spring")
    public void testGetAllForUserDescription() throws Exception {
        List<Transaction> all = new ArrayList<>();
        all.addAll(income);
        all.addAll(expences);
        Page<Transaction> pagedResponse = new PageImpl<>(all);
        when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
        when(transactionsRepository.findByUserIdAndDescriptionContaining(user.getId(), "description", PageRequest.of(0, 5, Sort.by("date").ascending()))).thenReturn(pagedResponse);
        this.mockMvc.perform(get("/api/transactions/getAllForUser/")
                        .param("userID", user.getUserName())
                        .param("description", "description")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("spring")
    public void testGetAllForAccount() throws Exception {
        List<Transaction> all = new ArrayList<>();
        all.addAll(income);
        all.addAll(expences);
        Page<Transaction> pagedResponse = new PageImpl<>(all);
        when(transactionsRepository.findByAccountId(1L, PageRequest.of(0, 5, Sort.by("date").ascending()))).thenReturn(pagedResponse);
        this.mockMvc.perform(get("/api/transactions/getAllForAccount/")
                        .param("accountId", "1")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(status().isOk());
    }
}
