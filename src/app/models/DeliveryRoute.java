package app.models;

// Упрощённый объект-значение для маршрута доставки
public class DeliveryRoute {

    private final String origin;
    private final String destination;

    public DeliveryRoute(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Route from " + origin + " to " + destination;
    }
}