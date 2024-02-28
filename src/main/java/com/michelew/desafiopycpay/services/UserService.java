package com.michelew.desafiopycpay.services;

import com.michelew.desafiopycpay.domain.User;
import com.michelew.desafiopycpay.repositories.UserRepository;
import com.michelew.desafiopycpay.services.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User insert(User newUser){
        if (repository.existsByEmailOrNumeroDocumento(newUser.getEmail(), newUser.getNumeroDocumento())){
            throw new UserAlreadyExistsException("User with the same email or document number already exists");
        }
        return repository.save(newUser);
    }
}
