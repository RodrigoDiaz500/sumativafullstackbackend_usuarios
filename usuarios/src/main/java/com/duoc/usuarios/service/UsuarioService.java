package com.duoc.usuarios.service;

import com.duoc.usuarios.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Usuario crearUsuario(Usuario usuario);

    List<Usuario> listarUsuarios();

    Optional<Usuario> obtenerPorId(Long id);

    Usuario actualizarUsuario(Long id, Usuario usuario);

    void eliminarUsuario(Long id);

    Optional<Usuario> login(String email, String password);
}

