package com.flexpag.paymentscheduler.components;

import com.flexpag.paymentscheduler.models.PaymentSchedulerModel;
import com.flexpag.paymentscheduler.repositories.PaymentSchedulerRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class PaymentSchedulerComponent {

    final PaymentSchedulerRepository paymentSchedulerRepository;
    

    public PaymentSchedulerComponent(PaymentSchedulerRepository paymentSchedulerRepository) {
        this.paymentSchedulerRepository = paymentSchedulerRepository;
    }

    // verificar a data do agendamento e atualizar o status
    @Scheduled(fixedDelay = 5000)
    public void verifyScheduling() {

        List<PaymentSchedulerModel> allScheduling = paymentSchedulerRepository.findAll();

        if (allScheduling.isEmpty() == false) {

            for (int i = 0; i < allScheduling.size(); i++) {
                var scheduling = allScheduling.get(i);

                long verifyDays = ChronoUnit.SECONDS.between(LocalDateTime.now(), scheduling.getSchedulingDate());

                if (verifyDays < 0) {

                    if (scheduling.getStatus() == "pending") {
                        scheduling.setStatus("paid");
                        paymentSchedulerRepository.save(scheduling);
                    }
                }
            }
        }

    }
}