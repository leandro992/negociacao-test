package br.com.rest.builders.negociacao.service;


import br.com.rest.builders.negociacao.model.Cliente;
import br.com.rest.builders.negociacao.model.FormaPagamento;
import br.com.rest.builders.negociacao.model.dto.ClienteRequestDTO;
import br.com.rest.builders.negociacao.model.dto.ClienteResponseDTO;
import br.com.rest.builders.negociacao.repository.ClienteRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {


    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteResponseDTO> list(Pageable page) {
        List<ClienteResponseDTO> listResponse = new ArrayList<>();
        ClienteResponseDTO responseDTO = new ClienteResponseDTO();
        List<Cliente> response = clienteRepository.findAll(page).getContent();

        for (Cliente c : response) {
            responseDTO = new ClienteResponseDTO();
            responseDTO.setCpf(c.getCpf());
            responseDTO.setDataNascimento(c.getDataNascimento());
            responseDTO.setNome(c.getNome());
            responseDTO.setId(c.getId());
            Period idade = Period.between(c.getDataNascimento(), LocalDate.now());
            responseDTO.setIdade(String.valueOf(idade.getYears()));
            listResponse.add(responseDTO);
        }
        return listResponse;
    }


    public Optional<Cliente> consultNomeCpf(String nome, String cpf) {
        return clienteRepository.findByNomeAndCpf(nome, cpf);
    }


    public Optional<Cliente> consult(Long clienteId) {
        return clienteRepository.findById(clienteId);
    }


    public void save(ClienteRequestDTO cliente) {

        Cliente entityCliente = new Cliente();

        BeanUtils.copyProperties(cliente, entityCliente, "id");
        entityCliente.setFormaPagamento(FormaPagamento.builder()
                .id(cliente.getCodigoFormaPagamento())
                .build());

        clienteRepository.save(entityCliente);
    }


    public ResponseEntity<Cliente> update(Cliente cliente, Long clienteId) {

        Optional<Cliente> cliente1 = clienteRepository.findById(clienteId);

        if (cliente1.isPresent()) {
            BeanUtils.copyProperties(cliente, cliente1.get(), "id");
            Cliente response = clienteRepository.save(cliente1.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }


    public Boolean delete(Long clienteId) {
        Optional<Cliente> consult = consult(clienteId);
        if (consult.isPresent()) {
            clienteRepository.delete(consult.get());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


}
