package org.veritas.alterego;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.veritas.alterego.bots.AlterEgoBot;

public class Launcher {

    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new AlterEgoBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

    }

}
