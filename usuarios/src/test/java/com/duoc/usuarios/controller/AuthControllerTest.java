package com.duoc.usuarios.controller;

import com.duoc.usuarios.model.Usuario;
import com.duoc.usuarios.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    private Usuario usuario;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "Rodrigo", "Díaz", "rodri@test.com", "123456", "PATIENT", "12345678", "ACTIVO");
    }

    @Test
    void testLoginExitoso() throws Exception {
        when(usuarioService.login("rodri@test.com", "123456")).thenReturn(Optional.of(usuario));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Rodrigo"));
    }

    @Test
    void testLoginFallido() throws Exception {
        when(usuarioService.login("rodri@test.com", "wrong")).thenReturn(Optional.empty());

        usuario.setPassword("wrong");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(usuario)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Credenciales incorrectas"));
    }
}
