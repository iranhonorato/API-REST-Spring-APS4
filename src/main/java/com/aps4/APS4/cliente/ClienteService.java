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


    public Optional<Cliente> buscarCliente(Integer id) {
        return repository.findById(id);
    }


    public Cliente cadastrarCliente(Cliente cliente) {
        if (cliente == null || cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CPF do cliente não pode ser nulo ou vazio");
        }

        Optional<Cliente> existente = repository.findByCpf(cliente.getCpf());
        if (existente != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um cliente com esse CPF");
        }

        repository.save(cliente);
        return cliente;
    }


    public Cliente editarCliente(Cliente novosDados) {
        Cliente existente = repository.findById(novosDados.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado com ID:");
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
}