package src.com.es2.designpatterns.Credential;

import java.util.ArrayList;
import java.util.List;

public class DatabaseStorage implements CredentialStorage {
    private List<Credential> database = new ArrayList<>();

    @Override
    public void saveCredential(Credential credential) {
        database.add(credential);
    }

    @Override
    public List<Credential> loadCredentials() {
        return new ArrayList<>(database);
    }
}
