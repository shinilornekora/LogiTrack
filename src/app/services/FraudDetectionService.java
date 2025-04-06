package app.services;

import app.models.TransportationOrder;

// Система анти-фрода
public class FraudDetectionService {
    public boolean isSuspicious(TransportationOrder order) {
        // Упрощённая логика: если заказ создан больше N раз за короткий период,
        // или например автор сообщений меняется слишком часто, и т.п.
        // Здесь просто заглушка:
        return false;
    }
}