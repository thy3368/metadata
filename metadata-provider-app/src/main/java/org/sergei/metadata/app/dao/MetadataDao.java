package org.sergei.metadata.app.dao;

import org.sergei.metadata.app.dto.FormMetadata;

public interface MetadataDao {

    FormMetadata getFormMetadata(String formName, String lang);

}
