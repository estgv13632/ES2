package src.com.es2.designpatterns.Credential;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LocalFileStorage implements CredentialStorage {
    private static final String FILE_NAME = "credentials.dat";

    @Override
    public void saveCredential(Credential credential) {
        List<Credential> credentials = loadCredentials();
        credentials.add(credential);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(credentials);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Credential> loadCredentials() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Credential>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
