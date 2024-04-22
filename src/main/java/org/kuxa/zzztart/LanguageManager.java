package org.kuxa.zzztart;

import com.pengrad.telegrambot.model.User;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LanguageManager {
    private static final Map<String, Locale> supportedLocales = new HashMap<>();

    static {
        supportedLocales.put("en", Locale.ENGLISH);
        supportedLocales.put("ru", new Locale("ru", "RU"));
        // Add other locales as necessary
    }

    public static Locale getUserLocale(User user) {
        if (user != null && user.languageCode() != null) {
            String languageCode = user.languageCode().toLowerCase();
            return supportedLocales.getOrDefault(languageCode, Locale.ENGLISH); // Default to English
        }
        return Locale.ENGLISH; // Default if user or language code is null
    }

    public static void setUserLocale(User user) {
        Locale locale = getUserLocale(user);
        LocaleContextHolder.setLocale(locale);
    }
}
