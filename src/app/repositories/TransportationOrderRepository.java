package app.repositories;

import app.models.TransportationOrder;
import java.util.ArrayList;
import java.util.List;

public class TransportationOrderRepository {

    // Database is useless there!~
    private final List<TransportationOrder> store = new ArrayList<>();

    public void save(TransportationOrder order) {
        // Если заказ уже есть в списке – удалим старую версию и сохраним новую
        store.removeIf(o -> o.getOrderId().equals(order.getOrderId()));
        store.add(order);
    }

    public TransportationOrder findById(Long orderId) {
        for (TransportationOrder o : store) {
            if (o.getOrderId().equals(orderId)) {
                return o;
            }
        }
        return null;
    }

    public List<TransportationOrder> findAll() {
        return new ArrayList<>(store);
    }
}
