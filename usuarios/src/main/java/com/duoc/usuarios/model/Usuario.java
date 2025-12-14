package com.duoc.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USUARIO_SISTEMA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROL", nullable = false)
    private String rol;

    @Column(name = "ESTADO", nullable = false)
    private String estado;
}
