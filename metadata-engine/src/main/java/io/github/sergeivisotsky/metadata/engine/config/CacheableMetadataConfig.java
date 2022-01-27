/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.sergeivisotsky.metadata.engine.config;

import io.github.sergeivisotsky.metadata.engine.config.properties.CacheConfigProperties;
import io.github.sergeivisotsky.metadata.engine.dao.FormMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.ViewMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.ViewQueryDao;
import io.github.sergeivisotsky.metadata.engine.dao.cache.CacheableFormMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.cache.CacheableViewMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.cache.CacheableViewQueryDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Metadata cacheable DAOs configuration.
 *
 * @author Sergei Visotsky
 */
@Configuration
public class CacheableMetadataConfig {

    @Bean
    @Primary
    public ViewMetadataDao cacheableViewMetadataDao(ViewMetadataDao viewMetadataDao,
                                                    CacheConfigProperties viewCacheProperties) {
        return new CacheableViewMetadataDao(viewMetadataDao, viewCacheProperties);
    }


    @Bean
    @Primary
    public FormMetadataDao cacheableFormMetadataDao(FormMetadataDao formMetadataDao,
                                                    CacheConfigProperties formCacheProperties) {
        return new CacheableFormMetadataDao(formMetadataDao, formCacheProperties);
    }

    @Bean
    @Primary
    public ViewQueryDao cacheableViewQueryDao(ViewQueryDao viewQueryDao,
                                              CacheConfigProperties queryCacheProperties) {
        return new CacheableViewQueryDao(viewQueryDao, queryCacheProperties);
    }
}
