package by.resliv.citybot.service.messagehandlers;

import by.resliv.citybot.buttons.ButtonsFactory;
import by.resliv.citybot.entity.City;
import by.resliv.citybot.exceptions.NoSuchCityException;
import by.resliv.citybot.service.ICityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static by.resliv.citybot.AppConstants.ENTER_CITY_NAME;

@Component
@AllArgsConstructor
public class CityRecommendationMessageHandler implements MessageHandler {

    private ICityService iCityService;
    private ButtonsFactory buttonsFactory;

    @Override
    public SendMessage handleMessage(Message message) {

        Long chatId = message.getChatId();
        if (message.getText().equals("/city")) {
            return new SendMessage(chatId, ENTER_CITY_NAME);
        }
        try {
            City city = iCityService.getCity(message.getText());
            String cityInfo = city.getRecommendations() +
                    " \r\n" +
                    " \r\n" +
                    ENTER_CITY_NAME;
            return prepareMessage(chatId, cityInfo);
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