package pl.dmcs.jmazur.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.dmcs.jmazur.dto.PaymentDto;
import pl.dmcs.jmazur.enums.PaymentStatusEnum;
import pl.dmcs.jmazur.service.PaymentService;
import pl.dmcs.jmazur.service.PdfService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;
    private final PaymentService paymentService;

    @RequestMapping(value = "/generatePdf-{paymentUUID}", method = RequestMethod.GET)
    public void generatePdf(@PathVariable("paymentUUID") String paymentUUID,
                            HttpServletResponse response) throws IOException {

        try {
            PaymentDto payment = paymentService.findPaymentByUUID(paymentUUID);

            if (payment == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "error.payment.not.found");
                return;
            }

            if (payment.getStatus() != PaymentStatusEnum.PAID) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "error.invoice.available");
                return;
            }

            pdfService.generatePdf(payment, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "error.pdf.generation");
        }
    }
}
