package pl.dmcs.jmazur.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.dmcs.jmazur.enums.ReservationStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(name = "reservationFrom", nullable = false)
    private LocalDate from;

    @Column(name = "reservationTo", nullable = false)
    private LocalDate to;

    @Column(name = "totalPrice")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    @ToString.Exclude
    private Car car;

    @Column(name = "payment_deadline", nullable = false)
    private LocalDateTime paymentDeadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Payment payment;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
        if (status == null) {
            status = ReservationStatusEnum.PENDING;
        }
        if (paymentDeadline == null) {
            paymentDeadline = LocalDateTime.now().plusMinutes(2);
        }
    }
}
