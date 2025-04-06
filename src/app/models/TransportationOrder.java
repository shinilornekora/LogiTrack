package app.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Корень агрегата
public class TransportationOrder {
    private final Long orderId;
    private OrderStatus status;
    private final DeliveryRoute deliveryRoute;
    private LocalDateTime lastActivityTime; // Для логики авто-закрытия
    private final List<Message> messages = new ArrayList<>();

    public TransportationOrder(Long orderId, DeliveryRoute route) {
        this.orderId = orderId;
        this.deliveryRoute = route;
        this.status = OrderStatus.CREATED;
        this.lastActivityTime = LocalDateTime.now();
    }

    // Инвариант: "Сообщения можно добавлять только к активной заявке".
    // Условимся, что активные статусы – CREATED, IN_PROGRESS.
    public void addMessage(Message message) {
        if (!isActive()) {
            throw new IllegalStateException("Нельзя добавлять сообщение в неактивную заявку!");
        }
        messages.add(message);
        touchActivity();
    }

    // Обновляем статус заявки (например, на IN_PROGRESS)
    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        touchActivity();
    }

    // Проверка: заявка всё ещё активна?
    public boolean isActive() {
        return status == OrderStatus.CREATED || status == OrderStatus.IN_PROGRESS;
    }

    // Имеем возможность закрывать заявку.
    public void close() {
        this.status = OrderStatus.COMPLETED;
        touchActivity();
    }

    // Симулируем "автоматическое" закрытие при отсутствии активности (вызывается из доменного сервиса).
    public void autoCloseIfInactive() {
        // Например, если с момента последней активности прошло больше определённого времени
        // (здесь для упрощения сравниваем только минуты).
        if (isActive() && lastActivityTime.isBefore(LocalDateTime.now().minusMinutes(30))) {
            this.status = OrderStatus.CANCELED;
        }
    }

    // Регистрация активности (например, при добавлении сообщений, изменении статуса и т. п.)
    private void touchActivity() {
        this.lastActivityTime = LocalDateTime.now();
    }

    // Геттеры
    public Long getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public DeliveryRoute getDeliveryRoute() {
        return deliveryRoute;
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public LocalDateTime getLastActivityTime() {
        return lastActivityTime;
    }
}
