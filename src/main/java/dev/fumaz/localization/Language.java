package dev.fumaz.localization;

import java.util.*;

public class Language {

    private final Locale locale;
    private final List<Message> messages;

    public Language(Locale locale) {
        this.locale = locale;
        this.messages = new ArrayList<>();
    }

    public Locale getLocale() {
        return locale;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Message getMessage(String key) {
        return messages.stream()
                .filter(message -> message.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }

    public String format(String key, Map<String, Object> arguments) {
        Message message = getMessage(key);

        if (message == null) {
            return null;
        }

        return message.format(arguments);
    }

    public String format(String key, Object... arguments) {
        Message message = getMessage(key);

        if (message == null) {
            return null;
        }

        return message.format(arguments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Language language)) {
            return false;
        }

        return Objects.equals(locale, language.locale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale);
    }


}
