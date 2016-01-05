package logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.*;

import com.google.gson.Gson;

import database.UserDB;
import model.User;
import vm.UserViewModel;

@Path("/user")
public class UserLogic {
	@SuppressWarnings("unchecked")
	@POST
	@Path("/login")
	@Produces("text/plain")
	@Consumes("application/json")
	public String login(String json) {
		Gson gson = new Gson();
		Map<String, String> jsonLog = gson.fromJson(json, HashMap.class);
		User usr = new User(jsonLog.get("username"), jsonLog.get("password"));
		boolean success = UserDB.checkUser(usr);
		if (success)
			return "200";
		else
			return "404";
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/register")
	@Produces("text/plain")
	@Consumes("application/json")
	public String addUser(String json) {
		Gson gson = new Gson();
		Map<String, String> jsonLog = gson.fromJson(json, HashMap.class);
		User usr = new User(jsonLog.get("username"), jsonLog.get("password"), jsonLog.get("name"),
				Date.valueOf(jsonLog.get("birthdate")), jsonLog.get("gender"));
		boolean success = UserDB.addUser(usr);
		if (success)
			return "200";
		else
			return "409";
	}

	@GET
	@Path("/usernames")
	@Produces("application/json")
	public String getUserNames(@QueryParam("search") String search) {
		ArrayList<String> names = new ArrayList<String>();
		List<User> users = UserDB.searchUserName(search);

		for (User user : users) {
			names.add(user.getUsername());
		}
		String json = null;
		if (names.size() > 0) {
			Gson gson = new Gson();
			json = gson.toJson(names, ArrayList.class);
		}
		return json;
	}

	@GET
	@Path("/userinfo")
	@Produces("application/json")
	public String getUserInfo(@QueryParam("user") String user) {
		String json = null;
		User usr = UserDB.getUserInfo(user);
		UserViewModel vm = new UserViewModel(usr.getUsername(),usr.getPassword(),usr.getBirthDate(),usr.getGender());
		Gson gson = new Gson();
		json = gson.toJson(vm, UserViewModel.class);
		return json;
	}
}
