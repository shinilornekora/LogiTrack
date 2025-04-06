package app.models;

import java.time.LocalDateTime;

public class Shift {
    private final Long driverId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Shift(Long driverId, LocalDateTime startTime, LocalDateTime endTime) {
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
}
