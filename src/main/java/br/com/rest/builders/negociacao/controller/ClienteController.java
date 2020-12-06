package br.com.rest.builders.negociacao.controller;

import br.com.rest.builders.negociacao.model.Cliente;
import br.com.rest.builders.negociacao.model.dto.ClienteRequestDTO;
import br.com.rest.builders.negociacao.model.dto.ClienteResponseDTO;
import br.com.rest.builders.negociacao.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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
            if (delete) return ResponseEntity.ok().build();
            return ResponseEntity.notFound().build();
    }


    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateParcial(@RequestHeader String clienteId, @RequestBody Map<String,Object> camposOrigem){
        Long clienteIdLong = Long.valueOf(clienteId);
        Optional<Cliente> consult = clienteService.consult(clienteIdLong);

        if (consult.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        merge(camposOrigem, consult.get());
        return update(consult.get(),clienteIdLong);
    }


    private void merge(Map<String, Object> camposOrigem, Cliente clienteDestino){
        ObjectMapper objectMapper = new ObjectMapper();

        Cliente cliente = objectMapper.convertValue(camposOrigem, Cliente.class);

        camposOrigem.forEach((nomePropriedade, valorPropriedade) ->{
            Field field = ReflectionUtils.findField(Cliente.class, nomePropriedade);
            field.setAccessible(Boolean.TRUE);

            Object novoValor = ReflectionUtils.getField(field, cliente);

            ReflectionUtils.setField(field, clienteDestino, novoValor);
        });
    }






}
