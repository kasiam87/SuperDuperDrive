package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.service.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private NotesService notesService;
    private CredentialsService credentialsService;
    private FileService fileService;

    public HomeController(UserService userService, NotesService notesService, CredentialsService credentialsService, FileService fileService) {
        this.userService = userService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
        this.fileService = fileService;
    }

    @GetMapping
    public String homeView(Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("availableNotes", notesService.getAllNotes(userId));
        model.addAttribute("availableCredentials", credentialsService.getAllCredentials(userId));
        model.addAttribute("availableFiles", fileService.getAllFiles(userId));
        return "home";
    }
}
