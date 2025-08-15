package com.example.EY.Infrastructure.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nombre;

    @Column(unique = true)
    private String correo;

    private String contraseña;
    private String token;
    private boolean activo;

    private LocalDateTime creado;
    private LocalDateTime modificado;
    private LocalDateTime ultimoLogin;

    @Column(nullable = false)
    private String role;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "usuario",
            orphanRemoval = true
    )
    private List<Telefono> telefonos = new ArrayList<>();

    @PrePersist
    private void setDefaultRole() {
        if (this.role == null) {
            this.role = "USER";  // Valor por defecto
        }
    }
}
