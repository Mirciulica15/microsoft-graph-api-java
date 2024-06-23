package com.mirceatalu;

import com.mirceatalu.config.OAuthConfig;
import com.mirceatalu.console.Console;

public class App {

    public static void main(String[] args) {
        OAuthConfig oAuthConfig = new OAuthConfig();
        oAuthConfig.initializeGraph();
        Console.displayOptions();
    }
}
