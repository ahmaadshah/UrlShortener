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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

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

    //@WithMockUser(roles = "REGISTERED")
    @WithAnonymousUser
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
    void getLoginPage_ReturnsLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("auth/login"));
    }



    @Test
    void getRegistrationPage_ReturnsRegistrationPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("auth/registration"));
    }






    @Test
    void register_InvalidUser_ReturnsRegistrationPage() throws Exception {
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto();
        // Установка недопустимых значений полей requestDto

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .flashAttr("userForm", requestDto))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("auth/registration"));
    }
    @Test
    void confirmEmail_ReturnsConfirmationPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/1/email_confirmation"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/confirmation"));
    }


















}
