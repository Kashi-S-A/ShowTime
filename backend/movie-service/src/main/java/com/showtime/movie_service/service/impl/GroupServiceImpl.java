package com.showtime.movie_service.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import com.showtime.movie_service.dto.GroupDto;
import com.showtime.movie_service.dto.GroupRequestDto;
import com.showtime.movie_service.entity.Group;
import com.showtime.movie_service.entity.Movie;  
import com.showtime.movie_service.enums.MovieType;
import com.showtime.movie_service.repository.GroupRepository;
import com.showtime.movie_service.repository.MovieRepository;
import com.showtime.movie_service.service.GroupService;

@Service 
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MovieRepository movieRepository;


    public Group toEntity(GroupRequestDto dto) {
        Group group = new Group();
        BeanUtils.copyProperties(dto, group);
        
        if (dto.getMovieId() != null) {
            Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
            group.setMovie(movie);
        }
        
        if (dto.getType() != null) {
            group.setType(MovieType.valueOf(dto.getType()));
        }
        return group;
    }

   
    public GroupDto toDto(Group group) {
        GroupDto dto = new GroupDto();
        BeanUtils.copyProperties(group, dto);
        dto.setType(group.getType().name());
        return dto;
    }

    @Override
    public GroupDto createGroup(GroupRequestDto request) {
        Group group = toEntity(request);
        return toDto(groupRepository.save(group));
    }

    @Override
    public GroupDto getGroupById(Integer id) {
        return toDto(groupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Group not found: " + id)));
    }

  
    @Override
    public List<GroupDto> getGroupsByMovieId(Integer movieId) {
        List<Group> groups = groupRepository.findByMovieMovieId(movieId);  
        List<GroupDto> dtos = new ArrayList<>();  
        for (Group group : groups) {
            dtos.add(toDto(group));
        }
        return dtos;
    }

	
	@Override
	public List<GroupDto> getGroupsByMovieIdAndType(Integer movieId, String type) {
	    MovieType movieType = MovieType.valueOf(type.toUpperCase());

	    List<Group> groups = groupRepository.findByMovieMovieIdAndType(movieId, movieType);
	    List<GroupDto> dtos = new ArrayList<>();
	    for (Group group : groups) {
	        dtos.add(toDto(group));
	    }
	    return dtos;
	}


    @Override
    public GroupDto updateGroup(Integer id, GroupRequestDto request) {
        Group existing = groupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Group not found: " + id));
        
        BeanUtils.copyProperties(request, existing);  
        
        if (request.getType() != null) {
            existing.setType(MovieType.valueOf(request.getType()));
        }
        if (request.getMovieId() != null) {
            existing.setMovie(movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found")));
        }
        return toDto(groupRepository.save(existing));
    }

    @Override
    public void deleteGroup(Integer id) {
        Group group = groupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Group not found: " + id));
        groupRepository.delete(group);
    }






	
}
