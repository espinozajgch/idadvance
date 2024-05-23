package com.inadvance.prueba.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inadvance.prueba.dto.UserDto;
import com.inadvance.prueba.exception.NotFoundException;
import com.inadvance.prueba.exception.ValidationException;
import com.inadvance.prueba.security.JWTAuthtenticationConfig;
import com.inadvance.prueba.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private JWTAuthtenticationConfig jwtAuthtenticationConfig;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void startUp(){
        token = jwtAuthtenticationConfig.getJWTToken("test");
    }

    @Test
    @WithMockUser
    public void testGetUserByEmail() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("juan@rodriguez.cl");
        userDto.setName("Juan Rodriguez");

        when(userService.getUserByEmail("juan@rodriguez.cl")).thenReturn(userDto);

        mockMvc.perform(get("/api/v1/user")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("email", "juan@rodriguez.cl"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("juan@rodriguez.cl"))
                .andExpect(jsonPath("$.name").value("Juan Rodriguez"));
    }

    @Test
    @WithMockUser
    public void testGetUserByEmailNotFound() throws Exception {

        when(userService.getUserByEmail("juan@rodriguez.cl")).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/user")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("email", "juan@rodriguez.cl"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserByEmail("juan@rodriguez.cl");

    }

    @Test
    @WithMockUser
    public void testGetUserByEmailBadRequest() throws Exception {

        when(userService.getUserByEmail("juan@rodriguez.cl")).thenThrow(ValidationException.class);

        mockMvc.perform(get("/api/v1/user")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("email", "juan@rodriguez.cl"))
                .andExpect(status().isBadRequest());

        verify(userService, times(1)).getUserByEmail("juan@rodriguez.cl");

    }

    @Test
    @WithMockUser
    public void testGetAllUsers() throws Exception {
        UserDto userDto1 = new UserDto();
        userDto1.setEmail("juan@rodriguez.cl");
        userDto1.setName("Juan Rodriguez");

        UserDto userDto2 = new UserDto();
        userDto2.setEmail("test@example.com");
        userDto2.setName("Test User ");

        List<UserDto> users = Arrays.asList(userDto1, userDto2);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/user/all")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("juan@rodriguez.cl"))
                .andExpect(jsonPath("$[0].name").value("Juan Rodriguez"))
                .andExpect(jsonPath("$[1].email").value("test@example.com"))
                .andExpect(jsonPath("$[1].name").value("Test User "));
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("juan@rodriguez.cl");
        userDto.setName("Juan Rodriguez");

        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/api/v1/user")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("juan@rodriguez.cl"))
                .andExpect(jsonPath("$.name").value("Juan Rodriguez"));
    }

    @Test
    @WithMockUser
    void testUpdateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("juan@rodriguez.cl");
        userDto.setName("Juan Rodriguez");

        when(userService.updateUser(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(put("/api/v1/user")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("juan@rodriguez.cl"))
                .andExpect(jsonPath("$.name").value("Juan Rodriguez"));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/user")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("email", "juan@rodriguez.cl"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser("juan@rodriguez.cl");
    }

}