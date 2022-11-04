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

    public MessageBuilder message(String key) {
        return getMessages().stream()
                .filter(message -> message.getKey().equals(key))
                .findFirst()
                .map(Message::builder)
                .orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Language)) {
            return false;
        }

        Language language = (Language) o;
        return Objects.equals(locale, language.locale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale);
    }


}
