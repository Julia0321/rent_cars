package pl.dmcs.jmazur.service;

import pl.dmcs.jmazur.domain.Car;
import pl.dmcs.jmazur.dto.CarDto;
import pl.dmcs.jmazur.dto.CarForAdminDto;

import java.time.LocalDate;
import java.util.List;

public interface CarService {

    List<CarDto> findAll();

    CarDto getCarDtoByUUID(String carUUID);

    List<CarDto> findAvailable(LocalDate from, LocalDate to);

    Car getCarByUUID(String carUUID);

    List<CarDto> getAllCars();

    List<CarForAdminDto> findAllForAdmin();

    void deleteCar(String uuid);

    boolean changeAvailability(String uuid);

    void addCar(CarForAdminDto dto);
}
