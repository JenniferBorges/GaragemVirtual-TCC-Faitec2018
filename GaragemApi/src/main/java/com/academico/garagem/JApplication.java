package com.academico.garagem;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.apache.log4j.Logger;

@ApplicationPath("/")
public class JApplication extends Application {

    private final static Logger log = Logger.getLogger(JApplication.class);

    public JApplication() {
        log.info("Garagem Virtual - API. v1.0");
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        return classes;
    }

}
