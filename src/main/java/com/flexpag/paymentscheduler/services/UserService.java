package com.flexpag.paymentscheduler.services;

import com.flexpag.paymentscheduler.models.User;
import com.flexpag.paymentscheduler.repositories.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Service
public class UserService {

    final IUserRepository iUserRepository;
    final InvoiceService invoiceService;

    public UserService(IUserRepository iUserRepository, InvoiceService invoiceService) {
        this.iUserRepository = iUserRepository;
        this.invoiceService = invoiceService;
    }

    @Transactional
    public ResponseEntity<Object> createTest() {
        User user = this.createUser();
        if (Objects.nonNull(user)) {
            invoiceService.createInvoicesToUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("Teste criado!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O teste já foi executado!");
        }
    }

    private User createUser() {
        if (Boolean.FALSE.equals(iUserRepository.existsUserByRegistrationNumber("09849667494"))) {
            var user = new User();
            user.setName("Gilberto");
            user.setRegistrationNumber("09849667494");
            user.setDhCreation(LocalDateTime.now(ZoneId.of("UTC")));
            user.setDhModification(LocalDateTime.now(ZoneId.of("UTC")));
            return iUserRepository.save(user);
        } else {
            return null;
        }
    }


}
