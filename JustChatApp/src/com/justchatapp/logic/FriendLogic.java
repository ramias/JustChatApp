package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.*;

import com.google.gson.Gson;

import database.FriendDB;
import model.User;

@Path("/friend")
public class FriendLogic {
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addfriend")
	@Produces("text/plain")
	@Consumes("application/json")
	public String addFriend(String json) {
		Gson gson = new Gson();
		Map<String, String> jsonFriend = gson.fromJson(json, HashMap.class);
		return String.valueOf(FriendDB.addFriend(new User(jsonFriend.get("user")), new User(jsonFriend.get("friend"))));
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/removefriend")
	@Produces("text/plain")
	@Consumes("application/json")
	public String removeFriend(String json) {
		Gson gson = new Gson();
		Map<String, String> jsonFriend = gson.fromJson(json, HashMap.class);
		return String
				.valueOf(FriendDB.removeFriend(new User(jsonFriend.get("user")), new User(jsonFriend.get("friend"))));
	}

	@GET
	@Path("/friendlist")
	@Produces("application/json")
	public String getFriends(@QueryParam("user") String userName) {
		User user = new User(userName);
		List<String> friendNames = new ArrayList<String>();
		List<User> friendList = FriendDB.getFriends(user);
		for (User friend : friendList) {
			friendNames.add(friend.getUsername());
		}
		String json = null;
		if (friendNames.size() > 0) {
			Gson gson = new Gson();
			json = gson.toJson(friendNames, ArrayList.class);
		}
		return json;
	}

	/**
	 * @param user
	 * @param friend
	 * @return String "OWN" if the user, "KNOWN" if he is a friend, "UNKNOWN" if
	 *         he is not a friend.
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/isfriend")
	@Produces("text/plain")
	@Consumes("application/json")
	public String isFriend(String json) {
		Gson gson = new Gson();
		Map<String, String> jsonFriend = gson.fromJson(json, HashMap.class);
		String user = jsonFriend.get("user");
		String friend = jsonFriend.get("friend");
		String isFriend = "";
		if (user.equals(friend)) {
			isFriend = "OWN";
		} else {
			isFriend = "UNKNOWN";
			List<User> friends = FriendDB.getFriends(new User(user));
			for (User fr : friends) {
				if (fr.equals(friend)) {
					isFriend = "KNOWN";
					break;
				}
			}
		}
		return isFriend;
	}
}
