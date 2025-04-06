package app.repositories;

import app.models.Driver;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {
    // "Хранилище" всех созданных водителей (упрощённо, вместо реальной БД)
    private static final List<Driver> DRIVERS = new ArrayList<>();

    public void save(Driver driver) {
        if (!DRIVERS.contains(driver)) {
            DRIVERS.add(driver);
        }
    }

    public Driver findById(Long id) {
        for (Driver d : DRIVERS) {
            if (d.getId().equals(id)) {
                return d;
            }
        }

        throw new IllegalArgumentException("Водитель не найден!");
    }
}
