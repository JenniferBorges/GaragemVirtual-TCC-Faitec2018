package com.academico.garagem.model.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {

    private static final String HOME_DIR = System.getProperty("user.home");
    private static final String APP_CONF_DIR = HOME_DIR + "/.garagem";
    private static final String LOGS_DIR = APP_CONF_DIR + "/logs";

    private static final String UPLOADS_PATH = HOME_DIR + "/uploads";
    private static final String PICTURES_PATH = UPLOADS_PATH + "/pictures";

    private String projectVersion;
    private String apiVersion;

    private static Config instance;

    private Config() {
        initDirs();
        loadProjectVersion();
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }

        return instance;
    }

    public String getLogsDir() {
        return LOGS_DIR;
    }

    public String getPicturesPath() {
        return PICTURES_PATH + "/";
    }

    private void initDirs() {
        createDirectoryIfNotExists(UPLOADS_PATH);
        createDirectoryIfNotExists(PICTURES_PATH);
        createDirectoryIfNotExists(APP_CONF_DIR);
        createDirectoryIfNotExists(LOGS_DIR);
    }

    public static void createDirectoryIfNotExists(String directoryPath) {
        try {
            Path path = Paths.get(directoryPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Properties loadProperties(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    public String getAppConfDir() {
        return APP_CONF_DIR;
    }

    private void loadProjectVersion() {
        // Tenta carregar versão do projeto web vindo do arquivo MAVEN
        try (InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = loadProperties(resourceStream);
            apiVersion = loadMavenProperties(properties).get("api.version");
            projectVersion = properties.getProperty("application.version");
        } catch (IOException ex) {
            projectVersion = "?.?.?";
        }

    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    private Map<String, String> loadMavenProperties(Properties properties) {
        String strAppProps = (String) properties.get("application.apiversion");
        int lastBrace = strAppProps.indexOf("}");
        strAppProps = strAppProps.substring(1, lastBrace);

        Map<String, String> appProperties = new HashMap<>();
        String[] appProps = strAppProps.split("[\\s,]+");
        for (String appProp : appProps) {
            String[] keyValue = appProp.split("=");
            if (keyValue != null && keyValue.length > 1) {
                appProperties.put(keyValue[0], keyValue[1]);
            }
        }
        return appProperties;
    }

}
