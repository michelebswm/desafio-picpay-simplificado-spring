package com.michelew.desafiopycpay.controllers;

import com.michelew.desafiopycpay.domain.User;
import com.michelew.desafiopycpay.dto.UserIdResponseDTO;
import com.michelew.desafiopycpay.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = service.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = service.findById(id);
        return ResponseEntity.ok().body(user);
    }


    @PostMapping
    public ResponseEntity<UserIdResponseDTO> insert(@RequestBody @Valid User userData) {
        if (userData.getTipoPessoa().getCode() == 1) {
            service.validarCNPJ(userData.getNumeroDocumento());
        } else if (userData.getTipoPessoa().getCode() == 2) {
            service.validaCPF(userData.getNumeroDocumento());
        }
        User newUser = service.insert(userData);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
        UserIdResponseDTO responseDTO = new UserIdResponseDTO(newUser.getId());
        return ResponseEntity.created(uri).body(responseDTO);

    }

}
