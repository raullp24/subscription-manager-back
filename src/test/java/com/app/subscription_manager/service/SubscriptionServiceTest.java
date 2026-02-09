package com.app.subscription_manager.service;

import com.app.subscription_manager.dtos.InputSubscriptionDTO;
import com.app.subscription_manager.dtos.SubscriptionDTO;
import com.app.subscription_manager.exception.SubscriptionNotFoundException;
import com.app.subscription_manager.exception.UserNotFoundException;
import com.app.subscription_manager.model.Subscription;
import com.app.subscription_manager.model.Users;
import com.app.subscription_manager.repository.SubscriptionRepository;
import com.app.subscription_manager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class SubscriptionServiceTest {

    @MockBean
    private SubscriptionRepository subscriptionRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    private Subscription subscription;
    private InputSubscriptionDTO inputSubscriptionDTO;

    @BeforeEach
    void setUp() {
        inputSubscriptionDTO = new InputSubscriptionDTO();
        inputSubscriptionDTO.setUserId("user123");
        inputSubscriptionDTO.setName("Netflix");
        inputSubscriptionDTO.setDescription("Servicio de streaming de películas y series");
        inputSubscriptionDTO.setStatus("ACTIVE");
        inputSubscriptionDTO.setStartDate(LocalDate.of(2024, 1, 1));
        inputSubscriptionDTO.setPeriodicity("MONTHLY");
        inputSubscriptionDTO.setAutoRenewal(true);
        inputSubscriptionDTO.setPrice(15.99);

        subscription = new Subscription();
        subscription.setId("sub123");
        subscription.setUserId("user123");
        subscription.setName("Netflix");
        subscription.setDescription("Servicio de streaming de películas y series");
        subscription.setStatus("ACTIVE");
        subscription.setStartDate(LocalDate.of(2024, 1, 1));
        subscription.setPeriodicity("MONTHLY");
        subscription.setAutoRenewal(true);
        subscription.setPrice(15.99);
    }

    @Test
    void create_ShouldReturnSubscriptionDTO() {

        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

        SubscriptionDTO result = subscriptionService.create(inputSubscriptionDTO);

        assertNotNull(result);
        assertEquals("sub123", result.getId());
        assertEquals("Netflix", result.getName());
        assertEquals("Servicio de streaming de películas y series", result.getDescription());
        assertEquals("ACTIVE", result.getStatus());
        assertEquals(LocalDate.of(2024, 1, 1), result.getStartDate());
        assertEquals("MONTHLY", result.getPeriodicity());
        assertTrue(result.getAutoRenewal());
        assertEquals(15.99, result.getPrice());
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    void findAll_ShouldReturnListOfSubscriptions() {

        Subscription subscription2 = new Subscription();
        subscription2.setId("sub456");
        subscription2.setUserId("user123");
        subscription2.setName("Spotify");
        subscription2.setDescription("Servicio de streaming de música");
        subscription2.setStatus("ACTIVE");
        subscription2.setStartDate(LocalDate.of(2024, 2, 1));
        subscription2.setPeriodicity("MONTHLY");
        subscription2.setAutoRenewal(true);
        subscription2.setPrice(9.99);
        
        when(subscriptionRepository.findAll()).thenReturn(Arrays.asList(subscription, subscription2));

        List<SubscriptionDTO> result = subscriptionService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(subscriptionRepository, times(1)).findAll();
    }

    @Test
    void findByUserId_WhenUserExists() throws UserNotFoundException {
        Users user = new Users();
        user.setId("user123");
        
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        when(subscriptionRepository.findByUserId("user123")).thenReturn(Arrays.asList(subscription));

        List<SubscriptionDTO> result = subscriptionService.findByUserId("user123");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("sub123", result.get(0).getId());
        assertEquals("Netflix", result.get(0).getName());
        assertEquals("Servicio de streaming de películas y series", result.get(0).getDescription());
        assertEquals("ACTIVE", result.get(0).getStatus());
        assertEquals(LocalDate.of(2024, 1, 1), result.get(0).getStartDate());
        assertEquals("MONTHLY", result.get(0).getPeriodicity());
        assertTrue(result.get(0).getAutoRenewal());
        assertEquals(15.99, result.get(0).getPrice());
        verify(userRepository, times(1)).findById("user123");
        verify(subscriptionRepository, times(1)).findByUserId("user123");
    }

    @Test
    void findByUserId_WhenUserNotExists() {
        when(userRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            subscriptionService.findByUserId("nonexistent");
        });
        verify(userRepository, times(1)).findById("nonexistent");
        verify(subscriptionRepository, never()).findByUserId(anyString());
    }

    @Test
    void findById_WhenSubscriptionExists() throws SubscriptionNotFoundException {
        when(subscriptionRepository.findById("sub123")).thenReturn(Optional.of(subscription));

        SubscriptionDTO result = subscriptionService.findById("sub123");

        assertNotNull(result);
        assertEquals("sub123", result.getId());
        assertEquals("Netflix", result.getName());
        assertEquals("Servicio de streaming de películas y series", result.getDescription());
        assertEquals("ACTIVE", result.getStatus());
        assertEquals(LocalDate.of(2024, 1, 1), result.getStartDate());
        assertEquals("MONTHLY", result.getPeriodicity());
        assertTrue(result.getAutoRenewal());
        assertEquals(15.99, result.getPrice());
        verify(subscriptionRepository, times(1)).findById("sub123");
    }

    @Test
    void findById_WhenSubscriptionNotExists() {
        when(subscriptionRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(SubscriptionNotFoundException.class, () -> {
            subscriptionService.findById("nonexistent");
        });
        verify(subscriptionRepository, times(1)).findById("nonexistent");
    }
}
