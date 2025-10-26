package com.aps4.APS4.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Collection<Cliente> listarClientes() {
        return repository.findAll();
    }


    public Cliente buscarCliente(String cpf) {
        return repository.findByCpf(cpf);
    }


    public Cliente cadastrarCliente(Cliente cliente) {
        if (cliente == null || cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CPF do cliente não pode ser nulo ou vazio");
        }

        Cliente existente = repository.findByCpf(cliente.getCpf());
        if (existente != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um cliente com esse CPF");
        }

        repository.save(cliente);
        return cliente;
    }


    public Cliente editarCliente(Cliente novosDados) {
        Cliente existente = repository.findByCpf(novosDados.getCpf());
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não existe um cliente com esse CPF cadastrado");
        }

        if (novosDados.getNome() != null) {
            existente.setNome(novosDados.getNome());
        }
        if (novosDados.getDataNascimento() != null) {
            existente.setDataNascimento(novosDados.getDataNascimento());
        }
        Cliente atualizado = repository.save(existente);
        return atualizado;
    }

    public void deletarCliente(String cpf) {
        Cliente cliente = buscarCliente(cpf);
        if (cliente != null) {
            repository.deleteById(cliente.getId());
        }
    }
}