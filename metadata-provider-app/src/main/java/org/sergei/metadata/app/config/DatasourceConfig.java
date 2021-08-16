package org.sergei.metadata.app.config;

import java.util.List;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.sergei.metadata.app.converter.AreaConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

@Configuration
public class DatasourceConfig extends AbstractR2dbcConfiguration {

    private final String host;
    private final Integer port;
    private final String database;
    private final String username;
    private final String password;

    public DatasourceConfig(@Value("${datasource.host}") String host,
                            @Value("${datasource.port}") Integer port,
                            @Value("${datasource.database}") String database,
                            @Value("${datasource.username}") String username,
                            @Value("${datasource.password}") String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host(host)
                .port(port)
                .database(database)
                .username(username)
                .password(password)
                .build());
    }

    @Bean
    public ReactiveTransactionManager transactionManager() {
        return new R2dbcTransactionManager(connectionFactory());
    }

    @Override
    protected List<Object> getCustomConverters() {
        return List.of(new AreaConverter());
    }
}
