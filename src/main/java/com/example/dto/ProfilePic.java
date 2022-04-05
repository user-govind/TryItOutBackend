package com.example.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProfilePic {
	
	private int id;
	private MultipartFile profilePic;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public MultipartFile getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(MultipartFile profilePic) {
		this.profilePic = profilePic;
	} 
}
