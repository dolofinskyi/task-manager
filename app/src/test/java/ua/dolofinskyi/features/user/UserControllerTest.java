package ua.dolofinskyi.features.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserServiceImpl userService;
    @Autowired
    private ObjectMapper objectMapper;
    private UserDto initUserDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        initUserDto = new UserDto();
        initUserDto.setUsername("username");
        initUserDto.setEmail("email");
        initUserDto.setTasks(new ArrayList<>());
    }
    @Test
    @WithMockUser
    void updateUser() throws Exception {
        when(userService.updateUser(any(UserDto.class))).thenReturn(initUserDto);

        mockMvc.perform(
                        put("/api/user/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(initUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(initUserDto.getUsername()))
                .andExpect(jsonPath("$.email").value(initUserDto.getEmail()))
                .andExpect(jsonPath("$.tasks").value(initUserDto.getTasks()));
    }

    @Test
    @WithMockUser
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/user/delete"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser();
    }
}