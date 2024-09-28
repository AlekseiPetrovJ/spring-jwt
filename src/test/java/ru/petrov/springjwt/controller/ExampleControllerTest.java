package ru.petrov.springjwt.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Попытка получить ответ в случае успешной авторизации")
    public void testExampleWithBearerAuth() throws Exception {

        mockMvc.perform(get("/example")
                        .header(HttpHeaders.AUTHORIZATION, "token"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Попытка получить ответ без авторизации")
    public void testExampleWithoutBearerAuth() throws Exception {
        mockMvc.perform(get("/example"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Попытка получить ресурс для админа с правами ADMIN")
    public void testExampleAdminWithAdmin() throws Exception {
        mockMvc.perform(get("/example/admin"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Попытка получить ресурс для админа с правами USER")
    public void testExampleAdminWithUser() throws Exception {
        mockMvc.perform(get("/example/admin"))
                .andExpect(status().isForbidden());
    }
}
