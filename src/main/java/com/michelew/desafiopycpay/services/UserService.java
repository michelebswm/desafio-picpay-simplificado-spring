package com.michelew.desafiopycpay.services;

import com.michelew.desafiopycpay.domain.User;
import com.michelew.desafiopycpay.repositories.UserRepository;
import com.michelew.desafiopycpay.services.exceptions.ResourceNotFoundException;
import com.michelew.desafiopycpay.services.exceptions.UserAlreadyExistsException;
import com.michelew.desafiopycpay.services.exceptions.UserDocumentInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id){
        Optional<User>optionalUser = repository.findById(id);
        return optionalUser.orElseThrow(()-> new ResourceNotFoundException(id));
    }

    public User insert(User newUser){
        if (repository.existsByEmailOrNumeroDocumento(newUser.getEmail(), newUser.getNumeroDocumento())){
            throw new UserAlreadyExistsException("User with the same email or document number already exists");
        }
        return repository.save(newUser);
    }
    public void validaCPF(String cpf) {
        if (!cpf.matches("\\d{11}")) {
            throw new UserDocumentInvalidException("The CPF number provided is invalid. Make sure you enter a valid 11-digit CPF.");
        }
    }

    public void validarCNPJ(String cnpj) {
        if (!cnpj.matches("\\d{14}")) {
            throw new UserDocumentInvalidException("The CNPJ number provided is invalid. Make sure you enter a valid 14-digit CNPJ.");
        }
    }
}
