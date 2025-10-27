package com.aps4.APS4.cliente;

import com.aps4.APS4.cartao.dto.CartaoResponseDTO;
import com.aps4.APS4.cliente.dto.ClienteRequestDTO;
import com.aps4.APS4.cliente.dto.ClienteResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;



    public List<ClienteResponseDTO> listarClientes() {
        return repository.findAll()
                .stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());
    }



    public ClienteResponseDTO buscarCliente(String cpf) {
        Cliente cliente = repository.findByCpf(cpf);
        if (cliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.");
        }
        return new ClienteResponseDTO(cliente);
    }


    @Transactional
    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO dto) {
        if (dto == null || dto.cpf() == null || dto.cpf().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CPF do cliente não pode ser nulo ou vazio");
        }

        Cliente existente = repository.findByCpf(dto.cpf());
        if (existente != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um cliente com esse CPF");
        }

        Cliente cliente = new Cliente();
        cliente.setCpf(dto.cpf());
        cliente.setNome(dto.nome());
        cliente.setDataNascimento(dto.dataNascimento());
        cliente.setSalario(dto.salario());


        Cliente salvo = repository.save(cliente);
        return new ClienteResponseDTO(salvo);
    }

//  PUT - Editar informações de um cliente
    @Transactional
    public ClienteResponseDTO editarCliente(ClienteRequestDTO dto) {
        Cliente existente = repository.findByCpf(dto.cpf());
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não existe um existente com esse CPF cadastrado");
        }

        if (dto.nome() != null && !dto.nome().isBlank()) {
            existente.setNome(dto.nome());
        }
        if (dto.dataNascimento() != null) {
            existente.setDataNascimento(dto.dataNascimento());
        }
        if (dto.salario() != null) {
            existente.setSalario(dto.salario());
        }

        Cliente atualizado = repository.save(existente);
        return new ClienteResponseDTO(atualizado);
    }


//  DELETE - Deletar um usuário
    public ClienteResponseDTO deletarCliente(String cpf) {
        Cliente existente = repository.findByCpf(cpf);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.");
        }
        repository.delete(existente);
        return new ClienteResponseDTO(existente);
    }
}