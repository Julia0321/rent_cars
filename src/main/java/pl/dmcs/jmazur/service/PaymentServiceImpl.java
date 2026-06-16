package pl.dmcs.jmazur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dmcs.jmazur.dto.PaymentDto;
import pl.dmcs.jmazur.mapper.PaymentMapper;
import pl.dmcs.jmazur.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDto findPaymentByUUID(String paymentUUID) {
        return paymentMapper.mapToDto(paymentRepository.findPaymentByUuid(paymentUUID)
                .orElseThrow(() -> new RuntimeException("Payment not found")));
    }
}
