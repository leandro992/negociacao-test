package br.com.rest.builders.negociacao.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_forma_pagamento")
@Entity
public class FormaPagamento {


    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private String descricao;

}
