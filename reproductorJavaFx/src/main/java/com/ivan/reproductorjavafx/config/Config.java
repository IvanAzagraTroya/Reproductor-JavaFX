package com.ivan.reproductorjavafx.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    public static Properties readProperties() {
        var properties = new Properties();
        try{
            properties.load(
                    new FileInputStream(System.getProperty("user.dir")+File.separator+
                            "src"+File.separator+
                            "main"+ File.separator+
                            "resources"+File.separator+
                            "properties"+File.separator+
                            File.separator+"config.properties")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
