import app.models.Attachment;
import app.models.DeliveryRoute;
import app.models.Driver;
import app.models.Message;
import app.models.TransportationOrder;
import app.repositories.TransportationOrderRepository;
import app.services.FraudDetectionService;
import app.services.OrderLifecycleService;
import app.services.ShiftService;

public class App {
    static void showcaseFirst() {
         // 1) Работа с водителями и расписанием:
         Driver driver1 = new Driver(1L, "Иванов Иван Иванович", true);
         driver1.save();
 
         Driver driver2 = new Driver(2L, "Петров Пётр Петрович", true);
         driver2.save();
 
         ShiftService shiftService = new ShiftService();
         shiftService.addShift(1L, 
                               java.time.LocalDateTime.of(2023, 10, 1, 8, 0),
                               java.time.LocalDateTime.of(2023, 10, 1, 16, 0));
         shiftService.addShift(1L, 
                               java.time.LocalDateTime.of(2023, 10, 2, 8, 0),
                               java.time.LocalDateTime.of(2023, 10, 2, 16, 0));
         // Проверим расписание:
         System.out.println("Смены водителя #1 на 2023-10-01:" + 
                            shiftService.getShiftsForDate(1L, java.time.LocalDate.of(2023, 10, 1)));
 
         boolean available = shiftService.isDriverAvailable(1L, 
                                java.time.LocalDateTime.of(2023, 10, 1, 12, 0));
         System.out.println("Водитель #1 доступен в 2023-10-01 12:00? " + available);
    }

    static void showcaseSecond() {
        // 2) Работа со сложным агрегатом TransportationOrder:
        TransportationOrderRepository repository = new TransportationOrderRepository();
        OrderLifecycleService lifecycleService = new OrderLifecycleService();
        FraudDetectionService fraudService = new FraudDetectionService();

        TransportationOrder order = new TransportationOrder(1001L, new DeliveryRoute("Moscow", "Saint-Petersburg"));
        repository.save(order);

        // Допустим, сначала подтверждаем отсутствие фрода:
        if (!fraudService.isSuspicious(order)) {
            lifecycleService.startOrder(order);
        }

        // Добавляем сообщение (от "Клиента")
        Message msg = new Message(1L, "Здравствуйте, готовы ли вы транспортировать груз?", "Клиент");
        Attachment attachment = new Attachment("invoice.pdf", new byte[]{1,2,3});
        msg.addAttachment(attachment);

        order.addMessage(msg);
        repository.save(order);

        // Подтверждаем сообщение "Перевозчиком"
        try {
            msg.confirm("Перевозчик");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Имитация "простоя" и автозакрытия
        // (На реальном cron-фоновой задаче – проверять lastActivityTime для заявок)
        order.autoCloseIfInactive();
        repository.save(order);

        // Смотрим статус
        System.out.println("Заявка #" + order.getOrderId() + " имеет статус " + order.getStatus());
    }

    public static void main(String[] args) throws Exception {
        showcaseFirst();
        showcaseSecond();
    }
}
