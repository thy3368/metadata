package org.sergei.metadata.app.rest;

import org.sergei.metadata.app.dao.MetadataDao;
import org.sergei.metadata.app.dto.FormMetadata;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class MetadataRestController {

    private final MetadataDao metadataDao;

    public MetadataRestController(MetadataDao metadataDao) {
        this.metadataDao = metadataDao;
    }

    @GetMapping("/getFormMetadata/{formName}/{lang}")
    public Mono<FormMetadata> getFormMetadata(@PathVariable("formName") String formName,
                                              @PathVariable("lang") String lang) {
        return metadataDao.getFormMetadata(formName, lang);
    }
}
