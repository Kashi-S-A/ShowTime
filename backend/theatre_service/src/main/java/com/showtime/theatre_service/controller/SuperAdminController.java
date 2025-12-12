package com.showtime.theatre_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.showtime.theatre_service.dto.AdminApprovalRequestDTO;
import com.showtime.theatre_service.dto.ApproveAdminRequest;
import com.showtime.theatre_service.entity.SuperAdmin;
import com.showtime.theatre_service.entity.TheaterReqStatus;
import com.showtime.theatre_service.entity.Theatre;
import com.showtime.theatre_service.entity.TheatreAdmin;
import com.showtime.theatre_service.repository.TheatreRepo;
import com.showtime.theatre_service.service.SuperAdminService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/superAdmin")
public class SuperAdminController {
	
	@Autowired
	private SuperAdminService superAdminService;
	
	@Autowired
	private TheatreRepo theatreRepo;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(
	        @RequestParam String email,
	        @RequestParam String password) {

	    try {
	        SuperAdmin sa = superAdminService.login(email, password);
	        return ResponseEntity.ok(sa);   
	    }
	    catch (Exception e) {
	        return ResponseEntity.badRequest().body(e.getMessage()); 
	    }
	}

	
	@GetMapping("/pendingAdmin")
	public ResponseEntity<?> getPendingAdmins(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "createdDateTime") String sortBy,
	        @RequestParam(defaultValue = "asc") String sortDir) {

	    try {
	        Page<TheatreAdmin> pending = superAdminService.getPendingTheatreAdmin(page, size, sortBy, sortDir);

	        if (pending.isEmpty()) {
	            return ResponseEntity.ok("No pending Theatre Admin requests found.");
	        }

	        return ResponseEntity.ok(pending);
	    }
	    catch (Exception e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}

	
	@PutMapping("/approveAdmin")
	public ResponseEntity<?> approveTheatreAdmin(@RequestBody ApproveAdminRequest req) {

	    try {
	        TheatreAdmin approvedAdmin =
	                superAdminService.approveTheatreAdmin(req.getTaid(), req.getSaid());

	        return ResponseEntity.ok(approvedAdmin); 
	    }
	    catch (Exception e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}

	
	@PutMapping("/rejectAdmin")
	public ResponseEntity<?> rejectTheatreAdmin(
	        @RequestParam Integer said,
	        @RequestParam Integer taid,
	        @RequestBody AdminApprovalRequestDTO dto) {

	    try {
	        TheatreAdmin rejectedAdmin =
	                superAdminService.rejectTheatreAdmin(said, taid, dto);

	        return ResponseEntity.ok(rejectedAdmin); 
	    }
	    catch (Exception e) {
	        return ResponseEntity.badRequest().body(e.getMessage()); 
	    }
	}

	//pending theatre 
	@GetMapping("/pendingTheatres")
	public ResponseEntity<Page<Theatre>> getPendingTheatres(
	        @RequestParam(defaultValue = "0") Integer page,
	        @RequestParam(defaultValue = "2") Integer size,
	        @RequestParam(defaultValue = "tid") String sortBy,
	        @RequestParam(defaultValue = "asc") String sortDir
	) {
	    Page<Theatre> theatres = superAdminService.getPendingTheatres(page, size, sortBy, sortDir);
	    return ResponseEntity.ok(theatres);
	}

	//approve thretre
	@PutMapping("/approveTheatre")
	public ResponseEntity<?> approveTheatre(
	        @RequestParam Integer tid,
	        @RequestParam Integer said) {

	    try {
	        Theatre approved = superAdminService.approveTheatre(tid, said);
	        return ResponseEntity.ok(approved);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(400).body(e.getMessage());
	    }
	}

	
	//Reject theatre
	@PutMapping("/rejectTheatre")
	public ResponseEntity<?> rejectTheatre(
	        @RequestParam Integer tid,
	        @RequestParam Integer said,
	        @RequestBody AdminApprovalRequestDTO dto) {

	    try {
	        Theatre rejected = superAdminService.rejectTheatre(tid, said, dto);
	        return ResponseEntity.ok(rejected);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(400).body(e.getMessage());
	    }
	}

	
}
