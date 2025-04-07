package app.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Сообщение, принадлежащее заявке (TransportationOrder).
public class Message {
    private final Long messageId;
    private final String text;
    private final List<Attachment> attachments = new ArrayList<>();
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final String author; // "Клиент" или "Перевозчик"
    private boolean confirmedByOppositeSide; // Инвариант: подтверждать может только другая сторона

    public Message(Long messageId, String text, String author) {
        this.messageId = messageId;
        this.text = text;
        this.author = author;
    }

    // Добавляем вложения (допускается только при создании нового сообщения).
    // Инвариант: "Вложения могут быть добавлены только вместе с сообщением".
    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }

    public void confirm(String confirmee) {
        // Предположим, логика: подтверждать может только противоположная сторона,
        // то есть если автор "Клиент", то подтверждать может "Перевозчик" и наоборот.
        if (confirmee.equals(author)) {
            throw new IllegalStateException("Нельзя подтвердить сообщение своей же стороной.");
        }
        this.confirmedByOppositeSide = true;
    }

    public boolean isConfirmedByOppositeSide() {
        return confirmedByOppositeSide;
    }

    public Long getMessageId() {
        return messageId;
    }

    public String getText() {
        return text;
    }

    public List<Attachment> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getAuthor() {
        return author;
    }
}
