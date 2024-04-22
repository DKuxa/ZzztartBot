package org.kuxa.zzztart;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuService {

    private final LocalizationUtil localizationUtil;

    @Autowired
    public MenuService(LocalizationUtil localizationUtil) {
        this.localizationUtil = localizationUtil;
    }

    public InlineKeyboardMarkup getMainMenuInlineKeyboard() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton(localizationUtil.getMessage("main_menu_bedtime")).callbackData("/bedtime"),
                        new InlineKeyboardButton(localizationUtil.getMessage("main_menu_phonesleep")).callbackData("/phonesleep")
                },
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton(localizationUtil.getMessage("main_menu_sleeping_now")).callbackData("/oopsphone"),
                        new InlineKeyboardButton(localizationUtil.getMessage("main_menu_woke_up")).callbackData("/awake")
                },
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton(localizationUtil.getMessage("main_menu_sleep_stats")).callbackData("/average_sleep")
                }
        );
    }
}
