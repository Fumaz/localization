package dev.fumaz.localization;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Message {

    private final String key;
    private final String value;

    public Message(String key, String value) {
        this.key = key;
        this.value = value;
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

        if (!(o instanceof Message message)) {
            return false;
        }

        return Objects.equals(key, message.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    public String format(Map<String, Object> arguments) {
        String message = value;

        for (Map.Entry<String, Object> entry : arguments.entrySet()) {
            message = message.replace("{{" + entry.getKey() + "}}", Objects.toString(entry.getValue()));
        }

        return message;
    }

    public String format(Object... arguments) {
        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < arguments.length - 1; i += 2) {
            map.put(Objects.toString(arguments[i]), arguments[i + 1]);
        }

        return format(map);
    }

}
