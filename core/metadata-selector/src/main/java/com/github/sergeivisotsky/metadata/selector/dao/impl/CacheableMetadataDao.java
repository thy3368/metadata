/*
 * Copyright 2021 the original author or authors.
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

package com.github.sergeivisotsky.metadata.selector.dao.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.github.sergeivisotsky.metadata.selector.dao.MetadataDao;
import com.github.sergeivisotsky.metadata.selector.dto.FormMetadata;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.github.sergeivisotsky.metadata.selector.exception.DataAccessException;

/**
 * @author Sergei Visotsky
 */
public class CacheableMetadataDao implements MetadataDao {

    private final Cache<MetadataCacheKey, FormMetadata> cache;
    private final MetadataDao metadataDao;

    public CacheableMetadataDao(MetadataDao metadataDao) {
        this.metadataDao = metadataDao;
        cache = CacheBuilder.newBuilder()
                .initialCapacity(8000)
                .expireAfterAccess(8, TimeUnit.HOURS)
                .build();
    }

    @Override
    public FormMetadata getFormMetadata(String formName, String lang) {
        try {
            MetadataCacheKey cacheKey = new MetadataCacheKey(formName, lang);
            return cache.get(cacheKey, () -> metadataDao.getFormMetadata(formName, lang));
        } catch (ExecutionException e) {
            throw new DataAccessException(e, "Unable to get a metadata from cache for " +
                    "formName={} with lang={}", formName, lang);
        }
    }

    private static final class MetadataCacheKey {
        private final String formName;
        private final String lang;

        private MetadataCacheKey(String formName, String lang) {
            this.formName = formName;
            this.lang = lang;
        }

        public String getFormName() {
            return formName;
        }

        public String getLang() {
            return lang;
        }
    }
}