package com.af.arabimotors.model.request;

import javax.validation.constraints.NotBlank;

public class ResetPasswordRequest {

	private String token;
	
	@NotBlank(message = "{password.notempty}")
	private String new_password;
	
	@NotBlank(message = "{confirmpassword}")
	private String confirm_password;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}

	public String getConfirm_password() {
		return confirm_password;
	}

	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}

	@Override
	public String toString() {
		return "ResetPasswordRequest [token=" + token + ", new_password=" + new_password + ", confirm_password="
				+ confirm_password + "]";
	}
	
	
	
}
