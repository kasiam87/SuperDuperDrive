package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.view.CredentialView;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<CredentialView> getAllCredentials(Integer userId){
        List<Credential> credentialTable = credentialsMapper.getCredentials(userId);
        List<CredentialView> credential = credentialTable.stream().map(Credential::toModel).collect(Collectors.toList());
        credential.forEach(c -> {
            String decryptedPassword = encryptionService.decryptValue(c.getPassword(), c.getKey());
            c.setDecryptedPassword(decryptedPassword);
        });
        return credential;
    }

    public void addCredentials(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        credentialsMapper.addCredential(credential);
    }

    public void updateCredential(Credential credential) {
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
        credentialsMapper.updateCredential(credential);
    }

    public void deleteCredential(Integer credentialId) {
        credentialsMapper.deleteCredential(credentialId);
    }
}
