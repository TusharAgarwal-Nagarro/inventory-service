package com.xyz.inventory.model;

import java.io.Serializable;

public class ProductRequest implements Serializable {
	
	private UserRequest user;

	public UserRequest getUser() {
		return user;
	}

	public void setUser(UserRequest user) {
		this.user = user;
	}

}
