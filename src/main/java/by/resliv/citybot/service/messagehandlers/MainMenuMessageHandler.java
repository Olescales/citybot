package by.resliv.citybot.service.messagehandlers;

import by.resliv.citybot.buttons.ButtonsFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static by.resliv.citybot.AppConstants.GREETING;

@Component
@AllArgsConstructor
public class MainMenuMessageHandler implements MessageHandler {

    private ButtonsFactory buttonsFactory;

    @Override
    public SendMessage handleMessage(Message message) {
        SendMessage sendMessage = new SendMessage(message.getChatId(), GREETING);
        sendMessage.setReplyMarkup(buttonsFactory.getButtonsForStartState());
        return sendMessage;
    }
}