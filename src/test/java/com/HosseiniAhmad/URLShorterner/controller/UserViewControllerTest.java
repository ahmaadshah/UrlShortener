package com.HosseiniAhmad.URLShorterner.controller;

import com.HosseiniAhmad.URLShorterner.dto.user.UserRegistrationRequestDto;
import com.HosseiniAhmad.URLShorterner.service.SubscriptionService;
import com.HosseiniAhmad.URLShorterner.service.UrlService;
import com.HosseiniAhmad.URLShorterner.service.UserService;
import com.HosseiniAhmad.URLShorterner.service.bill.BillingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserViewController.class)
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

    @WithMockUser(roles = "REGISTERED")
    @Test
    public void testRegisterUser() throws Exception {
        // Создаем объект запроса пользователя
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto();
        requestDto.setUsername("testUser");
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("12345");
        requestDto.setPasswordConfirmation("12345");

        // Отправляем POST запрос на URL /auth/register с данными пользователя и проверяем успешность операции
        mockMvc.perform(post("/auth/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testUser\",\"email\":\"test@example.com\",\"password\":\"12345\",\"passwordConfirmation\":\"12345\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void getProfile() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk());
    }

    @Test
    void getSubscriptions() throws Exception {
        mockMvc.perform(get("/subscriptions"))
                .andExpect(status().isOk());
    }

    @Test
    void getSubscriptionBuyingConfirmationPage() throws Exception {
        mockMvc.perform(get("/subscription/buy"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetSubscriptionBuyingConfirmationPage() throws Exception {
        mockMvc.perform(get("/subscription/buy"))
                .andExpect(status().isOk());
    }

    @Test
    void denySubscriptionBuying() throws Exception {
        mockMvc.perform(get("/subscription/deny"))
                .andExpect(status().isOk());
    }

    @Test
    void confirmSubscription() throws Exception {
        mockMvc.perform(get("/subscription/confirm"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUnpaidSubscription() throws Exception {
        mockMvc.perform(delete("/subscription/unpaid"))
                .andExpect(status().isOk());
    }

    @Test
    void getBindingPage() throws Exception {
        mockMvc.perform(get("/binding"))
                .andExpect(status().isOk());
    }

    @Test
    void getCreateBindingPage() throws Exception {
        mockMvc.perform(get("/binding/create"))
                .andExpect(status().isOk());
    }

    @Test
    void createBinding() throws Exception {
        mockMvc.perform(post("/binding/create"))
                .andExpect(status().isOk());
    }

    @Test
    void closeBinding() throws Exception {
        mockMvc.perform(delete("/binding/close"))
                .andExpect(status().isOk());
    }
}
