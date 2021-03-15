package by.resliv.citybot.service.buttonhandlers;

import by.resliv.citybot.buttons.ButtonsFactory;
import by.resliv.citybot.cache.ChatState;
import by.resliv.citybot.exceptions.NoSuchCityException;
import by.resliv.citybot.service.ICityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static by.resliv.citybot.AppConstants.DELETED_SUCCESSFULLY;
import static by.resliv.citybot.AppConstants.ENTER_CITY_NAME;
import static by.resliv.citybot.bot.BotState.DELETE_GET_CITY_NAME;

@Component
@AllArgsConstructor
public class DeleteCityButtonHandler implements ButtonHandler {

    private ChatState chatState;
    private ICityService iCityService;
    private ButtonsFactory buttonsFactory;

    @Override
    public BotApiMethod handleButton(Update update) {
        if (update.hasCallbackQuery()) {
            return handleCallback(update.getCallbackQuery());
        }
        return handleMessage(update.getMessage());
    }

    private BotApiMethod handleCallback(CallbackQuery callback) {
        Long chatID = callback.getMessage().getChatId();
        chatState.putCurrentChatBotState(chatID, DELETE_GET_CITY_NAME);
        return new SendMessage(chatID, ENTER_CITY_NAME);
    }

    private SendMessage handleMessage(Message message) {
        String cityForDelete = message.getText();
        Long chatId = message.getChatId();
        try {
            iCityService.deleteCity(cityForDelete);
            return prepareMessage(chatId, DELETED_SUCCESSFULLY);
        } catch (NoSuchCityException ncce) {
            return prepareMessage(chatId, ncce.getMessage());
        }
    }

    private SendMessage prepareMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);
        sendMessage.setReplyMarkup(buttonsFactory.getReturnToMainMenuButton());
        return sendMessage;
    }
}