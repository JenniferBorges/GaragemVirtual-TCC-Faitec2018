package com.academico.garagem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {

    private final HandlerCollection handlers = new ContextHandlerCollection();
    private static final int HTTP_PORT = 8686;

    private void execute() throws Exception {

        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "OFF");

        System.out.println("Preparando para iniciar Garagem Virtual...");

        // Prepara servidor
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(512, 8);
        queuedThreadPool.setName("thread_Main_server");

        Server server = new Server(queuedThreadPool);
        Configuration.ClassList classlist = Configuration.ClassList
                .setServerDefault(server);
        classlist.addBefore(
                "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");

        try {
            upServerNormalMode(server);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void upServerNormalMode(Server server) throws Exception {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(HTTP_PORT);

        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());
        server.setConnectors(new Connector[]{connector});

        // Configura contexto da WebApp
        WebAppContext webappContext = configWar("/virtualgarage", getResourceAsFile("war/GaragemWeb.war"));
        webappContext.setAttribute(
                "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$");

        //configurando mais de um contexto no mesmo servidor
        handlers.addHandler(new DefaultHandler());
        handlers.addHandler(webappContext);

        //configurando compressao Gzip
        GzipHandler gzipHandler = new GzipHandler();
        gzipHandler.setIncludedMimeTypes("text/html", "text/plain", "text/css", "application/javascript", "text/javascript", "application/json");
        gzipHandler.setHandler(handlers);

        server.setHandler(gzipHandler);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            System.out.println("Problema ao iniciar servidor.");
            e.printStackTrace();
        }

    }

    /**
     * Configura o arquivo war de uma aplicação web no contexto espeficiado
     *
     * @param context Contexto da aplicação
     * @param warFile Arquivo .war da aplição
     * @return webappContext Objeto da Aplicação configurada
     */
    private WebAppContext configWar(String context, File warFile) {
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath(context);
        webapp.setAllowNullPathInfo(true);

        if (!warFile.exists()) {
            throw new RuntimeException("Unable to find WAR File: " + warFile.getAbsolutePath());
        }
        webapp.setWar(warFile.getAbsolutePath());
        webapp.setDescriptor("WEB-INF/web.xml");

        return webapp;
    }

    public static void main(String[] args) throws Exception {
        new Main().execute();
    }

    public static File getResourceAsFile(String resourcePath) {
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
            if (in == null) {
                return null;
            }

            File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            System.out.println("TEMP FILE: " + tempFile);
            tempFile.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                //copy stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
