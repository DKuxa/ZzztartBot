package org.kuxa.zzztart;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.kuxa.zzztart.service.EventService;
import org.kuxa.zzztart.service.SleepSessionService;
import org.kuxa.zzztart.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.context.i18n.LocaleContextHolder;

@Component
public class SleepTrackerBot {
    private final TelegramBot bot;
    private static final Logger LOGGER = LoggerFactory.getLogger(SleepTrackerBot.class);
    private final MessageSource messageSource;
    private final SleepSessionService sleepSessionService;
    private final UserService userService;
    private final EventService eventService;
    private final MenuService menuService;

    public SleepTrackerBot(
            @Value("${bot.token}") String botToken,
            MessageSource messageSource,
            SleepSessionService sleepSessionService, UserService userService, EventService eventService, MenuService menuService) {
        this.bot = new TelegramBot(botToken);
        this.messageSource = messageSource;
        this.sleepSessionService = sleepSessionService;
        this.userService = userService;
        this.eventService = eventService;
        this.menuService = menuService;
        LOGGER.info("SleepTrackerBot initialized with bot token.");
    }

    public void run() {
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                if (update.callbackQuery() != null) {
                    handleCallback(update.callbackQuery());
                } else if (update.message() != null && update.message().text() != null) {
                    handleMessage(update.message());
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void handleMessage(Message message) {
        LanguageManager.setUserLocale(message.from());
        long chatId = message.chat().id();

        switch (message.text().toLowerCase()) {
            case "/start":
                if (userService.isUserRegistered(chatId)) {
                    sendMainMenu(chatId);
                } else {
                    userService.registerUser(chatId, message.from().username());
                    sendResponse(chatId, "welcome_message");
                    sendMainMenu(chatId);
                }
                break;
            case "/menu":
                sendMainMenu(chatId);
                break;
            default:
                sendResponse(chatId, "unknown_command");
                break;
        }
        LocaleContextHolder.resetLocaleContext();
    }

    private void handleCallback(CallbackQuery callbackQuery) {
        LanguageManager.setUserLocale(callbackQuery.from());
        long chatId = callbackQuery.message().chat().id();

        switch (callbackQuery.data()) {
            case "/bedtime":
                sleepSessionService.startSleepSession(chatId);
                sendResponse(chatId, "sleep_tight");
                break;
            case "/phonesleep":
                eventService.createEventForCurrentSession(chatId, "phone_before_sleep");
                sendResponse(chatId, "phone_before_sleep");
                break;
            case "/oopsphone":
                eventService.createEventForCurrentSession(chatId, "phone_during_sleep");
                sendResponse(chatId, "phone_during_sleep");
                break;
            case "/awake":
                sleepSessionService.endSleepSession(chatId);
                sendResponse(chatId, "good_morning");
                break;
            case "/average_sleep":
                double averageSleepHours = sleepSessionService.calculateAverageSleepHours(chatId);
                sendResponse(chatId, "average_sleep_time", averageSleepHours);
                break;
        }

        bot.execute(new AnswerCallbackQuery(callbackQuery.id()));
        LocaleContextHolder.resetLocaleContext();
    }

    private void sendMainMenu(long chatId) {
        InlineKeyboardMarkup inlineKeyboard = menuService.getMainMenuInlineKeyboard();
        String messageText = messageSource.getMessage("choose_option", null, LocaleContextHolder.getLocale());
        SendMessage message = new SendMessage(chatId, messageText)
                .replyMarkup(inlineKeyboard);
        bot.execute(message);
    }

    private void sendResponse(long chatId, String responseKey, Object... args) {
        String responseText = messageSource.getMessage(responseKey, args, LocaleContextHolder.getLocale());
        SendMessage response = new SendMessage(chatId, responseText);
        bot.execute(response);
    }
}
