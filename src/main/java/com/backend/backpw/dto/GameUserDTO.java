package com.backend.backpw.dto;

public class GameUserDTO {
	
	private Long id;
    private String truename;
    private String email;
    
	public GameUserDTO(Long id, String truename, String email) {
		this.id = id;
		this.truename = truename;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
