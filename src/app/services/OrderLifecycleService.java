package app.services;

import app.models.OrderStatus;
import app.models.TransportationOrder;

// Сервис управления статусом заявки
public class OrderLifecycleService {

    public void startOrder(TransportationOrder order) {
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("Заявка должна быть в статусе CREATED, чтобы начать выполнение");
        }
        order.updateStatus(OrderStatus.IN_PROGRESS);
    }

    public void completeOrder(TransportationOrder order) {
        if (order.getStatus() != OrderStatus.IN_PROGRESS) {
            throw new IllegalStateException("Заявка должна быть в процессе выполнения, чтобы её завершить");
        }
        order.close();
    }
}
