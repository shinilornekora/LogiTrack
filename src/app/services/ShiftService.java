package app.services;

import app.models.Driver;
import app.models.Shift;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShiftService {

    // Упрощённое "хранилище" смен в памяти
    private static final List<Shift> SHIFTS = new ArrayList<>();

    // Добавление новой смены
    public void addShift(Long driverId, LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end) || start.equals(end)) {
            throw new IllegalArgumentException("Некорректный интервал смены: начало не может быть позже или равно окончанию.");
        }
        
        Driver driver = Driver.findById(driverId);

        if (driver == null) {
            throw new IllegalArgumentException("Водитель не найден!");
        }

        if (!driver.isActive()) {
            throw new IllegalStateException("Нельзя добавить смену неактивному водителю.");
        }

        // Проверка наложения на существующие смены
        for (Shift existingShift : SHIFTS) {
            if (!existingShift.getDriverId().equals(driverId)) {
                continue;
            }
            boolean overlaps =!end.isBefore(existingShift.getStartTime()) &&
                               !start.isAfter(existingShift.getEndTime());
            if (overlaps) {
                throw new IllegalArgumentException("Смена пересекается с существующей сменой водителя.");
            }
        }

        // Если валидации пройдены – добавляем смену:
        Shift shift = new Shift(driverId, start, end);
        SHIFTS.add(shift);
    }

    // Получение списка смен водителя на выбранную дату
    public List<Shift> getShiftsForDate(Long driverId, LocalDate date) {
        List<Shift> result = new ArrayList<>();
        for (Shift shift : SHIFTS) {
            if (shift.getDriverId().equals(driverId)) {
                // Проверяем, попадает ли начало/конец смены на выбранную дату
                if (isSameDate(date, shift.getStartTime()) ||
                    isSameDate(date, shift.getEndTime()) ||
                    // Или смена целиком "покрывает" дату
                    (shift.getStartTime().toLocalDate().isBefore(date)
                     && shift.getEndTime().toLocalDate().isAfter(date))) {
                    result.add(shift);
                }
            }
        }
        return result;
    }

    // Проверка, доступен ли водитель в конкретный момент времени
    public boolean isDriverAvailable(Long driverId, LocalDateTime checkTime) {
        Driver driver = Driver.findById(driverId);
        if (driver == null || !driver.isActive()) {
            return false;
        }
        // Водитель недоступен, если checkTime попадает в интервал одной из смен
        for (Shift shift : SHIFTS) {
            if (shift.getDriverId().equals(driverId)) {
                if (!checkTime.isBefore(shift.getStartTime()) && !checkTime.isAfter(shift.getEndTime())) {
                    // Время попадания в смену
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSameDate(LocalDate date, LocalDateTime dateTime) {
        return dateTime.toLocalDate().equals(date);
    }
}

