package pl.dmcs.jmazur.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import pl.dmcs.jmazur.domain.Reservation;
import pl.dmcs.jmazur.dto.PaymentDto;
import pl.dmcs.jmazur.repository.ReservationRepository;

import java.io.IOException;
import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {

    final private ReservationRepository reservationRepository;
    private final MessageSource messageSource;

    private String msg(String key) {

        return messageSource.getMessage(key, null, key, LocaleContextHolder.getLocale());
    }

    @Override
    public void generatePdf(PaymentDto payment, HttpServletResponse response) {
        Reservation reservation =
                reservationRepository.findReservationByUuid(
                                payment.getReservationUUID())
                        .orElseThrow(() -> new RuntimeException(
                                        "Reservation not found for uuid=" + payment.getReservationUUID()
                                )
                        );

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=bill- " + payment.getUuid() + ".pdf");

        try {
            OutputStream os = response.getOutputStream();

            Document pdf = new Document();
            PdfWriter.getInstance(pdf, os);

            pdf.open();

            pdf.add(new Paragraph(msg("pdf.title")));
            pdf.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(2);

            table.addCell(msg("label.email"));
            table.addCell(reservation.getUser().getEmail());

            table.addCell(msg("label.birthday"));
            table.addCell(String.valueOf(reservation.getUser().getDateOfBirth()));

            table.addCell(msg("pdf.car.brand"));
            table.addCell(reservation.getCar().getBrand());

            table.addCell(msg("pdf.car.model"));
            table.addCell(reservation.getCar().getModel());

            table.addCell(msg("pdf.status"));
            table.addCell(payment.getStatus().toString());

            table.addCell(msg("pdf.paymentDate"));
            table.addCell(String.valueOf(payment.getPaymentDate()));

            table.addCell(msg("pdf.amount"));
            table.addCell(payment.getAmount() + " PLN");

            pdf.add(table);
            pdf.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
