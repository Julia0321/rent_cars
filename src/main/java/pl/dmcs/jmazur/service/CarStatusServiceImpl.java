package pl.dmcs.jmazur.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dmcs.jmazur.repository.CarRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CarStatusServiceImpl implements CarStatusService {

    private final CarRepository carRepository;

    @Transactional
    public void refreshStatuses() {

        LocalDate today = LocalDate.now();
        carRepository.setRentedForToday(today);
        carRepository.setAvailableIfNotRentedToday(today);
    }
}
