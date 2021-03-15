package by.resliv.citybot.service.buttonhandlers;

import by.resliv.citybot.bot.BotState;
import by.resliv.citybot.buttons.ButtonsFactory;
import by.resliv.citybot.cache.ChatState;
import by.resliv.citybot.cache.UserObjectsStorage;
import by.resliv.citybot.entity.City;
import by.resliv.citybot.exceptions.NoSuchCityException;
import by.resliv.citybot.service.ICityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static by.resliv.citybot.AppConstants.EDITED_SUCCESSFULLY;
import static by.resliv.citybot.AppConstants.ENTER_CITY_DESCRIPTION;
import static by.resliv.citybot.AppConstants.ENTER_CITY_NAME;
import static by.resliv.citybot.bot.BotState.EDIT_CITY_DESCRIPTION;
import static by.resliv.citybot.bot.BotState.EDIT_CITY_NAME;

@Component
@AllArgsConstructor
public class EditCityButtonHandler implements ButtonHandler {

    private ChatState chatState;
    private UserObjectsStorage userObjectsStorage;
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
        Long chatId = callback.getMessage().getChatId();
        chatState.putCurrentChatBotState(chatId, EDIT_CITY_NAME);
        return new SendMessage(chatId, ENTER_CITY_NAME);
    }

    private SendMessage handleMessage(Message message) {
        Long chatId = message.getChatId();
        BotState currentChatBotState = chatState.getCurrentChatBotState(chatId);
        Integer userID = message.getFrom().getId();
        if (currentChatBotState == EDIT_CITY_NAME) {
            City city = new City(message.getText());
            userObjectsStorage.putCity(userID, city);
            chatState.putCurrentChatBotState(chatId, EDIT_CITY_DESCRIPTION);
            return new SendMessage(chatId, ENTER_CITY_DESCRIPTION);
        } else {
            City city = userObjectsStorage.getCity(userID);
            city.setRecommendations(message.getText());
            try {
                iCityService.updateCity(city);
            } catch (NoSuchCityException ncce) {
                return prepareMessage(chatId, ncce.getMessage());
            }
            chatState.cleanBotState(chatId);
            userObjectsStorage.removeCityFromStorage(userID);
            return prepareMessage(chatId, EDITED_SUCCESSFULLY);
        }
    }

    private SendMessage prepareMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);
        sendMessage.setReplyMarkup(buttonsFactory.getReturnToMainMenuButton());
        return sendMessage;
    }
}