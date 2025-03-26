package src.com.es2.designpatterns.Credential;

import java.util.ArrayList;
import java.util.List;

public class CloudStorage implements CredentialStorage {
    private List<Credential> cloudStorage = new ArrayList<>();

    @Override
    public void saveCredential(Credential credential) {
        cloudStorage.add(credential);
    }

    @Override
    public List<Credential> loadCredentials() {
        return new ArrayList<>(cloudStorage);
    }
}
