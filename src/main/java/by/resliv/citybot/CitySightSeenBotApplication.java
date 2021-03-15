package by.resliv.citybot;

import by.resliv.citybot.bot.BotState;
import by.resliv.citybot.service.buttonhandlers.AddCityButtonHandler;
import by.resliv.citybot.service.buttonhandlers.ButtonHandler;
import by.resliv.citybot.service.buttonhandlers.DeleteCityButtonHandler;
import by.resliv.citybot.service.buttonhandlers.EditCityButtonHandler;
import by.resliv.citybot.service.buttonhandlers.ManageCitiesButtonHandler;
import by.resliv.citybot.service.buttonhandlers.ToMainMenuButtonHandler;
import by.resliv.citybot.service.buttonhandlers.WatchCitiesButtonHandler;
import by.resliv.citybot.service.messagehandlers.WatchCitiesMessageHandler;
import by.resliv.citybot.service.messagehandlers.CityRecommendationMessageHandler;
import by.resliv.citybot.service.messagehandlers.MessageHandler;
import by.resliv.citybot.service.messagehandlers.MainMenuMessageHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@AllArgsConstructor
public class CitySightSeenBotApplication {

    private MainMenuMessageHandler mainMenuMessageHandler;
    private CityRecommendationMessageHandler cityRecommendationMessageHandler;
    private WatchCitiesMessageHandler watchCitiesMessageHandler;

    private AddCityButtonHandler addCityCallbackHandler;
    private DeleteCityButtonHandler deleteCityCallbackHandler;
    private EditCityButtonHandler editCityCallbackHandler;
    private ManageCitiesButtonHandler manageCitiesCallbackHandler;
    private ToMainMenuButtonHandler toMainMenuCallbackHandler;
    private WatchCitiesButtonHandler watchCitiesCallbackHandler;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(CitySightSeenBotApplication.class, args);
    }

    @Bean
    public Map<String, MessageHandler> messageHandlers() {
        Map<String, MessageHandler> messageHandlers = new HashMap<>();
        messageHandlers.put("/main", mainMenuMessageHandler);
        messageHandlers.put("/city", cityRecommendationMessageHandler);
        messageHandlers.put("/cities", watchCitiesMessageHandler);
        return messageHandlers;
    }

    @Bean
    public Map<String, ButtonHandler> buttonHandlers() {
        Map<String, ButtonHandler> callbackHandlers = new HashMap<>();
        callbackHandlers.put(BotState.MANAGE_CITIES.getCommandName(), manageCitiesCallbackHandler);
        callbackHandlers.put(BotState.ADD_CITY.getCommandName(), addCityCallbackHandler);
        callbackHandlers.put(BotState.EDIT_CITY.getCommandName(), editCityCallbackHandler);
        callbackHandlers.put(BotState.DELETE_CITY.getCommandName(), deleteCityCallbackHandler);
        callbackHandlers.put("main", toMainMenuCallbackHandler);
        callbackHandlers.put(BotState.WATCH_CITIES.getCommandName(), watchCitiesCallbackHandler);
        return callbackHandlers;
    }
}
