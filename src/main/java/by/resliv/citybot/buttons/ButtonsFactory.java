package by.resliv.citybot.buttons;

import by.resliv.citybot.bot.BotState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class ButtonsFactory {

    public InlineKeyboardMarkup getButtonsForStartState() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton citiesButton = new InlineKeyboardButton("Watch cities");
        InlineKeyboardButton citiesManagement = new InlineKeyboardButton("Manage cities");
        citiesButton.setCallbackData(BotState.WATCH_CITIES.getCommandName());
        citiesManagement.setCallbackData(BotState.MANAGE_CITIES.getCommandName());
        List<InlineKeyboardButton> line = new ArrayList<>();
        line.add(citiesButton);
        line.add(citiesManagement);
        List<List<InlineKeyboardButton>> allButtons = new ArrayList<>();
        allButtons.add(line);
        inlineKeyboardMarkup.setKeyboard(allButtons);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getReturnToMainMenuButton() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton backToMainMenu = new InlineKeyboardButton("Back to main menu");
        backToMainMenu.setCallbackData("main");
        List<InlineKeyboardButton> line = new ArrayList<>();
        line.add(backToMainMenu);
        List<List<InlineKeyboardButton>> allButtons = new ArrayList<>();
        allButtons.add(line);
        inlineKeyboardMarkup.setKeyboard(allButtons);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getButtonsForManageCitiesState() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton addCityDescription = new InlineKeyboardButton("Add city description");
        InlineKeyboardButton editCity = new InlineKeyboardButton("Edit city");
        InlineKeyboardButton deleteCity = new InlineKeyboardButton("Delete city");
        addCityDescription.setCallbackData(BotState.ADD_CITY.getCommandName());
        editCity.setCallbackData(BotState.EDIT_CITY.getCommandName());
        deleteCity.setCallbackData(BotState.DELETE_CITY.getCommandName());
        List<InlineKeyboardButton> line = new ArrayList<>();
        line.add(addCityDescription);
        line.add(editCity);
        line.add(deleteCity);
        List<List<InlineKeyboardButton>> allButtons = new ArrayList<>();
        allButtons.add(line);
        inlineKeyboardMarkup.setKeyboard(allButtons);
        return inlineKeyboardMarkup;
    }
}