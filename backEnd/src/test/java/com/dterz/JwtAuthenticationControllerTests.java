package com.dterz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtAuthenticationControllerTests extends BaseTests {

    @Test
    @WithMockUser("spring")
    public void testLogout() throws Exception {
        mockMvc.perform(post("/api/logout")
                        .content(asJsonString("spring")))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
