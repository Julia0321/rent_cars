package pl.dmcs.jmazur.service;

import jakarta.servlet.http.HttpServletResponse;
import pl.dmcs.jmazur.dto.PaymentDto;

public interface PdfService {

    void generatePdf(PaymentDto payment, HttpServletResponse response);
}
