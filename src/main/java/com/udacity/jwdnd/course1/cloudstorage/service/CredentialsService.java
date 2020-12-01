package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class CredentialsService {

    private final CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public boolean isCredentialPresent(Integer credentialId){
        return getCredential(credentialId) != null;
    }

    public Credential getCredential(Integer credentialId){
        return credentialsMapper.getCredential(credentialId);
    }

    public List<Credential> getAllCredentials(Integer userId){
        return credentialsMapper.getCredentials(userId);
    }

    public void addCredentials(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);
        credential.setKey(encodedSalt);
        credential.setPassword(encryptedPassword);
        credentialsMapper.addCredential(credential);
    }

    public void updateCredential(Credential credential) {
        System.out.println(credential.getPassword());
        System.out.println(credential.getKey());
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
        credentialsMapper.updateCredential(credential);
    }

    public void deleteCredential(Integer credentialId) {
        credentialsMapper.deleteCredential(credentialId);
    }
}
