package com.mirceatalu.graph;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.DeviceCodeCredential;
import com.azure.identity.DeviceCodeCredentialBuilder;
import com.azure.identity.DeviceCodeInfo;
import com.microsoft.graph.models.User;
import com.microsoft.graph.serviceclient.GraphServiceClient;

import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;

public final class Graph {

    private static Properties properties;
    private static DeviceCodeCredential deviceCodeCredential;
    private static GraphServiceClient userClient;

    private Graph() {
        // Private constructor to prevent instantiation
    }

    public static void initializeGraphForUserAuth(Properties oAuthProperties, Consumer<DeviceCodeInfo> challenge) {
        Optional.ofNullable(oAuthProperties)
                .orElseThrow(() -> new IllegalArgumentException("Properties cannot be null"));

        properties = oAuthProperties;

        final String clientId = properties.getProperty("app.clientId");
        final String tenantId = properties.getProperty("app.tenantId");
        final String[] graphUserScopes = properties.getProperty("app.graphUserScopes").split(",");

        deviceCodeCredential = new DeviceCodeCredentialBuilder()
                .clientId(clientId)
                .tenantId(tenantId)
                .challengeConsumer(challenge)
                .build();

        userClient = new GraphServiceClient(deviceCodeCredential, graphUserScopes);
    }

    public static String getUserToken() {
        Optional.ofNullable(deviceCodeCredential)
                .orElseThrow(() -> new IllegalStateException("DeviceCodeCredential is null"));

        final String[] graphUserScopes = properties.getProperty("app.graphUserScopes").split(",");
        final AccessToken token = deviceCodeCredential.getToken(new TokenRequestContext().addScopes(graphUserScopes)).block();

        return Objects.requireNonNull(token).getToken();
    }

    public static User getUser() {
        Optional.ofNullable(userClient)
                .orElseThrow(() -> new IllegalStateException("GraphServiceClient is null"));

        return userClient.me()
                .get();
    }

}
