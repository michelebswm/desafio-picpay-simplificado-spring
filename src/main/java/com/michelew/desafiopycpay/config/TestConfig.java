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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User(null, "Michele", "michele@gmail.com", "123456", TypePerson.FISICA, "12532564581", TypeUser.COMMON);

        userRepository.save(u1);

        System.out.println(u1);

        Wallet w1 = new Wallet();
        w1.setAccount("12389");
        w1.setUser(u1);
        w1.setBalance(0.0);

        System.out.println(w1);
        walletRepository.save(w1);
    }
}
