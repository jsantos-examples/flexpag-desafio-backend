package com.flexpag.paymentscheduler.servirce;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.flexpag.paymentscheduler.model.Payment;
import com.flexpag.paymentscheduler.model.dto.StatusPaymentsDto;
import com.flexpag.paymentscheduler.repository.PaymentRepository;
import com.flexpag.paymentscheduler.status.Status;

@Service
public class PaymentService {

	@Autowired
	PaymentRepository paymentRepository;
	
	
	public List<Payment> listAll(){
		return paymentRepository.findAll();
	}
	
	public Payment create(@RequestBody Payment payment) {

		Payment object = new Payment(null,payment.getName(),payment.getMoney(), LocalDateTime.now());
        return paymentRepository.save(object);
    }
	
	public Payment listById(Long id){
		Optional<Payment> payment = paymentRepository.findById(id);
		return payment.get();
	}
	
	 public void delete(Long id) {
	        Optional<Payment> payment = paymentRepository.findById(id);
	        if (payment.get().getStatus() == Status.PENDING) {
	            paymentRepository.deleteById(id);
	        } 
	    }
	 
	 public StatusPaymentsDto listStatusId (@PathVariable Long id) {
			Optional<Payment> payment = paymentRepository.findById(id);
			
			return new StatusPaymentsDto(payment.get().getId(), payment.get().getStatus());
		}
	 
	 public Payment update(@PathVariable Long id, @RequestBody Payment payment){
		 
			payment.setId(id);
			return paymentRepository.save(payment);
			
			}
}
