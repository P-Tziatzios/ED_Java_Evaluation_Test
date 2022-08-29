package com.dterz;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dterz.dtos.UserDTO;
import com.dterz.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultMatcher;
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests extends BaseTests {

  private String URL = "/api/user";

  @MockBean
  private UserService userService;

  private final ResultMatcher okStatus = status().isOk();

  @Test
  @WithMockUser("spring")
  public void testGetById() throws Exception {
    long id = user.getId();

    mockMvc.perform(get(URL + "/{id}", id)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(okStatus)
        .andReturn();
    verify(userService, times(1)).getUserById(id);
  }

  @Test
  @WithMockUser("spring")
  public void testGetAll() throws Exception {
    mockMvc.perform(get(URL)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(okStatus)
        .andReturn();
    verify(userService, times(1)).getAllUsers();
  }

  @Test
  @WithMockUser("spring")
  public void testUpdate() throws Exception {
    UserDTO userDTO = new UserDTO();

    mockMvc.perform(post(URL)
            .content(asJsonString(userDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(okStatus)
        .andReturn();
    verify(userService, times(1)).updateUser(any(UserDTO.class));
  }

  @Test
  @WithMockUser("spring")
  public void testCreateAccount() throws Exception {
    mockMvc.perform(get(URL + "/_draft")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(okStatus)
        .andReturn();
    verify(userService, times(1)).createDraftUser();
  }
}
