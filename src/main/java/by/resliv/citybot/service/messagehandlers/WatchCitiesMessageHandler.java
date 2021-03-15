package by.resliv.citybot.service.messagehandlers;

import by.resliv.citybot.buttons.ButtonsFactory;
import by.resliv.citybot.entity.City;
import by.resliv.citybot.service.ICityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
@AllArgsConstructor
public class WatchCitiesMessageHandler implements MessageHandler {

    private ICityService iCityService;
    private ButtonsFactory buttonsFactory;

    @Override
    public SendMessage handleMessage(Message message) {
        SendMessage sendMessage = new SendMessage();

        List<City> cities = iCityService.getCities();
        StringBuilder stringBuilder = new StringBuilder();
        cities.forEach(c -> stringBuilder.append(c.getName()).append(" \r\n").append(" \r\n"));
        sendMessage.setText(stringBuilder.toString());
        sendMessage.setChatId(message.getChatId());
        InlineKeyboardMarkup buttons = buttonsFactory.getReturnToMainMenuButton();
        sendMessage.setReplyMarkup(buttons);
        return sendMessage;
    }
}