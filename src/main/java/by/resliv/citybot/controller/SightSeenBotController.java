package by.resliv.citybot.controller;

import by.resliv.citybot.bot.BotState;
import by.resliv.citybot.cache.ChatState;
import by.resliv.citybot.service.buttonhandlers.ButtonHandler;
import by.resliv.citybot.service.messagehandlers.MessageHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@RestController
@AllArgsConstructor
public class SightSeenBotController {

    private ChatState chatState;
    private Map<String, ButtonHandler> buttonsHandlers;
    private Map<String, MessageHandler> messageHandlers;

    @PostMapping(value = "/")
    public BotApiMethod processWebhook(@RequestBody Update update) {

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            ButtonHandler buttonHandler = buttonsHandlers.get(data);
            return buttonHandler.handleButton(update);
        }

        Message message = update.getMessage();
        Long chatId = message.getChatId();
        BotState currentChatBotState = chatState.getCurrentChatBotState(chatId);
        if (currentChatBotState != null) {
            ButtonHandler buttonHandler = buttonsHandlers.get(currentChatBotState.getCommandName());
            return buttonHandler.handleButton(update);
        }

        MessageHandler messageHandler = messageHandlers.get(message.getText());
        if (messageHandler == null) {
            MessageHandler handler = messageHandlers.get("/city");
            return handler.handleMessage(message);
        }
        return messageHandler.handleMessage(update.getMessage());
    }

    @Autowired
    @Qualifier("messageHandlers")
    public void setMessageHandlers(Map<String, MessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
    }

    @Autowired
    @Qualifier("buttonHandlers")
    public void setCallbackHandlers(Map<String, ButtonHandler> buttonHandlers) {
        this.buttonsHandlers = buttonHandlers;
    }

    @Autowired
    public void setChatState(ChatState chatState) {
        this.chatState = chatState;
    }
}