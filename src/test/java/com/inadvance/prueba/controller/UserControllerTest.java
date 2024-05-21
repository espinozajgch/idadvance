package com.inadvance.prueba.controller;

import com.inadvance.prueba.dto.UserDto;
import com.inadvance.prueba.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterUser_Success() throws Exception {
        UserDto user = new UserDto();
        user.setName("Juan Rodriguez");
        user.setEmail("juan@rodriguez.org");
        user.setPassword("hunter2");
        user.setPhones(Collections.emptyList());

        //Mockito.when(userService.getUserByEmail(user.getEmail())).thenReturn(false);
        Mockito.when(userService.createUser(Mockito.any(UserDto.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Juan Rodriguez\", \"email\": \"juan@rodriguez.org\", \"password\": \"hunter2\", \"phones\": [] }"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testRegisterUser_EmailExists() throws Exception {
        Mockito.when(userService.getUserByEmail("juan@rodriguez.org")).thenReturn(null);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Juan Rodriguez\", \"email\": \"juan@rodriguez.org\", \"password\": \"hunter2\", \"phones\": [] }"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"mensaje\":\"El correo ya registrado\"}"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById_NotFound() throws Exception {
        UUID userId = UUID.randomUUID();

        Mockito.when(userService.getUserByEmail("")).thenReturn(null);

        mockMvc.perform(get("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"mensaje\":\"Usuario no encontrado\"}"));
    }

    @Test
    public void testUpdateUser_Success() throws Exception {
        UUID userId = UUID.randomUUID();
        UserDto user = new UserDto();
        user.setId(userId);
        user.setName("Juan Rodriguez");
        user.setEmail("juan@rodriguez.org");
        user.setPassword("hunter2");
        user.setPhones(Collections.emptyList());

        Mockito.when(userService.getUserByEmail("")).thenReturn(user);
        Mockito.when(userService.createUser(Mockito.any(UserDto.class))).thenReturn(user);

        mockMvc.perform(put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Juan Updated\", \"email\": \"juan@updated.org\", \"password\": \"updated2\", \"phones\": [] }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser_NotFound() throws Exception {
        UUID userId = UUID.randomUUID();

        Mockito.when(userService.getUserByEmail("userId")).thenReturn(null);

        mockMvc.perform(delete("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"mensaje\":\"Usuario no encontrado\"}"));
    }

}