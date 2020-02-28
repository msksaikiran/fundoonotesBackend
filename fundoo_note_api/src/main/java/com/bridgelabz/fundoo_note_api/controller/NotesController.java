package com.bridgelabz.fundoo_note_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.exception.UserNotFoundException;
import com.bridgelabz.fundoo_note_api.service.NoteService;

@RestController
public class NotesController {
	@Autowired
	private NoteService noteService;

	@GetMapping("/user/{id}/notes")
	public List<Noteinfo> getAllNotes(@PathVariable String id) {
		return noteService.getAllNotes(id);
	}

	@PostMapping(value = "/topic/{id}/notes")
	public void addNotes(@RequestBody NoteDto notes,@PathVariable String id) {

		//user.getId();
		//notes.setUser(new User(Integer.parseInt(id)));
		noteService.addNotes(notes,id);
	}

	@GetMapping(value = "/user/{id}/notes/{id}")
	public Noteinfo getNote(@RequestBody Noteinfo notes,@PathVariable String noteid) {
		Noteinfo result = noteService.getNote(noteid);
		if (result == null)
			throw new UserNotFoundException(noteid + " Record not Exist in Database");

		return result;

	}

	@DeleteMapping(value = "/user/{id}/notes/{id}")
	public void deleteNote(@RequestBody Noteinfo notes, @PathVariable String id) {
		noteService.removeNotes(notes, id);
	}

}