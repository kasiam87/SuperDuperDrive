package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.service.ResultService;
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
    private ResultService resultService;

    public CredentialsController(UserService userService, CredentialsService credentialsService, ResultService resultService) {
        this.userService = userService;
        this.credentialsService = credentialsService;
        this.resultService = resultService;
    }

    @PostMapping("/add")
    public String addCredentials(Authentication authentication, @ModelAttribute Credential credential, Model model) {
        String actionErrorMsg = null;

        try {
            Integer userId = userService.getUser(authentication.getName()).getUserId();
            credential.setUserId(userId);
            credentialsService.addCredentials(credential);
        } catch (Exception e) {
            actionErrorMsg = "Something went wrong. Please try again. ";
        }

        resultService.setResultMessage(model, actionErrorMsg);

        return "result";
    }

    @PostMapping("/{credentialId}/delete")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, Credential credential, Model model){
        String actionErrorMsg = null;

        try {
            if (!credentialsService.isCredentialPresent(credential.getCredentialId())) {
                actionErrorMsg = "Cannot find credential. ";
            }

            try {
                credentialsService.deleteCredential(credential.getCredentialId());
            } catch (Exception e) {
                model.addAttribute("updateFailed", "Your changes were not saved.");
            }
        } catch (Exception e) {
            actionErrorMsg = "Something went wrong. Please try again. ";
        }

        resultService.setResultMessage(model, actionErrorMsg);

        return "result";
    }

    @PostMapping("/{credentialId}/update")
    public String updateCredential(Credential credential, Model model){
        String actionErrorMsg = null;

        try {
            if (!credentialsService.isCredentialPresent(credential.getCredentialId())) {
                actionErrorMsg = "Cannot find credential. ";
            }

            credentialsService.updateCredential(credential);
        } catch (Exception e) {
            actionErrorMsg = "Something went wrong. Please try again. ";
        }

        resultService.setResultMessage(model, actionErrorMsg);

        return "result";
    }
}