package com.groupthree.myhomesbe.auth.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
	private String accessToken;
	private String type = "Bearer";
	//	private String username;
	private String id;
	private String email;
	private String firstName;
	private String lastName;
	private String accountType;
	private String company;
	private String plan;
	private List<String> roles;

	public JwtResponse(
			String accessToken,
			String id,
//			String username,
			String email,
			String firstName,
			String lastName,
			String accountType,
			String company,
			String plan
//			List<String> roles
	) {
		this.accessToken = accessToken;
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountType = accountType;
		this.company = company;
		this.plan = plan;
	}

}