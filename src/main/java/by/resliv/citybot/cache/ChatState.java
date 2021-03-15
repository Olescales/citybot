package by.resliv.citybot.cache;

import by.resliv.citybot.bot.BotState;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.WeakHashMap;

@Component
public class ChatState {

    private static Map<Long, BotState> currentChatState = new WeakHashMap<>();

    public BotState getCurrentChatBotState(Long chatID) {
        return currentChatState.get(chatID);
    }

    public void putCurrentChatBotState(Long chatID, BotState botState) {
        currentChatState.put(chatID, botState);
    }

    public void cleanBotState(Long chatID) {
        currentChatState.remove(chatID);
    }
}