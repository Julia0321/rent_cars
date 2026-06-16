package pl.dmcs.jmazur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarStatusScheduler {

    private final CarStatusService carStatusService;

    @Scheduled(fixedRate = 60000)
    public void updateCarStatuses() {
        carStatusService.refreshStatuses();
    }
}
