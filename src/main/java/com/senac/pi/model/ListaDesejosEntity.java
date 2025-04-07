package com.senac.pi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lista_desejos")
public class ListaDesejosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UsuarioEntity usuario;

    @ManyToOne
    private ProdutoEntity produto;
}