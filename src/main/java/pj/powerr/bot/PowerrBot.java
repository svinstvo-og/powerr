package pj.powerr.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.springframework.stereotype.Component;

@Component
public class PowerrBot extends TelegramLongPollingBot{

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            // Simple command: /add_exercise exercise_name weight reps
            if (messageText.startsWith("/add_exercise")) {
                String[] parts = messageText.split(" ");
                if (parts.length == 4) {
                    String exercise = parts[1];
                    String weight = parts[2];
                    String reps = parts[3];

                    // TODO: db PostgreSQL

                    sendMsg(chatId, "Exercise added: " + exercise + " | " + weight + "kg x " + reps + " reps");
                } else {
                    sendMsg(chatId, "Incorrect format. Use: /add_exercise [exercise] [weight] [reps]");
                }
            } else {
                sendMsg(chatId, "Hello! Use /add_exercise to log your workout.");
            }
        }
    }

    private void sendMsg(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "p0werr_bot";  // name
    }

    @Override
    public String getBotToken() {
        return "7904114720:AAGxdkoO3Vqpx25ygZC5r3nmFDPMLnPWi3k";  // token
    }
}
