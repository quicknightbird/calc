import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Calc extends TelegramLongPollingBot {
    protected Calc(DefaultBotOptions options) {
        super(options);
    }

    @SneakyThrows
    public static void main(String[] args) {
        Calc bot = new Calc(new DefaultBotOptions());
        TelegramBotsApi botApi = new TelegramBotsApi(DefaultBotSession.class);
        botApi.registerBot(bot);

    }

    @Override
    public String getBotUsername() {
        return "@calculatedsomebot";
    }

    @Override
    public String getBotToken() {
        return "5495935138:AAFyq2u6277p47rwmSRPuegvw2-H9PTtUeA";
    }

    public static String answer (String str){
        operation op = new operation();
        int intAnswer = op.Calculate(op.CheckBrackets(str));
        return String.valueOf(intAnswer);
    }


    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                execute(SendMessage.builder()
                        .chatId(message.getChatId()
                                .toString())
                        .text(answer(message.getText()))
                        .build());
            }
        }

    }
}
