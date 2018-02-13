package org.veritas.alterego.bots.modules;

import org.redisson.api.RSet;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Pidor implements Module {

    private RSet<String> members;
    private LocalDate previous;
    private String currentPidor;

    private final String ERROR_FORMAT = "Пидор дня уже известен: это %s!";
    private final String MESSAGE_FORMAT = "Обнаружен пидор дня: %s!";

    public Pidor(RSet<String> members) {
        this.members = members;
        this.previous = LocalDate.now().minusDays(1);
    }

    @Override
    public SendMessage accept(Update update) {
        LocalDate current = Instant
                .ofEpochSecond(update.getMessage().getDate())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        if (current.minusDays(1).getDayOfMonth() == previous.getDayOfMonth()) {
            return new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText(String.format(ERROR_FORMAT, current));
        } else {

        }
    }

    public SendMessage declarePidor(Update update) {
        currentPidor = members.random();
    }

}
