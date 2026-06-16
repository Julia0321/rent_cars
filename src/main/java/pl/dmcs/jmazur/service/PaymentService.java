package pl.dmcs.jmazur.service;

import pl.dmcs.jmazur.dto.PaymentDto;

public interface PaymentService {

    PaymentDto findPaymentByUUID(String paymentUUID);
}
