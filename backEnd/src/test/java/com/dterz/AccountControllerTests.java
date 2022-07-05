package com.dterz;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.dterz.dtos.AccountDTO;
import com.dterz.model.Account;
import com.dterz.model.TransanctionType;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTests extends BaseTests {


    @Test
    public void checkIfAccessRestricted() throws Exception {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(new Account()));
        mockMvc.perform(get("/api/account/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("spring")
    public void testGetById() throws Exception {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        this.mockMvc.perform(get("/api/account/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("spring")
    public void testCalculation() throws Exception {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(transactionsRepository.findByTypeAndAccount_Id(TransanctionType.INCOME, account.getId())).thenReturn(income);
        when(transactionsRepository.findByTypeAndAccount_Id(TransanctionType.EXPENCE, account.getId())).thenReturn(expences);
        this.mockMvc.perform(get("/api/account/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("spring")
    public void testCreateAccount() throws Exception {
        this.mockMvc.perform(get("/api/account/_draft")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("spring")
    public void testGetAll() throws Exception {
        Page<Account> pagedResponse = new PageImpl<>(accounts);
        when(accountRepository.findAll(PageRequest.of(0, 100))).thenReturn(pagedResponse);
        this.mockMvc.perform(get("/api/account"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("spring")
    public void testUpdate() throws Exception {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        this.mockMvc.perform(put("/api/account")
                        .content(asJsonString(new AccountDTO()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
