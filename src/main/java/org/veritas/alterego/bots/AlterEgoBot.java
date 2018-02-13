package org.veritas.alterego.bots;

import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.veritas.alterego.utils.Messages;
import org.veritas.alterego.utils.RedissonClientProvider;

public class AlterEgoBot extends TelegramLongPollingBot {

    private RedissonClient client;

    public AlterEgoBot() {
        client = new RedissonClientProvider().getClient();
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateMembers(update);

        System.out.printf("[<] (%s) %s: %s%n",
                update.getMessage().getChat().getTitle(),
                update.getMessage().getFrom().getFirstName(),
                Messages.getMessageType(update.getMessage()));
    }

    public void onUpduteReceived(Update update) {

    }

    private void updateMembers(Update update) {
        String memberSetName = String.format("alterego:groups:%d:members", update.getMessage().getChatId());
        RSet<String> memberSet = client.getSet(memberSetName);
        if (update.getMessage().getLeftChatMember() != null) {
            memberSet.remove(String.valueOf(update.getMessage().getLeftChatMember().getId()));
        } else {
            memberSet.add(String.valueOf(update.getMessage().getFrom().getId()));
        }
        RSet<String> chatSet = client.getSet("alterego:groups");
        chatSet.add(memberSetName);
    }

    @Override
    public String getBotUsername() {
        return "alter_egobot";
    }

    @Override
    public String getBotToken() {
        return "530476044:AAGd-ryBDtWr5GBvnS-y3DJYeCcIsrVsEAw";
    }

}
