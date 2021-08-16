package org.sergei.metadata.app.dao;

import org.sergei.metadata.app.dto.FormMetadata;
import reactor.core.publisher.Mono;

public interface MetadataDao {

    Mono<FormMetadata> getFormMetadata(String formName, String lang);

}
