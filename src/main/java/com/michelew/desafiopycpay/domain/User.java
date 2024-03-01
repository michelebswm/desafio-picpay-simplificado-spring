package com.michelew.desafiopycpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michelew.desafiopycpay.domain.enums.TypePerson;
import com.michelew.desafiopycpay.domain.enums.TypeUser;
import com.michelew.desafiopycpay.services.exceptions.UserDocumentInvalidException;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String numeroDocumento;

    private TypePerson tipoPessoa;

    private TypeUser typeUser;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Wallet wallet;

    public User(Long id, String name, String email, String password, TypePerson tipoPessoa, String numeroDocumento, TypeUser typeUser) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tipoPessoa = tipoPessoa;
        this.typeUser = typeUser;
        this.numeroDocumento = numeroDocumento;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(numeroDocumento, user.numeroDocumento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, numeroDocumento);
    }
}
