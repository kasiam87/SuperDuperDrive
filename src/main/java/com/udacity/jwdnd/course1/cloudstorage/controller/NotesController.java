package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
public class NotesController {

    private UserService userService;
    private NotesService notesService;

    public NotesController(UserService userService, NotesService notesService) {
        this.userService = userService;
        this.notesService = notesService;
    }

    @PostMapping("/add")
    public String addNote(Authentication authentication, @ModelAttribute Note note, Model model){
        String actionErrorMsg = null;

        Integer userId = userService.getUser(authentication.getName()).getUserId();
        note.setUserId(userId);
        notesService.addNote(note);

        if (actionErrorMsg == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFailed", actionErrorMsg);
        }

        return "result";
    }

    @PostMapping("/{noteId}/delete")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Note note, Model model){
        String actionErrorMsg = null;

        if (!notesService.isNotePresent(note.getNoteId())){
            actionErrorMsg = "Cannot find note.  ";
        }
        notesService.deleteNote(note.getNoteId());

        if (actionErrorMsg == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFailed", actionErrorMsg);
        }
        return "result";
    }

    @PostMapping("/{noteId}/update")
    public String updateNote(Note note, Model model){
        String actionErrorMsg = null;

        if (!notesService.isNotePresent(note.getNoteId())){
            actionErrorMsg = "Cannot find note. ";
        }

        notesService.updateNote(note);

        if (actionErrorMsg == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFailed", actionErrorMsg);
        }
        return "result";
    }
}