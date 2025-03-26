package src.com.es2.designpatterns.Credential;

import java.util.List;

public interface CredentialStorage {
    void saveCredential(Credential credential);
    List<Credential> loadCredentials();

}
