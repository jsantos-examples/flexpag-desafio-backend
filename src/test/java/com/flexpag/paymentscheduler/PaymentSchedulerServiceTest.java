package com.flexpag.paymentscheduler;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.flexpag.paymentscheduler.models.PaymentSchedulerModel;
import com.flexpag.paymentscheduler.repositories.PaymentSchedulerRepository;
import com.flexpag.paymentscheduler.services.PaymentSchedulerService;

@DisplayName("PaymentSchedulerServiceTest")
public class PaymentSchedulerServiceTest extends AplicationConfigTest {

    @MockBean
    private PaymentSchedulerRepository paymentSchedulerRepository;

    @Autowired
    private PaymentSchedulerService paymentSchedulerService;


    @Test
    public void saveScheduling() {
        PaymentSchedulerModel paymentSchedulerModel = new PaymentSchedulerModel();
        Mockito.when(paymentSchedulerRepository.save(paymentSchedulerModel)).thenReturn(createScheduling());
        paymentSchedulerService.save(paymentSchedulerModel);
    }

    @Test
    public void findAllScheduling() {
        List<PaymentSchedulerModel> list = new ArrayList<PaymentSchedulerModel>();
        Mockito.when(paymentSchedulerRepository.findAll()).thenReturn(list);
        paymentSchedulerService.findAll();
    }

    @Test
    public void findOneScheduling() {
        UUID schedulingId = UUID.randomUUID();

        Optional<PaymentSchedulerModel> paymentSchedulerModel = Optional.of(createScheduling());
        Mockito.when(paymentSchedulerRepository.findById(schedulingId)).thenReturn(paymentSchedulerModel);

        paymentSchedulerService.findById(schedulingId);
    }

    @Test
    public void deleteScheduling() {
        PaymentSchedulerModel paymentSchedulerModel = createScheduling();
        paymentSchedulerService.delete(paymentSchedulerModel);
        Mockito.verify(paymentSchedulerRepository, Mockito.times(1)).delete(paymentSchedulerModel);
    }

    @Test
    public void updateScheduling() {
        PaymentSchedulerModel paymentSchedulerModel = createScheduling();
        Mockito.when(paymentSchedulerRepository.save(paymentSchedulerModel)).thenReturn(createScheduling());
        paymentSchedulerService.update(paymentSchedulerModel);
    }

    private PaymentSchedulerModel createScheduling() {
        PaymentSchedulerModel paymentSchedulerModel = new PaymentSchedulerModel();
        paymentSchedulerModel.setNamePayment("Buy car");
        paymentSchedulerModel.setSchedulingDate(LocalDateTime.now());
        paymentSchedulerModel.setCreated_at(LocalDateTime.now());
        paymentSchedulerModel.setStatus("pending");
        paymentSchedulerModel.setId(UUID.randomUUID());
        return paymentSchedulerModel;
    }
}
