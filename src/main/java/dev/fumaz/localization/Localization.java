package dev.fumaz.localization;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Localization {

    private final Set<Language> languages;

    public Localization() {
        this.languages = new HashSet<>();
    }

    public void add(File file) {
        String[] split = file.getName().split("_");
        String languageCode = split[split.length - 1].replace(".json", "");

        try {
            add(new Locale(languageCode), new Gson().fromJson(new FileReader(file), JsonObject.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Locale locale, InputStream stream) {
        try {
            add(locale, new Gson().fromJson(new InputStreamReader(stream), JsonObject.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Locale locale, JsonObject json) {
        Language language = getLanguageByCode(locale.getLanguage());

        if (language == null) {
            language = new Language(locale);
            languages.add(language);
        }

        Language finalLanguage = language;

        json.entrySet().forEach(entry -> {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();

            Message message = new Message(key, value);

            if (!finalLanguage.getMessages().contains(message)) {
                finalLanguage.getMessages().add(message);
            }
        });
    }

    public Language getLanguageByCode(String code) {
        return languages.stream()
                .filter(language -> language.getLocale().getLanguage().equals(code))
                .findFirst()
                .orElse(null);
    }

    public Language getLanguageByLocale(Locale locale) {
        return languages.stream()
                .filter(language -> language.getLocale().equals(locale))
                .findFirst()
                .orElse(null);
    }

    public Message getMessage(String languageCode, String key) {
        Language language = getLanguageByCode(languageCode);

        if (language == null) {
            return null;
        }

        return language.getMessage(key);
    }

    public String format(String languageCode, String key, Map<String, Object> arguments) {
        Message message = getMessage(languageCode, key);

        if (message == null) {
            return null;
        }

        return message.format(arguments);
    }

    public String format(String languageCode, String key, Object... arguments) {
        Message message = getMessage(languageCode, key);

        if (message == null) {
            return null;
        }

        return message.format(arguments);
    }

}
