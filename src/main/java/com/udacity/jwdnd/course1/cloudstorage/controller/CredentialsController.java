package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credentials")
public class CredentialsController {

    private UserService userService;
    private CredentialsService credentialsService;

    public CredentialsController(UserService userService, CredentialsService credentialsService) {
        this.userService = userService;
        this.credentialsService = credentialsService;
    }

    @PostMapping("/add")
    public String addCredentials(Authentication authentication, @ModelAttribute Credential credential, Model model){
        String actionErrorMsg = null;

        Integer userId = userService.getUser(authentication.getName()).getUserId();
        credential.setUserId(userId);
        credentialsService.addCredentials(credential);

        if (actionErrorMsg == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFailed", actionErrorMsg);
        }

        return "result";
    }

    @PostMapping("/{credentialId}/delete")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, Credential credential, Model model){
        String actionErrorMsg = null;

        if (!credentialsService.isCredentialPresent(credential.getCredentialId())) {
            actionErrorMsg = "Cannot find credential. ";
        }

        try {
            credentialsService.deleteCredential(credential.getCredentialId());
        } catch (Exception e) {
            model.addAttribute("updateFailed", "Your changes were not saved.");
        }

        if (actionErrorMsg == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFailed", actionErrorMsg);
        }
        return "result";
    }

    @PostMapping("/{credentialId}/update")
    public String updateCredential(Credential credential, Model model){
        String actionErrorMsg = null;

        if (!credentialsService.isCredentialPresent(credential.getCredentialId())){
            actionErrorMsg = "Cannot find credential. ";
        }

        credentialsService.updateCredential(credential);

        if (actionErrorMsg == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFailed", actionErrorMsg);
        }
        return "result";
    }
}