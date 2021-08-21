package org.sergei.metadata.app.dao.impl;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.sergei.metadata.app.dao.MetadataDao;
import org.sergei.metadata.app.dto.FormMetadata;
import reactor.core.publisher.Mono;

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
    public Mono<FormMetadata> getFormMetadata(String formName, String lang) {
        MetadataCacheKey cacheKey = new MetadataCacheKey(formName, lang);
        return Mono.justOrEmpty(cache.getIfPresent(cacheKey))
                .switchIfEmpty(metadataDao.getFormMetadata(formName, lang)
                        .flatMap(metadata -> {
                            cache.put(cacheKey, metadata);
                            return Mono.just(metadata);
                        }));
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
