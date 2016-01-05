package com.justchatapp.logic;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

@Path("rest")
public class LogicApp extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(UserLogic.class);
		classes.add(FriendLogic.class);
		classes.add(ChatLogic.class);
        
		return classes;
	}
}
