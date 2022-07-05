package com.dterz;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dterz.dtos.UserDTO;
import com.dterz.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests extends BaseTests {

    @Test
    @WithMockUser("spring")
    public void testGetById() throws Exception {

    }

    @Test
    @WithMockUser("spring")
    public void testGetAll() throws Exception {

    }

    @Test
    @WithMockUser("spring")
    public void testUpdate() throws Exception {

    }

    @Test
    @WithMockUser("spring")
    public void testCreateAccount() throws Exception {

    }
}
