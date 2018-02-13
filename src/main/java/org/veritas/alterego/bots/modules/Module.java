package org.veritas.alterego.bots.modules;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public interface Module {

    SendMessage accept(Update update);

}
