package com.michelew.desafiopycpay.config;

import com.michelew.desafiopycpay.domain.User;
import com.michelew.desafiopycpay.domain.Wallet;
import com.michelew.desafiopycpay.domain.enums.TypePerson;
import com.michelew.desafiopycpay.domain.enums.TypeUser;
import com.michelew.desafiopycpay.repositories.UserRepository;
import com.michelew.desafiopycpay.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {


    }
}
