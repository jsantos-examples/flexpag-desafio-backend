package com.flexpag.paymentscheduler.services;

import com.flexpag.paymentscheduler.dtos.InvoiceDTO;
import com.flexpag.paymentscheduler.enums.InvoiceStatus;
import com.flexpag.paymentscheduler.models.Invoice;
import com.flexpag.paymentscheduler.models.User;
import com.flexpag.paymentscheduler.repositories.IInvoiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class InvoiceService {

    final IInvoiceRepository iInvoiceRepository;

    public InvoiceService(IInvoiceRepository iInvoiceRepository) {
        this.iInvoiceRepository = iInvoiceRepository;
    }

    public ResponseEntity<List<Invoice>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(iInvoiceRepository.findAll());
    }

    @Transactional
    public ResponseEntity<Object> schedulePayment(@NotNull InvoiceDTO invoiceDTO) {
        if (Objects.nonNull(invoiceDTO.getPaymentSchedule())) {
            var invoice = iInvoiceRepository.findInvoiceByBarcode(invoiceDTO.getBarCode());
            if (Objects.isNull(invoice)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fatura não existe, não será possível realizar o agendamento!");
            } else if (Objects.nonNull(invoice.getPaymentSchedule())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Para atualização ou remoção utilizar o método updateSchedulePayment.");
            } else if (invoice.getDhMaturity().isBefore(invoiceDTO.getPaymentSchedule())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data do agendamento não pode ser maior que a data do vencimento.");
            } else {
                if (Boolean.TRUE.equals(invoiceDTO.getRegistrationNumber().equals(invoice.getUser().getRegistrationNumber()))) {
                    invoice.setInvoiceStatus(InvoiceStatus.PENDING);
                    invoice.setPaymentSchedule(invoiceDTO.getPaymentSchedule());
                    return ResponseEntity.status(HttpStatus.OK).body("id: " + (iInvoiceRepository.save(invoice)).getId());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fatura não existe para este CPF, não será possível realizar o agendamento!");
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo com agendamento não poderá ser nulo, para atualização ou remoção utilizar o método updateSchedulePayment.");
        }
    }

    @Transactional
    public void createInvoicesToUser(User user) {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("UTC"));
        dateTime.minusMonths(5);
        final Double value = 20000.00;

        for (int i = 0; i < 15; i++) {

            var invoice = new Invoice();
            invoice.setUser(user);
            invoice.setDhMaturity(dateTime);
            invoice.setDhCreation(dateTime.minusDays(10));
            invoice.setDhModification(dateTime.minusDays(10));
            invoice.setValue(value);

            if (i == 1 || i == 3) {
                invoice.setPaymentSchedule(dateTime.minusDays(5));
                invoice.setInvoiceStatus(InvoiceStatus.PAID);
            } else if (i < 5) {
                invoice.setInvoiceStatus(InvoiceStatus.OVERDUE);
            } else {
                invoice.setInvoiceStatus(InvoiceStatus.WAITING_SCHEDULE);
            }

            invoice.setBarcode(this.getBarCodeGenerator());
            iInvoiceRepository.save(invoice);
            dateTime = dateTime.plusMonths(1);
        }
    }

    private String getBarCodeGenerator() {
        Random random = new Random();
        StringBuilder barCode = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            barCode.append(random.nextInt(10));
        }
        return barCode.toString();
    }

    public ResponseEntity<List<Invoice>> findAllInvoicesPendingStatus() {
        return ResponseEntity.status(HttpStatus.OK).body(iInvoiceRepository.findAllByInvoiceStatus(InvoiceStatus.PENDING));
    }

    public ResponseEntity<List<Invoice>> findAllInvoicesPaidStatus() {
        return ResponseEntity.status(HttpStatus.OK).body(iInvoiceRepository.findAllByInvoiceStatus(InvoiceStatus.PAID));
    }

    public ResponseEntity<List<Invoice>> findAllInvoicesWaitingScheduleStatus() {
        return ResponseEntity.status(HttpStatus.OK).body(iInvoiceRepository.findAllByInvoiceStatus(InvoiceStatus.WAITING_SCHEDULE));
    }

    @Transactional
    public ResponseEntity<Object> updateSchedulePayment(InvoiceDTO invoiceDTO) {
        var invoice = iInvoiceRepository.findInvoiceByBarcode(invoiceDTO.getBarCode());
        if (Objects.isNull(invoice.getPaymentSchedule())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A fatura não possui agendamento, para realizar um agendamento utilizar o método schedulePayment.");
        } else if (InvoiceStatus.PAID.equals(invoice.getInvoiceStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pagamento já realizado, o agendamento não pode ser alterado.");
        }
        if (Objects.nonNull(invoiceDTO.getPaymentSchedule()) && invoice.getDhMaturity().isBefore(invoiceDTO.getPaymentSchedule())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data do agendamento não pode ser maior que a data do vencimento");
        } else {
            invoice.setPaymentSchedule(invoiceDTO.getPaymentSchedule());
            invoice.setInvoiceStatus(InvoiceStatus.WAITING_SCHEDULE);
            iInvoiceRepository.save(invoice);
            return ResponseEntity.status(HttpStatus.OK).body("Agendamento atualizado!");
        }
    }
}
