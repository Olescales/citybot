package by.resliv.citybot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.WebhookInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@PropertySource("classpath:application.properties")
public class MyCustomBot extends TelegramWebhookBot {

    @Value("${telegram.bot.name}")
    private String botUserName;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.webhookpath}")
    private String path;

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        System.out.println("Don't know why this method exist");
        try {
            WebhookInfo webhookInfo = getWebhookInfo();
            System.out.println(webhookInfo.toString());
            System.out.println(webhookInfo.getUrl());
            System.out.println(webhookInfo.getMaxConnections());
            System.out.println(webhookInfo.isHasCustomCertificate());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return new SendMessage();
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
