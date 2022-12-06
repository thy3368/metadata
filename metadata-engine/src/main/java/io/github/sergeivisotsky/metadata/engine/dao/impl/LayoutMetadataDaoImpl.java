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

package io.github.sergeivisotsky.metadata.engine.dao.impl;

import java.util.List;
import java.util.Map;

import io.github.sergeivisotsky.metadata.engine.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.LayoutMetadataDao;
import io.github.sergeivisotsky.metadata.engine.domain.Layout;
import io.github.sergeivisotsky.metadata.engine.exception.MetadataStorageException;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;

/**
 * @author Sergei Visotsky
 */
public class LayoutMetadataDaoImpl extends AbstractMetadataDao implements LayoutMetadataDao {

    private final MetadataMapper<Layout> layoutMapper;

    public LayoutMetadataDaoImpl(MetadataMapper<Layout> layoutMapper) {
        this.layoutMapper = layoutMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Layout> getLayoutMetadata(String viewName) {
        try {
            Map<String, Object> params = Map.of("viewName", viewName);
            return executeQuery(params, layoutMapper);
        } catch (Exception e) {
            throw new MetadataStorageException(e, "Unable to get a layout metadata with the " +
                    "following parameters: viewName={}", viewName);
        }
    }
}
