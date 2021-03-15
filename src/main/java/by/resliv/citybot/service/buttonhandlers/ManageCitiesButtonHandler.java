package by.resliv.citybot.service.buttonhandlers;

import by.resliv.citybot.buttons.ButtonsFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static by.resliv.citybot.AppConstants.ASKING;

@Component
@AllArgsConstructor
public class ManageCitiesButtonHandler implements ButtonHandler {

    private ButtonsFactory buttonsFactory;

    @Override
    public BotApiMethod handleButton(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(ASKING);
        InlineKeyboardMarkup buttons = buttonsFactory.getButtonsForManageCitiesState();
        sendMessage.setReplyMarkup(buttons);
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        return sendMessage;
    }
}