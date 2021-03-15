package by.resliv.citybot.service.buttonhandlers;

import by.resliv.citybot.buttons.ButtonsFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static by.resliv.citybot.AppConstants.GREETING;

@Component
@AllArgsConstructor
public class ToMainMenuButtonHandler implements ButtonHandler {

    private ButtonsFactory buttonsFactory;

    @Override
    public BotApiMethod handleButton(Update update) {
        SendMessage sendMessage = new SendMessage(update.getCallbackQuery().getMessage().getChatId(), GREETING);
        sendMessage.setReplyMarkup(buttonsFactory.getButtonsForStartState());
        return sendMessage;
    }
}
