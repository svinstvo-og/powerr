package pj.powerr.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.stereotype.Component;
import pj.powerr.db.ExerciseRepository;
import pj.powerr.db.UserRepository;
import pj.powerr.entity.Exercise;
import pj.powerr.entity.User;

@Component
public class BotController extends TelegramLongPollingBot{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            // Simple command: /add_exercise exercise_name weight reps
            if (messageText.startsWith("/add_exercise") || messageText.startsWith("/exercise") || messageText.startsWith("/adex")) {
                addExercise(update, chatId, messageText);
            }
            else if (messageText.startsWith("/Id") || messageText.startsWith("/id")){
                getId(update, chatId);
            }
            else {
                sendMsg(chatId, "Incorrect format. Use: /help to list all commands.");
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

    private String getId(Update update, String chatId) {
        String id = update.getMessage().getChatId().toString();
        sendMsg(chatId, "Your id is " + id + ", link it to the web app");
        return id;
    }

    public void addExercise(Update update, String chatId, String messageText) {
//        String[] parts = messageText.split(" ");
//        if (parts.length == 4 || parts.length == 5) {
//
//            String id = update.getMessage().getChatId().toString();
//
//            User user = userRepository.findByTelegramId(id)
//                    .orElseThrow(() -> new RuntimeException("User not found, womp womp"));
//            Exercise exerciseToAdd = new Exercise();
//
//            String exercise = parts[1];
//            String weight = parts[2];
//            String reps = parts[3];
//            if (parts.length == 5) {
//                String muscleGroup = parts[4];
//                exerciseToAdd.setMuscleGroup(muscleGroup);
//            }
//
//
//
//            exerciseToAdd.setName(exercise);
//            exerciseToAdd.setWeight(Integer.parseInt(weight));
//            exerciseToAdd.setReps(Integer.parseInt(reps));
//            exerciseToAdd.setUser(user);
//
//            exerciseRepository.save(exerciseToAdd);
//
//            sendMsg(chatId, "Exercise added: " + exercise + " | " + weight + "kg x " + reps + " reps");
//        } else {
//            sendMsg(chatId, "Incorrect format. Use: /add_exercise [exercise] [weight] [reps] [*optional* muscle group]");
//        }
    }
}
