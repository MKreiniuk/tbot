package dbConect.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import dbConect.config.Botconfig;
import dbConect.dto.DBData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
	
	@Autowired
	MongoTemplate repo;

	private final Botconfig config;

	public TelegramBot(Botconfig config) {
		this.config = config;

		List<BotCommand> listCom = new ArrayList<>();
		listCom.add(new BotCommand("/start", "greeting"));
		listCom.add(new BotCommand("/list", "All the lessons"));
		listCom.add(new BotCommand("/help", "help"));

		try {
			this.execute(new SetMyCommands(listCom, new BotCommandScopeDefault(), null));
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {

			String mes = update.getMessage().getText();
			long chatId = update.getMessage().getChatId();

			switch (mes) {
			case "/start":
				botStarCommand(chatId, update.getMessage().getChat().getFirstName());
				break;

			case "/help":
				sendMessage(chatId, "Тебе никто не поможет");
				break;
				
			case "/list":
				makelist(chatId);
				break;

			default:
				sendMessage(chatId, "We are working on it");
					log.info("cant`handle request");

				break;
			}

		}

	}

//	private void makeCalc(long chatId) {
//	
//		
//	InlineKeyboardMarkup calc = new InlineKeyboardMarkup();
//	List<InlineKeyboardButton> oper = new ArrayList<InlineKeyboardButton>();
//	InlineKeyboardButton plus = new InlineKeyboardButton();
//	plus.setText("+");
//	plus.setCallbackData("add");
//	oper.add(plus);
//	
//	InlineKeyboardButton minus = new InlineKeyboardButton();
//	minus.setText("-");
//	minus.setCallbackData("substract");
//	oper.add(minus);
//	List<List<InlineKeyboardButton>> oper1 = new ArrayList<List<InlineKeyboardButton>>();
//	oper1.add(oper);
//	
//	calc.setKeyboard(oper1);
//	SendMessage mess = new SendMessage();
//	mess.setReplyMarkup(calc);
//	mess.setChatId(chatId);
//	mess.setText("Какое действие ты хочешь выполнить?");
//	try {
//	execute(mess);
//	} catch (TelegramApiException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	

	
	
	
	
		
	private void makelist(long chatId) {
		
	List<DBData>  in =repo.findAll(DBData.class);

	for (DBData r : in) {
		String print = r.getName();
		SendMessage mess = new SendMessage();
		mess.setChatId(chatId);
		mess.setText(print);
	
		try {
			execute(mess);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	
	}
	
	
		
		
	}

	

	private void botStarCommand(long chatId, String name) {

		String res = "Hi " + name + ", how can I help you";
		sendMessage(chatId, res);
			log.info("Replied to user "+name);

	}

	private void sendMessage(long chatId, String text) {
		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		message.setText(text);

		try {
			execute(message);
		} catch (TelegramApiException e) {
				log.error("Error: " + e.getMessage());
		}

	}

	@Override
	public String getBotUsername() {
		return config.getBotName();
	}

	@Override
	public String getBotToken() {
		return config.getToken();
	}

}
