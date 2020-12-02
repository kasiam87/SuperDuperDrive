package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.service.ResultService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FilesController {

    private UserService userService;
    private FileService fileService;
    private ResultService resultService;

    public FilesController(UserService userService, FileService fileService, ResultService resultService) {
        this.userService = userService;
        this.fileService = fileService;
        this.resultService = resultService;
    }

    @PostMapping("/add")
    public String addFile(Authentication authentication, @RequestParam("multipartFile") MultipartFile multipartFile, @ModelAttribute File file, Model model) {
        String actionErrorMsg = null;

        try {
            if (multipartFile == null || multipartFile.isEmpty()) {
                actionErrorMsg = "Please select a file to upload. ";
            } else {
                Integer userId = userService.getUser(authentication.getName()).getUserId();
                file.setUserId(userId);
                file.setFilename(StringUtils.cleanPath(multipartFile.getOriginalFilename()));
                file.setContentType(multipartFile.getContentType());
                file.setFileSize(String.valueOf(multipartFile.getSize()));
                try {
                    file.setFileData(multipartFile.getBytes());
                } catch (IOException e) {
                    actionErrorMsg = "Could not retrieve file. ";
                }

                fileService.addFile(file);
            }
        } catch (Exception e) {
            actionErrorMsg = "Something went wrong. Please try again. ";
        }

        resultService.setResultMessage(model, actionErrorMsg);

        return "result";
    }

    @PostMapping("/{fileId}/delete")
    public String deleteFile(@PathVariable("fileId") Integer fileId, File file, Model model) {
        String actionErrorMsg = null;

        try {
            if (!fileService.isFilePresent(file.getFileId())) {
                actionErrorMsg = "Cannot find file. ";
            }
            fileService.deleteFile(file.getFileId());
        } catch (Exception e) {
            actionErrorMsg = "Something went wrong. Please try again. ";
        }

        resultService.setResultMessage(model, actionErrorMsg);
        return "result";
    }

    @GetMapping("/{fileId}/get")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Integer fileId) {
        File file = null;
        try {
            file = fileService.getFile(fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }
}