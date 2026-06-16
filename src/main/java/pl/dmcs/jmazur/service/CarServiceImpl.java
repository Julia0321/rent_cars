package pl.dmcs.jmazur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dmcs.jmazur.domain.Car;
import pl.dmcs.jmazur.dto.CarDto;
import pl.dmcs.jmazur.dto.CarForAdminDto;
import pl.dmcs.jmazur.enums.CarStatusEnum;
import pl.dmcs.jmazur.mapper.CarForAdminMapper;
import pl.dmcs.jmazur.mapper.CarMapper;
import pl.dmcs.jmazur.repository.CarRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final CarForAdminMapper carForAdminMapper;

    @Override
    public List<CarDto> findAll() {
        return carMapper.mapToDto(carRepository.findAll());
    }

    @Override
    public CarDto getCarDtoByUUID(String carUUID) {
        return carMapper.mapToDto(carRepository.findByUuid(carUUID)
                .orElseThrow(() -> new RuntimeException("Car not found")));
    }

    public Car getCarByUUID(String carUUID) {
        return carRepository.findByUuid(carUUID)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(c -> carMapper.mapToDto(c))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarForAdminDto> findAllForAdmin() {
        return carRepository.findAll()
                .stream()
                .map((c) -> carForAdminMapper.mapToDto(c))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCar(String uuid) {

        Car car = carRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        carRepository.delete(car);
    }

    @Override
    public boolean changeAvailability(String uuid) {

        Car car = carRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        if (car.getCarStatus() == CarStatusEnum.RENTED) {
            throw new RuntimeException("Cannot change availability - car is currently rented");
        }

        boolean nowAvailable =
                car.getCarStatus() == CarStatusEnum.UNAVAILABLE;

        car.setCarStatus(
                nowAvailable
                        ? CarStatusEnum.AVAILABLE
                        : CarStatusEnum.UNAVAILABLE
        );

        carRepository.save(car);
        return nowAvailable;
    }

    @Override
    public void addCar(CarForAdminDto carDto) {

        if (carRepository.existsByRegistrationNumber(carDto.getRegistrationNumber())) {
            throw new RuntimeException("Registration number already exists");
        }

        Car carDB = carForAdminMapper.mapToCar(carDto);
        carRepository.save(carDB);
    }

    @Override
    public List<CarDto> findAvailable(LocalDate from, LocalDate to) {

        return carMapper.mapToDto(carRepository.findAllCarsBetween(from, to));
    }
}
