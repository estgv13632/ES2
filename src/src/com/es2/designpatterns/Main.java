package src.com.es2.designpatterns;

import java.util.Map;

import src.com.es2.designpatterns.Configuration.ConfigurationManager;
import src.com.es2.designpatterns.Credential.*;
import src.com.es2.designpatterns.Credential.Generator.PasswordGenerator;

public class Main {

    public static void main(String[] args) {

        ConfigurationManager configManager = ConfigurationManager.getInstance();
        configManager.loadConfigurations("config.properties");

        String storageType = configManager.getConfiguration("defaultStorageType");
        CredentialStorage storage;

        switch (storageType.toUpperCase()) {
            case "DATABASE":
                storage = new DatabaseStorage();
                break;
            case "CLOUD":
                storage = new CloudStorage();
                break;
            case "FILE":
            default:
                storage = new LocalFileStorage();
                break;
        }

        CredentialFactory instance = CredentialFactory.getInstance(storage);
        System.out.println("Using Storage Type: " + storageType);

        Credential passwordStandard = instance.createCredential(CredentialType.PASSWORD, new SecurityCriteria.Builder().build());
        Credential passwordEnhanced = instance.createCredential(CredentialType.PASSWORD, new SecurityCriteria.Builder().algorithm("enhanced").build());
        Credential API = instance.createCredential(CredentialType.API_KEY, null);
        Credential SecretKey = instance.createCredential(CredentialType.SECRET_KEY, null);

        System.out.println("Generated password: " + passwordStandard);
        System.out.println("Generated password: " + passwordEnhanced);
        System.out.println("Generated API: " + API);
        System.out.println("Generated SecretKey: " + SecretKey);

        displayCurrentConfigurations(configManager);
    }


    private static void displayCurrentConfigurations(ConfigurationManager configManager) {
        System.out.println("Current configurations:");

        Map<String, Object> configs = configManager.getConfigurations();
        for (Map.Entry<String, Object> entry : configs.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue() +
                    " (Type: " + (entry.getValue() != null ? entry.getValue().getClass().getSimpleName() : "null") + ")");
        }
    }

}