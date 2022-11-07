package dev.fumaz.localization;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Locale;
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

    public void add(Locale locale, JsonObject json, String... path) {
        Language language = getLanguageByCode(locale.getLanguage());

        if (language == null) {
            language = new Language(locale);
            languages.add(language);
        }

        Language finalLanguage = language;

        json.entrySet().forEach(entry -> {
            String key = entry.getKey();

            if (entry.getValue().isJsonObject()) {
                add(locale, entry.getValue().getAsJsonObject(), path.length == 0 ? key : path[0] + "." + key);
            } else {
                String value = entry.getValue().getAsString();
                String messageKey = path.length == 0 ? key : path[0] + "." + key;

                Message message = new Message(messageKey, value);
                finalLanguage.getMessages().add(message);
            }
        });

        languages.forEach(lang -> {
            System.out.println(lang.toString());
            lang.getMessages().forEach(message -> System.out.println(message.toString()));
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
                .filter(language -> language.getLocale().getLanguage().equals(locale.getLanguage()))
                .findFirst()
                .orElse(null);
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public MessageBuilder message(Locale locale, String key) {
        Language language = getLanguageByLocale(locale);

        if (language == null) {
            return null;
        }

        return language.message(key);
    }

}
