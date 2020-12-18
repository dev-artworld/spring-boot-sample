package com.equinix.appops.dart.portal.model.domain;


public class UserThreadLocal {

	public static final ThreadLocal<User> userThreadLocalVar = new ThreadLocal<User>();

	public UserThreadLocal(User user) {
		userThreadLocalVar.set(user);
	}

	public User getUser() {
		return userThreadLocalVar.get();
	}

	public void removeUser() {
		userThreadLocalVar.remove();
	}
}
