package com.academico.garagem.model.util;

import org.postgresql.ds.PGPoolingDataSource;

public class DataSourceUtil {

    private static final DataSourceUtil INSTANCE = new DataSourceUtil();

    private final PGPoolingDataSource dataSource;

    private DataSourceUtil() {
        this.dataSource = new PGPoolingDataSource();
        this.dataSource.setDataSourceName("virtualgarage");
        this.dataSource.setServerName("localhost");
        this.dataSource.setDatabaseName("virtualgarage");
        this.dataSource.setUser("postgres");
        this.dataSource.setPassword("postgres");
        this.dataSource.setMaxConnections(20);
        this.dataSource.setInitialConnections(5);
    }

    public static DataSourceUtil getInstance() {
        return INSTANCE;
    }

    public PGPoolingDataSource getDataSource() {
        return dataSource;
    }

}
