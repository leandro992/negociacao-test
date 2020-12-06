package br.com.rest.builders.negociacao.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequestDTO {

    private String nome;

    private String cpf;

    private LocalDate dataNascimento;

    private BigDecimal valorDivida;

    private Long codigoFormaPagamento;



}
