package com.flexpag.paymentscheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexpag.paymentscheduler.controllers.PaymentSchedulerController;
import com.flexpag.paymentscheduler.models.PaymentSchedulerModel;
import com.flexpag.paymentscheduler.services.PaymentSchedulerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentSchedulerController.class)
public class PaymentSchedulerControllerTest {

    @MockBean
    private PaymentSchedulerService paymentSchedulerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void postScheduling() throws Exception {

        PaymentSchedulerModel paymentSchedulerModel = createScheduling();

        Mockito.when(paymentSchedulerService.save(paymentSchedulerModel)).thenReturn(UUID.randomUUID());
        mockMvc.perform(post("/payment-scheduler").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentSchedulerModel)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void getAllScheduling() throws Exception {

        List<PaymentSchedulerModel> list = new ArrayList<PaymentSchedulerModel>();

        Mockito.when(paymentSchedulerService.findAll()).thenReturn(list);
        mockMvc.perform(get("/payment-scheduler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(list.size()));
    }

    @Test
    public void getOneScheduling() throws Exception {
        UUID id = UUID.randomUUID();
        PaymentSchedulerModel paymentSchedulerModel = new PaymentSchedulerModel();
        paymentSchedulerModel.setId(id);
        paymentSchedulerModel.setStatus("pending");
        paymentSchedulerModel.setCreated_at(LocalDateTime.now());
        paymentSchedulerModel.setSchedulingDate(LocalDateTime.now());
        paymentSchedulerModel.setNamePayment("Test get one scheduling");

        Mockito.when(paymentSchedulerService.findById(id)).thenReturn(Optional.of(paymentSchedulerModel));
        mockMvc.perform(get("/payment-scheduler/{id}",id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteScheduling() throws Exception {
        UUID id = UUID.randomUUID();
        PaymentSchedulerModel paymentSchedulerModel = new PaymentSchedulerModel();

        Mockito.when(paymentSchedulerService.findById(id)).thenReturn(Optional.of(paymentSchedulerModel));
        mockMvc.perform(delete("/payment-scheduler/{id}",id))
                .andExpect(status().isNoContent())
                .andDo(print());

        Mockito.verify(paymentSchedulerService, Mockito.times(1)).delete(paymentSchedulerModel);
    }

    @Test
    public void updateScheduling() throws Exception {
        UUID id = UUID.randomUUID();
        PaymentSchedulerModel paymentSchedulerModel = new PaymentSchedulerModel();
        paymentSchedulerModel.setId(id);
        paymentSchedulerModel.setStatus("pending");
        paymentSchedulerModel.setCreated_at(LocalDateTime.now());
        paymentSchedulerModel.setSchedulingDate(LocalDateTime.now());
        paymentSchedulerModel.setNamePayment("Test get one scheduling");

        Mockito.when(paymentSchedulerService.findById(id)).thenReturn(Optional.of(paymentSchedulerModel));
        Mockito.when(paymentSchedulerService.update(paymentSchedulerModel)).thenReturn(paymentSchedulerModel);
        mockMvc.perform(patch("/payment-scheduler/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUpdateScheduling())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // TEST ERRORS

    @Test
    public void errorNotFoundGetOneScheduling() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(paymentSchedulerService.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/payment-scheduler/{id}",id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void errorNotFoundDeleteScheduling() throws Exception {
        UUID id = UUID.randomUUID();
        PaymentSchedulerModel paymentSchedulerModel = new PaymentSchedulerModel();

        Mockito.when(paymentSchedulerService.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/payment-scheduler/{id}",id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void errorBadRequestUpdateScheduling() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(paymentSchedulerService.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(patch("/payment-scheduler/{id}",id))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void errorStatusPaidDeleteScheduling() throws Exception {
        UUID id = UUID.randomUUID();
        PaymentSchedulerModel paymentSchedulerModel = new PaymentSchedulerModel();
        paymentSchedulerModel.setId(id);
        paymentSchedulerModel.setStatus("paid");

        Mockito.when(paymentSchedulerService.findById(id)).thenReturn(Optional.of(paymentSchedulerModel));
        mockMvc.perform(delete("/payment-scheduler/{id}",id))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void errorStatusPaidUpdateScheduling() throws Exception {
        UUID id = UUID.randomUUID();
        PaymentSchedulerModel paymentSchedulerModel = new PaymentSchedulerModel();
        paymentSchedulerModel.setId(id);
        paymentSchedulerModel.setStatus("paid");

        Mockito.when(paymentSchedulerService.findById(id)).thenReturn(Optional.of(paymentSchedulerModel));
        mockMvc.perform(patch("/payment-scheduler/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUpdateScheduling())))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // HELPER METHODS

    private PaymentSchedulerModel createUpdateScheduling() {
        PaymentSchedulerModel paymentSchedulerModel = new PaymentSchedulerModel();
        paymentSchedulerModel.setSchedulingDate(LocalDateTime.of(3150,12,29,10,32));
        return paymentSchedulerModel;
    }

    private PaymentSchedulerModel createScheduling() {
        PaymentSchedulerModel paymentSchedulerModel = new PaymentSchedulerModel();
        paymentSchedulerModel.setNamePayment("Buy car");
        paymentSchedulerModel.setSchedulingDate(LocalDateTime.of(3150,12,29,10,32));
        return paymentSchedulerModel;
    }

}
