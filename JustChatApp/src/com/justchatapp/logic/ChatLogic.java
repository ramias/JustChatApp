package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.*;

import com.google.gson.Gson;

import database.ChatDB;

import model.Chat;
import vm.ChatViewModel;

@Path("chat")
public class ChatLogic {
	@SuppressWarnings("unchecked")
	@POST
	@Path("/sendmessage")
	@Consumes("application/json")
	public String sendMessage(String json) {
		Gson gson = new Gson();
		Map<String, String> jsonChat = gson.fromJson(json, HashMap.class);
		Chat chat = new Chat(jsonChat.get("chater"), jsonChat.get("chatee"), jsonChat.get("message"));
		return String.valueOf(ChatDB.addChat(chat));
	}

	@GET
	@Path("/history")
	@Produces("application/json")
	public String getChatHistory(@QueryParam("chater") String chater, @QueryParam("chatee") String chatee) {
		Chat chat = new Chat(chater, chatee);
		List<Chat> originalChat = new ArrayList<Chat>();
		List<ChatViewModel> chatvm = new ArrayList<ChatViewModel>();

		originalChat = ChatDB.getChatHistory(chat);
		originalChat.forEach(c -> c.setMessage(c.getMessage().replaceAll("(.{60})", "$1\n")));
		originalChat.forEach(c -> chatvm.add(new ChatViewModel(c)));
		String json = null;
		if (chatvm.size() > 0) {
			Gson gson = new Gson();
			json = gson.toJson(chatvm, ArrayList.class);
		}
		return json;
	}
}