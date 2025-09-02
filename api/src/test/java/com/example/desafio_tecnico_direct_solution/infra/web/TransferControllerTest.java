package com.example.desafio_tecnico_direct_solution.infra.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransferControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper mapper;

    @Test
    void schedule_should_return_200_and_body() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("sourceAccount", "1234567890");
        body.put("destinationAccount", "9999999999");
        body.put("amount", new BigDecimal("1000.00"));
        body.put("transferDate", LocalDate.now().plusDays(5).toString()); // yyyy-MM-dd

        mockMvc.perform(
                        post("/api/v1/transfers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.amount").value(1000.0))
                .andExpect(jsonPath("$.fee").value(12.0))
                .andExpect(jsonPath("$.total").value(1012.0))
                .andExpect(jsonPath("$.status").value("SCHEDULED"));
    }

    @Test
    void schedule_should_validate_and_return_400() throws Exception {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("sourceAccount", "123");            // inválido
        body.put("destinationAccount", "9999999999");
        body.put("amount", new BigDecimal("1000.00"));
        body.put("transferDate", LocalDate.now().plusDays(1).toString());

        mockMvc.perform(
                        post("/api/v1/transfers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validação falhou"))
                .andExpect(jsonPath("$.fields.sourceAccount").exists());
    }

    @Test
    void list_should_return_array() throws Exception {
        mockMvc.perform(get("/api/v1/transfers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
