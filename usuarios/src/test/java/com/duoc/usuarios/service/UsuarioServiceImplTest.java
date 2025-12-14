package com.duoc.usuarios.service;

import com.duoc.usuarios.model.Usuario;
import com.duoc.usuarios.repository.UsuarioRepository;
import com.duoc.usuarios.service.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void crearUsuario_ok() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@test.com");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario result = usuarioService.crearUsuario(usuario);

        assertNotNull(result);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void listarUsuarios_ok() {
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario()));

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        assertEquals(1, usuarios.size());
    }

    @Test
    void login_exitoso() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@test.com");
        usuario.setPassword("1234");

        when(usuarioRepository.findByEmailAndPassword("test@test.com", "1234"))
                .thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.login("test@test.com", "1234");

        assertTrue(result.isPresent());
    }

    @Test
    void login_fallido() {
        when(usuarioRepository.findByEmailAndPassword("test@test.com", "wrong"))
                .thenReturn(Optional.empty());

        Optional<Usuario> result = usuarioService.login("test@test.com", "wrong");

        assertTrue(result.isEmpty());
    }
}
