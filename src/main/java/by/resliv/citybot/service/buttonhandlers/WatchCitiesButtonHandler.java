package by.resliv.citybot.service.buttonhandlers;

import by.resliv.citybot.buttons.ButtonsFactory;
import by.resliv.citybot.entity.City;
import by.resliv.citybot.service.ICityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
@AllArgsConstructor
public class WatchCitiesButtonHandler implements ButtonHandler {

    private ICityService iCityService;
    private ButtonsFactory buttonsFactory;

    @Override
    public BotApiMethod handleButton(Update update) {
        SendMessage sendMessage = new SendMessage();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        List<City> cities = iCityService.getCities();
        StringBuilder stringBuilder = new StringBuilder();
        cities.forEach(c -> stringBuilder.append(c.getName()).append(" \r\n").append(" \r\n"));
        sendMessage.setText(stringBuilder.toString());
        sendMessage.setChatId(chatId);
        InlineKeyboardMarkup buttons = buttonsFactory.getReturnToMainMenuButton();
        sendMessage.setReplyMarkup(buttons);
        return sendMessage;
    }
}
