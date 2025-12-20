package com.duoc.usuarios.controller;

import com.duoc.usuarios.model.Usuario;
import com.duoc.usuarios.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

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
    void testListarUsuarios() throws Exception {
        when(usuarioService.listarUsuarios()).thenReturn(Collections.singletonList(usuario));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Rodrigo"));
    }

    @Test
    void testObtenerUsuarioExistente() throws Exception {
        when(usuarioService.obtenerPorId(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("rodri@test.com"));
    }

    @Test
    void testObtenerUsuarioNoExistente() throws Exception {
        when(usuarioService.obtenerPorId(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearUsuario() throws Exception {
        when(usuarioService.crearUsuario(any())).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Rodrigo"));
    }

    @Test
    void testActualizarUsuario() throws Exception {
        when(usuarioService.actualizarUsuario(eq(1L), any())).thenReturn(usuario);

        mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apellido").value("Díaz"));
    }

    @Test
    void testEliminarUsuario() throws Exception {
        doNothing().when(usuarioService).eliminarUsuario(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
