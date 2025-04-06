package app.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    private final Long id;
    private final String fullName;
    private final List<Shift> shifts = new ArrayList<>();
    
    private boolean active;

    public Driver(Long id, String fullName, boolean active) {
        this.id = id;
        this.fullName = fullName;
        this.active = active;
    }

    public void addShift(Shift shift) {
        // Проверка наложения на существующие смены
        for (Shift existingShift : this.shifts) {
            boolean overlaps = !shift.getEndTime().isBefore(existingShift.getStartTime()) &&
                               !shift.getStartTime().isAfter(existingShift.getEndTime());
            if (overlaps) {
                throw new IllegalArgumentException("Смена пересекается с существующей сменой водителя.");
            }
        }

        this.shifts.add(shift);
    }

    public List<Shift> getShiftsForDate(LocalDate date) {
        List<Shift> result = new ArrayList<>();

        for (Shift shift : this.shifts) {

            boolean isSameDate = isSameDate(date, shift.getStartTime()) || isSameDate(date, shift.getEndTime());
            boolean isOverlapped = (
                shift.getStartTime().toLocalDate().isBefore(date) && 
                shift.getEndTime().toLocalDate().isAfter(date)
            );

            // Проверяем, попадает ли начало/конец смены на выбранную дату
            if (isSameDate || isOverlapped) {
                result.add(shift);
            }
        }

        return result;
    }

    public boolean isAvailable(LocalDateTime checkTime) {
        for (Shift shift : this.shifts) {
            if (!checkTime.isBefore(shift.getStartTime()) && !checkTime.isAfter(shift.getEndTime())) {
                return false;
            }
        }

        return true;
    }

    private boolean isSameDate(LocalDate date, LocalDateTime dateTime) {
        return dateTime.toLocalDate().equals(date);
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Shift> getShifts() {
        return shifts;
    }
}
