package app.services;

import app.models.Driver;
import app.models.Shift;
import app.repositories.DriverRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ShiftService {

    private final DriverRepository driverRepository;

    public ShiftService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public void addShift(Long driverId, LocalDateTime start, LocalDateTime end) {
        Driver driver = this.driverRepository.findById(driverId);

        if (!driver.isActive()) {
            throw new IllegalStateException("Нельзя добавить смену неактивному водителю.");
        }

        driver.addShift(new Shift(driverId, start, end));
    }

    // Получение списка смен водителя на выбранную дату
    public List<Shift> getShiftsForDate(Long driverId, LocalDate date) {
        Driver driver = this.driverRepository.findById(driverId);

        return driver.getShiftsForDate(date);
    }

    // Проверка, доступен ли водитель в конкретный момент времени
    public boolean isDriverAvailable(Long driverId, LocalDateTime checkTime) {
        Driver driver = this.driverRepository.findById(driverId);

        // Ну мы не можем его трогать
        if (!driver.isActive()) {
            return false;
        }

        return driver.isAvailable(checkTime);
    }
}

