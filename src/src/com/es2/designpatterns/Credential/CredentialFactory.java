package src.com.es2.designpatterns.Credential;


import src.com.es2.designpatterns.Credential.Generator.GenerationAlgorithm;
import src.com.es2.designpatterns.Credential.Generator.PasswordGenerator;
import src.com.es2.designpatterns.Credential.CredentialStorage;
        //Storage.CredentialStorage;


import java.util.UUID;

public class CredentialFactory {
    // The single instance
    private static CredentialFactory instance;
    private PasswordGenerator passwordGenerator;
    private CredentialStorage storage;

    // Private constructor
    private CredentialFactory(CredentialStorage storage) {
        this.passwordGenerator = new PasswordGenerator();
        this.storage = storage;
    }

    // Global access point
    public static synchronized CredentialFactory getInstance(CredentialStorage storage) {
        if (instance == null) {
            instance = new CredentialFactory(storage);
        }
        return instance;
    }

    public void showMessage() {
        
        System.out.println("Singleton instance: " + this);
    }

    public Credential createCredential(CredentialType type, SecurityCriteria criteria) {
        String id = UUID.randomUUID().toString();
        Credential credential = null;

        switch (type) {
            case PASSWORD:
                String password = passwordGenerator.generatePassword(criteria);
                credential = new Credential(id, "Password", password);
                break;

            case API_KEY:
                // API keys might have different requirements
                SecurityCriteria apiCriteria = new SecurityCriteria.Builder().includeSymbols(false).algorithm("standard").build();

                String apiKey = passwordGenerator.generatePassword(apiCriteria);
                Credential apiCredential = new Credential(id, "API Key", apiKey);
                apiCredential.setMetadata("type", "api");
                //return apiCredential;
                break;

            case SECRET_KEY:
                // Secret keys might be longer and more complex
                SecurityCriteria secretCriteria = new SecurityCriteria.Builder().algorithm("enhanced").build();
                String secretKey = passwordGenerator.generatePassword(secretCriteria);
                Credential secretCredential = new Credential(id, "Secret Key", secretKey);
                secretCredential.setMetadata("type", "secret");
                //return secretCredential;
                break;

            case CREDIT_CARD:
                // Credit card credentials would store card details
                Credential ccCredential = new Credential(id, "Credit Card", "");
                ccCredential.setMetadata("type", "cc");
                return ccCredential;

            case PIN:
                SecurityCriteria pinCriteria = new SecurityCriteria.Builder().length(4).includeSymbols(false).algorithm("pin").build();
                String pin = passwordGenerator.generatePassword(pinCriteria);
                Credential pinCredential = new Credential(id, "PIN", pin);
                pinCredential.setMetadata("type", "pin");
                //return pinCredential;
                break;

            default:
                throw new IllegalArgumentException("Unsupported credential type: " + type);
        }
        storage.saveCredential(credential);
        return credential;
    }

}