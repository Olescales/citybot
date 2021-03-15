package by.resliv.citybot.service.buttonhandlers;

import by.resliv.citybot.bot.BotState;
import by.resliv.citybot.buttons.ButtonsFactory;
import by.resliv.citybot.cache.ChatState;
import by.resliv.citybot.cache.UserObjectsStorage;
import by.resliv.citybot.entity.City;
import by.resliv.citybot.service.ICityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static by.resliv.citybot.AppConstants.ADDED_SUCCESSFULLY;
import static by.resliv.citybot.AppConstants.ENTER_CITY_DESCRIPTION;
import static by.resliv.citybot.AppConstants.ENTER_CITY_NAME;
import static by.resliv.citybot.bot.BotState.ADD_GET_CITY_NAME;
import static by.resliv.citybot.bot.BotState.GET_CITY_DESCRIPTION;

@Component
@AllArgsConstructor
public class AddCityButtonHandler implements ButtonHandler {

    private ChatState chatState;
    private ICityService iCityService;
    private ButtonsFactory buttonsFactory;
    private UserObjectsStorage userObjectsStorage;

    @Override
    public BotApiMethod handleButton(Update update) {
        if (update.hasCallbackQuery()) {
            return handleCallback(update.getCallbackQuery());
        }
        return handleMessage(update.getMessage());
    }

    private SendMessage handleCallback(CallbackQuery callback) {
        Long chatID = callback.getMessage().getChatId();
        chatState.putCurrentChatBotState(chatID, ADD_GET_CITY_NAME);
        return new SendMessage(chatID, ENTER_CITY_NAME);
    }

    private SendMessage handleMessage(Message message) {
        Long chatId = message.getChatId();
        BotState currentChatBotState = chatState.getCurrentChatBotState(chatId);
        if (currentChatBotState == ADD_GET_CITY_NAME) {
            Integer userID = message.getFrom().getId();
            City city = new City();
            city.setName(message.getText());
            userObjectsStorage.putCity(userID, city);
            SendMessage sendMessage = new SendMessage(chatId, ENTER_CITY_DESCRIPTION);
            chatState.putCurrentChatBotState(chatId, GET_CITY_DESCRIPTION);
            return sendMessage;
        } else {
            Integer userID = message.getFrom().getId();
            String recommendations = message.getText();
            City city = userObjectsStorage.getCity(userID);
            city.setRecommendations(recommendations);
            iCityService.saveCity(city);
            chatState.cleanBotState(chatId);
            userObjectsStorage.removeCityFromStorage(userID);

            SendMessage sendMessage = new SendMessage(chatId, ADDED_SUCCESSFULLY);
            sendMessage.setReplyMarkup(buttonsFactory.getReturnToMainMenuButton());
            return sendMessage;
        }
    }
}