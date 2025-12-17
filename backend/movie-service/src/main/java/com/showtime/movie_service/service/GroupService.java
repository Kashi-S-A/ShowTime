package com.showtime.movie_service.service;

import java.util.List;

import com.showtime.movie_service.dto.GroupDto;
import com.showtime.movie_service.dto.GroupRequestDto;

public interface GroupService {
	
	GroupDto createGroup(GroupRequestDto request);
	
	GroupDto getGroupById(Integer id);
	
	List<GroupDto> getGroupsByMovieId(Integer movieId);
	
	List<GroupDto> getGroupsByMovieIdAndType(Integer movieId, String type);
	
	GroupDto updateGroup(Integer id, GroupRequestDto request);
	
	void deleteGroup(Integer id);


	
	
	

}
