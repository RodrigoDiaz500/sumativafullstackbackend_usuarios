package com.duoc.usuarios.service;

import com.duoc.usuarios.model.Usuario;
import com.duoc.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Rodrigo");
        usuario.setApellido("Díaz");
        usuario.setEmail("rodri@test.com");
        usuario.setPassword("123456");
        usuario.setRol(null);
        usuario.setEstado(null);
    }

    @Test
    void testCrearUsuarioAsignandoValoresPorDefecto() {
        when(usuarioRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario creado = usuarioService.crearUsuario(usuario);

        assertEquals("ACTIVO", creado.getEstado());
        assertEquals("PATIENT", creado.getRol());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testListarUsuarios() {
        List<Usuario> lista = Arrays.asList(usuario);
        when(usuarioRepository.findAll()).thenReturn(lista);

        List<Usuario> result = usuarioService.listarUsuarios();
        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.obtenerPorId(1L);
        assertTrue(result.isPresent());
        assertEquals("Rodrigo", result.get().getNombre());
    }

    @Test
    void testActualizarUsuario() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario actualizado = usuarioService.actualizarUsuario(1L, usuario);

        assertEquals(usuario, actualizado);
    }

    @Test
    void testEliminarUsuario() {
        doNothing().when(usuarioRepository).deleteById(1L);
        usuarioService.eliminarUsuario(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void testLoginExitoso() {
        when(usuarioRepository.findByEmailAndPassword("rodri@test.com", "123456"))
                .thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.login("rodri@test.com", "123456");
        assertTrue(result.isPresent());
    }

    @Test
    void testLoginFallido() {
        when(usuarioRepository.findByEmailAndPassword("rodri@test.com", "wrong"))
                .thenReturn(Optional.empty());

        Optional<Usuario> result = usuarioService.login("rodri@test.com", "wrong");
        assertFalse(result.isPresent());
    }
}
