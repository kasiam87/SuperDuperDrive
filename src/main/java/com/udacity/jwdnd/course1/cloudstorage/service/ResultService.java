package com.udacity.jwdnd.course1.cloudstorage.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ResultService {

    public void setResultMessage(Model model, String actionErrorMsg) {
        if (actionErrorMsg == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFailed", actionErrorMsg);
        }
    }
}
