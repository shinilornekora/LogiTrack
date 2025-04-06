package app.models;

import java.util.ArrayList;
import java.util.List;

// Паттерн Active Record: Driver сам содержит логику чтения/записи данных из БД.
// Ниже – упрощённый пример, где "симуляцией" хранения служит статическая коллекция.
public class Driver {

    // "Хранилище" всех созданных водителей (упрощённо, вместо реальной БД)
    private static final List<Driver> DRIVERS = new ArrayList<>();

    private final Long id;
    private final String fullName;
    private boolean active;

    // Конструктор
    public Driver(Long id, String fullName, boolean active) {
        this.id = id;
        this.fullName = fullName;
        this.active = active;
    }

    // Пример методов "Active Record" для сохранения/получения объекта
    public void save() {
        // Если объект уже есть в списке, обновим запись;
        // если нет – добавим.
        if (!DRIVERS.contains(this)) {
            DRIVERS.add(this);
        }
        // В реальной ситуации тут была бы работа с БД (INSERT/UPDATE).
    }

    public static Driver findById(Long id) {
        // Упрощённо ищем в локальной коллекции.
        // В реальности – SELECT из БД.
        for (Driver d : DRIVERS) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        return null;
    }

    // Геттеры/сеттеры
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
}
