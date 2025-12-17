package com.showtime.movie_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.showtime.movie_service.dto.GroupDto;
import com.showtime.movie_service.dto.GroupRequestDto;
import com.showtime.movie_service.service.GroupService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/groups")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	

	@PostMapping
	public ResponseEntity<GroupDto> createGroup(@RequestBody GroupRequestDto request) {
		GroupDto group = groupService.createGroup(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(group);
	}
	
	//get Single group by id
	@GetMapping("/{id}")
	public ResponseEntity<GroupDto> getGroupById(@PathVariable Integer id) {
		try {
			GroupDto group = groupService.getGroupById(id);
			return ResponseEntity.ok(group);
			
		} catch(RuntimeException ex){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	//get All groups by movie
	@GetMapping("/movie/{movieId}")
	public List<GroupDto> getGroupsByMovieId(@PathVariable Integer movieId) {
		return groupService.getGroupsByMovieId(movieId);
	}
	
	//get cast and crew only for a movie
	@GetMapping("/movie/{movieId}/{type}")
	public List<GroupDto> getGroupsbyMovieIdAndType(@PathVariable Integer movieId, @PathVariable String type){
	      return groupService.getGroupsByMovieIdAndType(movieId, type);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<GroupDto> updateGroup(@PathVariable Integer id, @RequestBody GroupRequestDto request) {
				try {
					GroupDto updated = groupService.updateGroup(id, request);
					return ResponseEntity.ok(updated);
				}catch(RuntimeException ex){
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				}
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteGroup(@PathVariable Integer id) {
	    try {
	        groupService.deleteGroup(id);
	        return ResponseEntity.noContent().build();  // 204
	    } catch (RuntimeException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}

	



}
