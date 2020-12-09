package br.com.rest.negociacao.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_cliente")
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column
    private String cpf;

    @Column
    private LocalDate dataNascimento;

    @Column
    private BigDecimal valorDivida;

    @OneToOne
    @JoinColumn( name = "forma_pagamento_id")
    private FormaPagamento formaPagamento;



}
