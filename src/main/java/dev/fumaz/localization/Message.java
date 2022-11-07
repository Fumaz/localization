package dev.fumaz.localization;

import java.util.Objects;

public class Message {

    private final String key;
    private final String value;

    public Message(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public MessageBuilder builder() {
        return new MessageBuilder(this);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Message)) {
            return false;
        }

        Message message = (Message) o;
        return Objects.equals(key, message.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "Message{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
