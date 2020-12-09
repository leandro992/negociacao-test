package br.com.rest.negociacao.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {

    private Long id;

    private String nome;

    private String cpf;

    private String idade;

    private LocalDate dataNascimento;
}
