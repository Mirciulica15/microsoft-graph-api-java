package com.mirceatalu.config;

import com.mirceatalu.graph.Graph;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OAuthConfig {

    public OAuthConfig() {
        loadProperties();
    }
    private Properties oAuthProperties;

    public void initializeGraph() {
        try {
            Graph.initializeGraphForUserAuth(oAuthProperties,
                    challenge -> System.out.println(challenge.getMessage()));
        } catch (Exception e) {
            System.out.println("Error initializing Graph for user auth");
            System.out.println(e.getMessage());
        }
    }

    private void loadProperties() {
        this.oAuthProperties = new Properties();
        try(InputStream inputStream = OAuthConfig.class.getResourceAsStream("/graphapi/oAuth.properties")) {
            this.oAuthProperties.load(inputStream);
            System.out.println(oAuthProperties.getProperty("app.clientId"));
        } catch (IOException e) {
            System.out.println("Unable to read OAuth configuration. Make sure you have a properly formatted oAuth.properties file. See README for details.");
        }
    }

}
