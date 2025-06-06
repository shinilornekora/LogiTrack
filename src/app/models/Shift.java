package app.models;

import java.time.LocalDateTime;

public class Shift {
    private final Long driverId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Shift(Long driverId, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new IllegalArgumentException("Некорректный интервал смены: начало не может быть позже или равно окончанию.");
        }

        this.driverId = driverId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getDriverId() {
        return driverId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "\nСмена для водителя с ID " + driverId + " с " +
            this.getStartTime() + " до " + this.getEndTime();
    }
}
