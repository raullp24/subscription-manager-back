package com.app.subscription_manager.web;

import com.app.subscription_manager.config.JwtAuthFilter;
import com.app.subscription_manager.dtos.InputSubscriptionDTO;
import com.app.subscription_manager.dtos.SubscriptionDTO;
import com.app.subscription_manager.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubscriptionController.class)
@AutoConfigureMockMvc(addFilters = false)
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionService subscriptionService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    private InputSubscriptionDTO inputSubscriptionDTO;
    private SubscriptionDTO subscriptionDTO;

    @BeforeEach
    void setUp() {
        inputSubscriptionDTO = new InputSubscriptionDTO();
        inputSubscriptionDTO.setUserId("user123");
        inputSubscriptionDTO.setName("Netflix");
        inputSubscriptionDTO.setDescription("Streaming platform");
        inputSubscriptionDTO.setStatus("active");
        inputSubscriptionDTO.setStartDate(LocalDate.of(2024, 1, 1));
        inputSubscriptionDTO.setPeriodicity("monthly");
        inputSubscriptionDTO.setAutoRenewal(true);
        inputSubscriptionDTO.setPrice(15.99);

        subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setId("sub123");
        subscriptionDTO.setUserId("user123");
        subscriptionDTO.setName("Netflix");
        subscriptionDTO.setDescription("Streaming platform");
        subscriptionDTO.setStatus("active");
        subscriptionDTO.setStartDate(LocalDate.of(2024, 1, 1));
        subscriptionDTO.setPeriodicity("monthly");
        subscriptionDTO.setAutoRenewal(true);
        subscriptionDTO.setPrice(15.99);
    }

    @Test
    void testCreateSubscription() throws Exception {
        when(subscriptionService.create(any(InputSubscriptionDTO.class))).thenReturn(subscriptionDTO);

        mockMvc.perform(post("/api/subscriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"user123\",\"name\":\"Netflix\",\"description\":\"Streaming platform\",\"status\":\"active\",\"startDate\":\"2024-01-01\",\"periodicity\":\"monthly\",\"autoRenewal\":true,\"price\":15.99}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("sub123"))
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.name").value("Netflix"))
                .andExpect(jsonPath("$.description").value("Streaming platform"))
                .andExpect(jsonPath("$.status").value("active"))
                .andExpect(jsonPath("$.periodicity").value("monthly"))
                .andExpect(jsonPath("$.autoRenewal").value(true))
                .andExpect(jsonPath("$.price").value(15.99));

        verify(subscriptionService, times(1)).create(any(InputSubscriptionDTO.class));
    }

    @Test
    void testGetAllSubscriptions() throws Exception {
        SubscriptionDTO subscription2 = new SubscriptionDTO();
        subscription2.setId("sub456");
        subscription2.setUserId("user456");
        subscription2.setName("Spotify");
        subscription2.setDescription("Music streaming");
        subscription2.setStatus("active");
        subscription2.setStartDate(LocalDate.of(2024, 2, 1));
        subscription2.setPeriodicity("monthly");
        subscription2.setAutoRenewal(false);
        subscription2.setPrice(9.99);

        when(subscriptionService.findAll()).thenReturn(List.of(subscriptionDTO, subscription2));

        mockMvc.perform(get("/api/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("sub123"))
                .andExpect(jsonPath("$[0].name").value("Netflix"))
                .andExpect(jsonPath("$[0].price").value(15.99))
                .andExpect(jsonPath("$[1].id").value("sub456"))
                .andExpect(jsonPath("$[1].name").value("Spotify"))
                .andExpect(jsonPath("$[1].price").value(9.99));

        verify(subscriptionService, times(1)).findAll();
    }

    @Test
    void testGetSubscriptionById() throws Exception {
        when(subscriptionService.findById("sub123")).thenReturn(subscriptionDTO);

        mockMvc.perform(get("/api/subscriptions/sub123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("sub123"))
                .andExpect(jsonPath("$.name").value("Netflix"))
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.description").value("Streaming platform"))
                .andExpect(jsonPath("$.price").value(15.99));

        verify(subscriptionService, times(1)).findById("sub123");
    }

    @Test
    void testGetSubscriptionsByUserId() throws Exception {
        SubscriptionDTO subscription2 = new SubscriptionDTO();
        subscription2.setId("sub789");
        subscription2.setUserId("user123");
        subscription2.setName("Amazon Prime");
        subscription2.setDescription("Prime membership");
        subscription2.setStatus("active");
        subscription2.setStartDate(LocalDate.of(2024, 3, 1));
        subscription2.setPeriodicity("yearly");
        subscription2.setAutoRenewal(true);
        subscription2.setPrice(99.99);

        when(subscriptionService.findByUserId("user123")).thenReturn(List.of(subscriptionDTO, subscription2));

        mockMvc.perform(get("/api/subscriptions/user/user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("sub123"))
                .andExpect(jsonPath("$[0].userId").value("user123"))
                .andExpect(jsonPath("$[0].name").value("Netflix"))
                .andExpect(jsonPath("$[1].id").value("sub789"))
                .andExpect(jsonPath("$[1].userId").value("user123"))
                .andExpect(jsonPath("$[1].name").value("Amazon Prime"));

        verify(subscriptionService, times(1)).findByUserId("user123");
    }
}
