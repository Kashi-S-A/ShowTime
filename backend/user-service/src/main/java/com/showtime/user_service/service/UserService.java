package com.showtime.user_service.service;

import com.showtime.user_service.dto.UserDto;

public interface UserService {

	public String register(UserDto userDto);

	public String login(String email, String password);

}
