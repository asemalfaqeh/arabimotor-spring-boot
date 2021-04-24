package com.af.arabimotors.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class UserChangePasswordRequest {

	@NotBlank(message = "{password.notempty}")
	@Size(min = 8, max = 50,message = "{password.min}")
	private String new_password;
	
	@NotBlank(message = "{confirmpassword}")
	private String confirm_password;
	
	@NotBlank(message = "{password.notempty}")
	private String old_password;
	
	
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

	public String getOld_password() {
		return old_password;
	}

	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}


	
}
