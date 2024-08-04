package ua.dolofinskyi.features.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;

import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private TaskServiceImpl taskService;
    @Autowired
    private ObjectMapper objectMapper;
    private TaskDto initTask;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        initTask = new TaskDto();
        initTask.setTitle("Title");
        initTask.setDescription("Description");
    }

    @Test
    @WithMockUser(username = "user")
    public void getTaskById() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(initTask);

        mockMvc.perform(
                get("/api/task/get?id=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(initTask.getTitle()))
                .andExpect(jsonPath("$.description").value(initTask.getDescription()));

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    @WithMockUser(username = "user")
    public void createTask() throws Exception {
        when(taskService.createTask(any(TaskDto.class))).thenReturn(initTask);

        mockMvc.perform(
                    post("/api/task/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(initTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(initTask.getTitle()))
                .andExpect(jsonPath("$.description").value(initTask.getDescription()));

        verify(taskService, times(1)).createTask(any(TaskDto.class));
    }

    @Test
    @WithMockUser(username = "user")
    public void updateTask() throws Exception {
        when(taskService.updateTask(any(TaskDto.class))).thenReturn(initTask);

        mockMvc.perform(
                        put("/api/task/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(initTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(initTask.getId()))
                .andExpect(jsonPath("$.title").value(initTask.getTitle()))
                .andExpect(jsonPath("$.description").value(initTask.getDescription()));

        verify(taskService, times(1)).updateTask(any(TaskDto.class));
    }

    @Test
    @WithMockUser(username = "user")
    public void deleteTask() throws Exception {
        mockMvc.perform(delete("/api/task/delete?id=1"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    @WithMockUser(username = "user")
    public void getAllUserTasks() throws Exception {
        List<TaskDto> listAllTasks = new ArrayList<>();
        listAllTasks.add(initTask);

        when(taskService.getAllUserTasks()).thenReturn(listAllTasks);

        mockMvc.perform(
                        get("/api/task/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(initTask.getId()))
                .andExpect(jsonPath("$[0].title").value(initTask.getTitle()))
                .andExpect(jsonPath("$[0].description").value(initTask.getDescription()));

        verify(taskService, times(1)).getAllUserTasks();
    }
}