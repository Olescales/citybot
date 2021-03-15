package by.resliv.citybot.service.buttonhandlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ButtonHandler {

    BotApiMethod handleButton(Update update);
}
