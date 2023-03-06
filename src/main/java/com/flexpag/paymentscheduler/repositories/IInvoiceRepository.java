package com.flexpag.paymentscheduler.repositories;

import com.flexpag.paymentscheduler.enums.InvoiceStatus;
import com.flexpag.paymentscheduler.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Integer> {

    Invoice findInvoiceByBarcode(String barCode);

    List<Invoice> findAllByInvoiceStatus(InvoiceStatus invoiceStatus);
}
