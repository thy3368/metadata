/*
 *
 *    Copyright 2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package org.sergei.metadata.selector.config;

import java.sql.ResultSet;

import org.sergei.metadata.selector.MetadataMapper;
import org.sergei.metadata.selector.dao.MetadataDao;
import org.sergei.metadata.selector.dao.impl.CacheableMetadataDao;
import org.sergei.metadata.selector.dao.impl.MetadataDaoImpl;
import org.sergei.metadata.selector.dto.FormMetadata;
import org.sergei.metadata.selector.dto.Layout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author Sergei Visotsky
 */
@Configuration
@ComponentScan(basePackages = {
        "org.sergei.metadata.*"
})
public class MetadataSelectorConfig {

    @Bean
    public MetadataDao metadataDao(NamedParameterJdbcTemplate jdbcTemplate,
                                   MetadataMapper<ResultSet, FormMetadata> formMetadataMapper,
                                   MetadataMapper<ResultSet, Layout> layoutMapper) {
        return new CacheableMetadataDao(new MetadataDaoImpl(jdbcTemplate, formMetadataMapper, layoutMapper));
    }
}
