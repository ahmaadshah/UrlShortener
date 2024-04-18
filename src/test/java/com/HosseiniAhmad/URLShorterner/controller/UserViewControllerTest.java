package com.HosseiniAhmad.URLShorterner.controller;

import com.HosseiniAhmad.URLShorterner.dto.subscription.SubscriptionResponseDto;
import com.HosseiniAhmad.URLShorterner.dto.url.binding.UrlBindingResponseDto;
import com.HosseiniAhmad.URLShorterner.dto.user.UserResponseDto;
import com.HosseiniAhmad.URLShorterner.model.entity.bill.Price;
import com.HosseiniAhmad.URLShorterner.model.entity.user.Role;
import com.HosseiniAhmad.URLShorterner.service.SubscriptionService;
import com.HosseiniAhmad.URLShorterner.service.UrlService;
import com.HosseiniAhmad.URLShorterner.service.UserService;
import com.HosseiniAhmad.URLShorterner.service.bill.BillingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserViewController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SubscriptionService subscriptionService;

    @MockBean
    private BillingService billingService;

    @MockBean
    private UrlService urlService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getProfile() throws Exception {
        UserResponseDto userResponseDto = new UserResponseDto(
                1L,
                "test@gmail.com",
                "Test",
                Role.ROLE_ADMIN,
                null);
        when(userService.getAuthenticatedUser()).thenReturn(userResponseDto);
        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk());
    }

    @Test
    void getSubscriptions() throws Exception {
        UserResponseDto userResponseDto = new UserResponseDto(
                1L,
                "test@gmail.com",
                "Test",
                Role.ROLE_ADMIN,
                null);
        when(userService.getAuthenticatedUser()).thenReturn(userResponseDto);
        mockMvc.perform(get("/user/subscription"))
                .andExpect(status().isOk());
    }

    @Test
    void getSubscriptionBuyingConfirmationPage() throws Exception {
        BigDecimal priceValue = new BigDecimal("10.00");
        Price price = new Price();
        price.setPrice(priceValue);
        when(billingService.geCurrentPrice()).thenReturn(price);
        Set<UrlBindingResponseDto> urlBindings = new HashSet<>();
        UserResponseDto userResponseDto = new UserResponseDto(
                1L,
                "test@example.com",
                "test-user",
                Role.ROLE_REGISTERED,
                urlBindings);
        when(userService.getAuthenticatedUser()).thenReturn(userResponseDto);
        SubscriptionResponseDto subscriptionResponseDto = new SubscriptionResponseDto(
                1L,
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                true,
                123L);


        when(subscriptionService.updateSubscription(1L)).thenReturn(subscriptionResponseDto);
        mockMvc.perform(get("/user/subscription/1/buy"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetSubscriptionBuyingConfirmationPage() throws Exception {
        BigDecimal priceValue = new BigDecimal("10.00");
        Price price = new Price();
        price.setPrice(priceValue);
        when(billingService.geCurrentPrice()).thenReturn(price);
        Set<UrlBindingResponseDto> urlBindings = new HashSet<>();
        UserResponseDto userResponseDto = new UserResponseDto(
                1L,
                "test@example.com",
                "test-user",
                Role.ROLE_REGISTERED,
                urlBindings);
        when(userService.getAuthenticatedUser()).thenReturn(userResponseDto);
        SubscriptionResponseDto subscriptionResponseDto = new SubscriptionResponseDto(
                1L,
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                true,
                123L);
        when(subscriptionService.createSubscription(1L)).thenReturn(subscriptionResponseDto);
        mockMvc.perform(get("/user/subscription/buy"))
                .andExpect(status().isOk());
    }

    @Test
    void denySubscriptionBuying() throws Exception {
        mockMvc.perform(get("/user/subscription/deny"))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    void confirmSubscription() throws Exception {
        SubscriptionResponseDto subscription = new SubscriptionResponseDto(
                123L,
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                true,
                456L
        );
        when(subscriptionService.getSubscription(123L)).thenReturn(subscription);
        mockMvc.perform(get("/user/subscription/{subscriptionId}/confirm", 123L))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void deleteUnpaidSubscription() throws Exception {
        mockMvc.perform(get("/user/subscription/{subscriptionId}/delete", 123L))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void getBindingPage() throws Exception {
        mockMvc.perform(get("/user/binding"))
                .andExpect(status().isOk());
    }

    @Test
    void getCreateBindingPage() throws Exception {
        mockMvc.perform(get("/user/binding/create"))
                .andExpect(status().isOk());
    }

    @Test
    void createBinding() throws Exception {
        mockMvc.perform(post("/user/binding/create"))
                .andExpect(status().isOk());
    }

    @Test
    void closeBinding() throws Exception {
        mockMvc.perform(get("/user/binding/{bindingId}/close", 123L))
                .andExpect(status().is3xxRedirection());
    }
}