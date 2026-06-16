package pl.dmcs.jmazur.mapper;

import org.springframework.stereotype.Component;
import pl.dmcs.jmazur.domain.Car;
import pl.dmcs.jmazur.dto.CarDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper {

    public CarDto mapToDto(Car car) {
        return CarDto.builder()
                .uuid(car.getUuid())
                .brand(car.getBrand())
                .model(car.getModel())
                .fuel(car.getFuel())
                .transmission(car.getTransmission())
                .pricePerDay(car.getPricePerDay())
                .productionNumber(car.getProductionNumber())
                .registrationNumber(car.getRegistrationNumber())
                .build();
    }

    public List<CarDto> mapToDto(List<Car> cars) {
        return cars.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
