package dev.fumaz.localization;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MessageBuilder {

    private final Message message;
    private final Map<String, Object> arguments;
    private String messagePrefix;
    private String argumentPrefix;

    public MessageBuilder(Message message) {
        this.message = message;
        this.messagePrefix = "";
        this.argumentPrefix = "";
        this.arguments = new HashMap<>();
    }

    public MessageBuilder prefix(Object messagePrefix) {
        this.messagePrefix = Objects.toString(messagePrefix);
        return this;
    }

    public MessageBuilder argumentPrefix(Object argumentPrefix) {
        this.argumentPrefix = Objects.toString(argumentPrefix);
        return this;
    }

    public MessageBuilder argument(String key, Object value) {
        arguments.put(key, value);
        return this;
    }

    public Message getMessage() {
        return message;
    }

    public String getMessagePrefix() {
        return messagePrefix;
    }

    public String getArgumentPrefix() {
        return argumentPrefix;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public String build() {
        String value = message.getValue();

        for (Map.Entry<String, Object> entry : arguments.entrySet()) {
            value = value.replace("{{" + entry.getKey() + "}}", argumentPrefix + Objects.toString(entry.getValue()) + messagePrefix);
        }

        return messagePrefix + value;
    }

}
