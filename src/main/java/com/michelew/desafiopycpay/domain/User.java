package com.michelew.desafiopycpay.domain;

import com.michelew.desafiopycpay.domain.enums.TypePerson;
import com.michelew.desafiopycpay.domain.enums.TypeUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity(name = "tb_user")
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

    @OneToOne(mappedBy = "user")
    private Wallet wallet;

    public User(Long id, String name, String email, String password, TypePerson tipoPessoa, String numeroDocumento, TypeUser typeUser) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tipoPessoa = tipoPessoa;
        this.typeUser = typeUser;

        if (tipoPessoa.getCode() == 1) {
            validarCNPJ(numeroDocumento);
            this.numeroDocumento = numeroDocumento;
        } else if (tipoPessoa.getCode() == 2) {
            validaCPF(numeroDocumento);
            this.numeroDocumento = numeroDocumento;
        }
    }

    public void validaCPF(String cpf) {
        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("Invalid CPF.");
        }
    }

    private void validarCNPJ(String cnpj) {
        if (!cnpj.matches("\\d{14}")) {
            throw new IllegalArgumentException("Invalid CNPJ.");
        }
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
