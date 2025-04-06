package app.models;

// Вложение к сообщению
public class Attachment {
    private final String fileName;
    private final byte[] data;

    public Attachment(String fileName, byte[] data) {
        this.fileName = fileName;
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getData() {
        return data;
    }
}

