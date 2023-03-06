package com.flexpag.paymentscheduler.controllers;

import com.flexpag.paymentscheduler.dtos.InvoiceDTO;
import com.flexpag.paymentscheduler.models.Invoice;
import com.flexpag.paymentscheduler.services.InvoiceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/invoice")
public class InvoiceController {

    final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
    @ApiOperation(value = "Retorna todas as faturas.")
    @GetMapping("/allInvoices")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return invoiceService.findAll();
    }

    @ApiOperation(value = "Retorna todas as faturas com status PENDING.")
    @GetMapping("/allInvoicesPendingStatus")
    public ResponseEntity<List<Invoice>> getAllInvoicesPendingStatus() {
        return invoiceService.findAllInvoicesPendingStatus();
    }
    @ApiOperation(value = "Retorna todas as faturas com status PAID.")
    @GetMapping("/allInvoicesPaidStatus")
    public ResponseEntity<List<Invoice>> getAllInvoicesPaidStatus() {
        return invoiceService.findAllInvoicesPaidStatus();
    }
    @ApiOperation(value = "Retorna todas as faturas com status WAITING SCHEDULE.")
    @GetMapping("/allInvoicesWaitingScheduleStatus")
    public ResponseEntity<List<Invoice>> getAllInvoicesWaitingScheduleStatus() {
        return invoiceService.findAllInvoicesWaitingScheduleStatus();
    }

    @ApiOperation(value = "Realiza o agendamento da fatura.")
    @PostMapping("/schedulePayment")
    public ResponseEntity<Object> schedulePayment(@RequestBody @Valid InvoiceDTO invoiceDTO) {
        return invoiceService.schedulePayment(invoiceDTO);
    }

    @ApiOperation(value = "Atualiza o agendamento da fatura.")
    @PutMapping("/updateSchedulePayment")
    public ResponseEntity<Object> updateSchedulePayment(@RequestBody @Valid InvoiceDTO invoiceDTO) {
        return invoiceService.updateSchedulePayment(invoiceDTO);
    }
}
