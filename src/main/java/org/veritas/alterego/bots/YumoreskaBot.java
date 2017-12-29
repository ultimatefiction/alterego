package org.veritas.alterego.bots;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.veritas.alterego.utils.ChatStats;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class YumoreskaBot extends TelegramLongPollingBot {

    private Random random;
    private HashMap<Long, ChatStats> chats;

    public YumoreskaBot() {
        random = new Random();
        chats = new HashMap<Long, ChatStats>();
    }

    public void onUpdateReceived(Update update) {
        long chatId = update.getMessage().getChatId();

        if (!chats.containsKey(update.getMessage().getChatId())) {
            chats.put(chatId, new ChatStats(chatId, 850, 850, 15));
        }
        ChatStats chat = chats.get(chatId);
        chat.update();
        chat.addMember(update.getMessage().getFrom().getUserName());
        prikol(chat, update);

        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().contains("/ship")) {
            if (chat.getSize() >= 2) {
                if (chat.getLastShipped().getDayOfMonth() != LocalDate.now().getDayOfMonth()) {
                    ship(chat);
                } else {
                    shipDefault(chat);
                }
            } else {
                shipError(chat);
            }
        }
    }

    private void shipDefault(ChatStats chat) {
        SendMessage message = new SendMessage()
                .setChatId(chat.getChatId())
                .setText(chat.getCurrentPairing());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void shipError(ChatStats chat) {
        SendMessage message = new SendMessage()
                .setChatId(chat.getChatId())
                .setText("Невозможно никого зашипперить: я пока знаю слишком мало людей!");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void ship(ChatStats chat) {
        ArrayList<String> pair = chat.getNRandomMembers(2);
        SendMessage message = new SendMessage()
                .setChatId(chat.getChatId())
                .setText(String.format("@%s, @%s, ебитес", pair.get(0), pair.get(1)));
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        chat.setLastShipped(LocalDate.now());
        chat.setCurrentPairing(String.format("Топ-пейринг дня: %s и %s", pair.get(0), pair.get(1)));
    }

    private void prikol(ChatStats chat, Update update) {
        int maxChance = 1000;
        boolean postHui = random.nextInt(maxChance) <= chat.getHuiChance();
        boolean postPizda = random.nextInt(maxChance) <= chat.getPizdaChance();

        //System.out.print(chat);

        if (postHui && update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().matches("(.* )*[aAаА]+[!?.]*")) {
            chat.setHuiChance(0);
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setReplyToMessageId(update.getMessage().getMessageId())
                    .setText("хуй на!");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (postPizda && update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().matches("(.* )*[дД]+[aAаА]+[!?.]*")) {
            chat.setPizdaChance(0);
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setReplyToMessageId(update.getMessage().getMessageId())
                    .setText("пизда!");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return "alter_egobot";
    }

    public String getBotToken() {
        return "token";
    }

}
