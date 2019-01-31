package com.academico.garagem.model.util;

import com.googlecode.flyway.core.Flyway;
import org.postgresql.ds.PGPoolingDataSource;

public class FlywayUtil {

    //Singleton
    public static void update() {
        PGPoolingDataSource dataSource = DataSourceUtil.getInstance().getDataSource();

        // Inicialição do FlyWay
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);

        // executa Migração;
        flyway.migrate();
    }

}
