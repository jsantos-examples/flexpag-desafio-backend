package com.flexpag.paymentscheduler.service;

import com.flexpag.paymentscheduler.dto.PaymentDto;
import com.flexpag.paymentscheduler.dto.PaymentRequestDto;
import com.flexpag.paymentscheduler.entity.Payment;
import com.flexpag.paymentscheduler.entity.PaymentStatus;
import com.flexpag.paymentscheduler.mapper.PaymentMapper;
import com.flexpag.paymentscheduler.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentDto checkPayment(Long id) {
        Payment savedPayment = getPayment(id);
        return paymentMapper.mapToPaymentDto(savedPayment);
    }


    public PaymentDto save(PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentMapper.mapToPayment(paymentRequestDto);
        Payment savedPayment = paymentRepository.save(payment);
        paymentRequestDto.setId(savedPayment.getId());
        return paymentRequestDto;
    }

    public List<PaymentDto> findAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::mapToPaymentDto)
                .collect(Collectors.toList());
    }

    public PaymentDto updatePayDate(Long id, LocalDateTime localDateTime) {
        Payment savedPayment = getPayment(id);
        savedPayment.setPayDate(localDateTime);
        paymentRepository.save(savedPayment);
        return paymentMapper.mapToPaymentDto(savedPayment);
    }

    public void updatePaymentStatus(Long id) {
        Payment savedPayment = getPayment(id);
        savedPayment.setPaymentStatus(PaymentStatus.PAID);
        paymentRepository.save(savedPayment);
    }

    public void delete(Long id) {
        if (getPayment(id).getPaymentStatus() != PaymentStatus.PENDING) {
            throw new RuntimeException("Cannot remove PAID payment");
        }
        paymentRepository.deleteById(id);
    }

    private Payment getPayment(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find Payment with id " + id));
    }

}
