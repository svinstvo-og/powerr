package pj.powerr.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.stereotype.Component;
import pj.powerr.controller.ExerciseController;
import pj.powerr.db.ExerciseRepository;
import pj.powerr.db.UserRepository;
import pj.powerr.entity.Exercise;
import pj.powerr.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

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
            if (messageText.startsWith("/add_exercise") || messageText.startsWith("/exercise") || messageText.startsWith("/add")|| messageText.startsWith("/adex")) {
                addExercise(update, chatId, messageText);
            }
            else if (messageText.startsWith("/Id") || messageText.startsWith("/id")){
                getId(update, chatId);
            }
            else if (messageText.startsWith("/list")) {
                listExercises(update, chatId);
            }
            else if (messageText.startsWith("/log")) {
                log(update, chatId);
            }
            else if (messageText.startsWith("/help")) {
                listExercises(update, chatId);
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
        String[] parts = messageText.split(" ");
        if (parts.length == 5) {

            String id = update.getMessage().getChatId().toString();

            User user = userRepository.findByTelegramId(id)
                    .orElseThrow(() -> new RuntimeException("User not found, womp womp"));
            Exercise exerciseToAdd = new Exercise();

            String exercise = parts[1];
            String weight = parts[2];
            String reps = parts[3];
            String sets = parts[4];

            exerciseToAdd.setName(exercise);
            exerciseToAdd.setWeight(Integer.parseInt(weight));
            exerciseToAdd.setReps(Integer.parseInt(reps));
            exerciseToAdd.setSets(Integer.parseInt(sets));
            exerciseToAdd.setCreated(LocalDate.now());
            exerciseToAdd.setUser(user);

            exerciseRepository.save(exerciseToAdd);

            sendMsg(chatId, "Exercise added: " + exercise + " | " + sets + " sets @ "+ weight + "kg x " + reps + " reps");
        } else {
            sendMsg(chatId, "Incorrect format. Use: /add_exercise [exercise] [weight] [reps] [sets]");
        }
    }

    public void listExercises(Update update, String chatId) {
        User user = userRepository.findByTelegramId(chatId)
                .orElseThrow(() -> new IllegalArgumentException("User with such telegram id wasn't found"));
        List<Exercise> exercises = exerciseRepository.findByUserId(user.getId());
        HashSet<String> uniqueExercises = new HashSet<>();
        for (Exercise exercise : exercises) {
            uniqueExercises.add(exercise.getName());
        }
        sendMsg(chatId, "List of exercises: " + uniqueExercises.toString());
    }

    public void log(Update update, String chatId) {
        String[] parts = update.getMessage().getText().split(" ");

        if (parts.length == 2) {
            User user = userRepository.findByTelegramId(chatId)
                    .orElseThrow(() -> new IllegalArgumentException("User with such telegram id wasn't found"));

            try {
                List<Exercise> log = exerciseRepository.findByUserIdAndName(user.getId(), parts[1]);
                StringBuilder msg = new StringBuilder().append(parts[1]);
                msg.append(" : \n");
                String instance;
                for (int i = 0; i < log.size(); i++) {
                    instance = log.get(i).getCreated() + ": " + log.get(i).getReps() + " reps @ " + log.get(i).getWeight() + "kg * " + log.get(i).getSets() + " sets";
                    msg.append(instance).append("\n");
                }
                sendMsg(chatId, msg.toString());
            }
            catch (Exception e) {
                sendMsg(chatId, e.getMessage());
            }
        }
        else {
            sendMsg(chatId, "Wrong format. Use: /log [exercise name]. To list all existing exercises use /list");
        }
    }

    public void help(Update update, String chatId) {

    }
}
