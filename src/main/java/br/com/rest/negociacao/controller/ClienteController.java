package br.com.rest.negociacao.controller;

import br.com.rest.negociacao.model.Cliente;
import br.com.rest.negociacao.model.dto.ClienteRequestDTO;
import br.com.rest.negociacao.model.dto.ClienteResponseDTO;
import br.com.rest.negociacao.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {


    @Autowired
    private ClienteService clienteService;


    @GetMapping(value = "/{clientes}")
    public ResponseEntity<List<ClienteResponseDTO>> list(@PageableDefault(sort = "nome", direction = Sort.Direction.ASC,
            page = 0,
            size = 10) Pageable page){
        return ResponseEntity.ok(clienteService.list(page));
    }

    @GetMapping()
    public ResponseEntity<Cliente> consult(@RequestParam String nome, @RequestParam String cpf){
        Optional<Cliente> consult = clienteService.consultNomeCpf(nome,cpf);
        if (consult.isPresent() ){ return ResponseEntity.ok(consult.get()); }
        return ResponseEntity.notFound().build();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody ClienteRequestDTO cliente){
         clienteService.save(cliente);
    }


    @PutMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> update(@RequestBody Cliente cliente, @RequestHeader Long clienteId){
        return clienteService.update(cliente, clienteId);
    }


    @DeleteMapping
    public ResponseEntity<Cliente> delete(@RequestHeader Long clienteId){
        Boolean delete = clienteService.delete(clienteId);
            if (delete){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
    }


}
