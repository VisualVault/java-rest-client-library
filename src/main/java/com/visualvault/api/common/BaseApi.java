package com.visualvault.api.common;

import com.visualvault.api.security.Token;

public class BaseApi {

	protected Token token;
	
	public BaseApi(Token token) {
		this.token = token;
	}
	
}
