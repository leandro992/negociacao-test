package br.com.rest.builders.negociacao.repository;

import br.com.rest.builders.negociacao.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {


    Optional<Cliente> findByNomeAndCpf(String nome, String cpf);
}
