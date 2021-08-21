package org.sergei.metadata.app.config;

import org.sergei.metadata.app.dao.MetadataDao;
import org.sergei.metadata.app.dao.impl.CacheableMetadataDao;
import org.sergei.metadata.app.dao.impl.MetadataDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
public class AppConfig {

    @Bean
    @Primary
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.failOnEmptyBeans(false);
        return builder;
    }

    @Bean
    public MetadataDao metadataDao(DatabaseClient client) {
        return new CacheableMetadataDao(new MetadataDaoImpl(client));
    }
}
