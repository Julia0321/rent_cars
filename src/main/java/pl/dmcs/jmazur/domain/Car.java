package pl.dmcs.jmazur.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.dmcs.jmazur.enums.CarStatusEnum;
import pl.dmcs.jmazur.enums.FuelEnum;
import pl.dmcs.jmazur.enums.TransmissionEnum;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "car")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel")
    private FuelEnum fuel;

    @Enumerated(EnumType.STRING)
    @Column(name = "transimission")
    private TransmissionEnum transmission;

    @Column(name = "production_number", nullable = false)
    private String productionNumber;

    @Column(name = "registration_number", unique = true, nullable = false)
    private String registrationNumber;

    @Column(name = "price_per_day", nullable = false)
    private BigDecimal pricePerDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_status", nullable = false)
    private CarStatusEnum carStatus;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Reservation> reservations = new HashSet<>();

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
            carStatus = CarStatusEnum.AVAILABLE;
        }
    }
}
