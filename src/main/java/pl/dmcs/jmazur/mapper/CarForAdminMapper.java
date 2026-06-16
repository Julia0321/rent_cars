package pl.dmcs.jmazur.mapper;

import org.springframework.stereotype.Component;
import pl.dmcs.jmazur.domain.Car;
import pl.dmcs.jmazur.dto.CarForAdminDto;

import java.util.UUID;

@Component
public class CarForAdminMapper {

    public CarForAdminDto mapToDto(Car car) {
        return CarForAdminDto.builder()
                .uuid(car.getUuid())
                .brand(car.getBrand())
                .model(car.getModel())
                .fuel(car.getFuel())
                .transmission(car.getTransmission())
                .pricePerDay(car.getPricePerDay())
                .productionNumber(car.getProductionNumber())
                .registrationNumber(car.getRegistrationNumber())
                .carStatus(car.getCarStatus())
                .build();
    }

    public Car mapToCar(CarForAdminDto carDto) {
        return Car.builder()
                .uuid(UUID.randomUUID().toString())
                .brand(carDto.getBrand())
                .model(carDto.getModel())
                .fuel(carDto.getFuel())
                .transmission(carDto.getTransmission())
                .productionNumber(carDto.getProductionNumber())
                .registrationNumber(carDto.getRegistrationNumber())
                .pricePerDay(carDto.getPricePerDay())
                .carStatus(carDto.getCarStatus())
                .build();
    }
}
