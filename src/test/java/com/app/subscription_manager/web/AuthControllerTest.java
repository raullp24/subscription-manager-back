package com.app.subscription_manager.web;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.app.subscription_manager.config.JwtAuthFilter;
import com.app.subscription_manager.dtos.LoginRequestDTO;
import com.app.subscription_manager.dtos.LoginResponseDTO;
import com.app.subscription_manager.dtos.RegisterUserDTO;
import com.app.subscription_manager.dtos.UserPrivateDTO;
import com.app.subscription_manager.service.UserService;
import com.app.subscription_manager.web.AuthController;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    void testRegister() throws Exception {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setName("Test");
        registerUserDTO.setSurname("User");
        registerUserDTO.setEmail("test@example.com");
        registerUserDTO.setPassword("password");

        UserPrivateDTO userPrivateDTO = new UserPrivateDTO();
        userPrivateDTO.setName("Test");
        userPrivateDTO.setSurname("User");
        userPrivateDTO.setEmail("test@example.com");
        userPrivateDTO.setPassword("");

        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userPrivateDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\":\"Test\",\"surname\":\"User\",\"email\":\"test@example.com\",\"password\":\"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("User"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(""));
    }

    @Test
    void testLogin() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("test@example.com");
        loginRequestDTO.setPassword("password");

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken("test@example.com");

        UserPrivateDTO responseUserPrivateDTO = new UserPrivateDTO();
        responseUserPrivateDTO.setEmail("test@example.com");
        responseUserPrivateDTO.setName("Test");
        responseUserPrivateDTO.setSurname("User");
        responseUserPrivateDTO.setPassword("");
        loginResponseDTO.setUser(responseUserPrivateDTO);

        Mockito.when(userService.login(Mockito.any())).thenReturn(loginResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
