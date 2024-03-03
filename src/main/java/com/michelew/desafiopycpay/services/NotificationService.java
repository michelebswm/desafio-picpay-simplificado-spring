package com.michelew.desafiopycpay.services;

import com.michelew.desafiopycpay.domain.User;
import com.michelew.desafiopycpay.dto.NotificationDTO;
import com.michelew.desafiopycpay.services.exceptions.NotificationExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message){
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);
        ResponseEntity<String> responseNotification= restTemplate.postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", notificationRequest, String.class);
        if (!(responseNotification.getStatusCode() == HttpStatus.OK)){
            System.out.println("ERRO AO ENVIAR NOTIFICAÇÃO");
            throw new NotificationExeption("Serviço de notificação está fora do ar");
        }
    }
}
