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

package io.github.sergeivisotsky.metadata.selector.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import io.github.sergeivisotsky.metadata.selector.MetadataMapper;
import io.github.sergeivisotsky.metadata.selector.dao.MetadataDao;
import io.github.sergeivisotsky.metadata.selector.dto.FormMetadata;
import io.github.sergeivisotsky.metadata.selector.dto.Layout;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author Sergei Visotsky
 */
public class MetadataDaoImpl implements MetadataDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final MetadataMapper<ResultSet, FormMetadata> formMetadataMapper;
    private final MetadataMapper<ResultSet, Layout> layoutMapper;

    public MetadataDaoImpl(NamedParameterJdbcTemplate jdbcTemplate,
                           MetadataMapper<ResultSet, FormMetadata> formMetadataMapper,
                           MetadataMapper<ResultSet, Layout> layoutMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.formMetadataMapper = formMetadataMapper;
        this.layoutMapper = layoutMapper;
    }

    @Override
    public FormMetadata getFormMetadata(String formName, String lang) {
        Map<String, Object> params = Map.of(
                "formName", formName,
                "lang", lang
        );
        return jdbcTemplate.queryForObject(formMetadataMapper.getSql(), params,
                (rs, index) -> {
                    FormMetadata metadata = formMetadataMapper.apply(rs);
                    metadata.setLayouts(getLayoutMetadata(formName));
                    return metadata;
                });
    }

    private List<Layout> getLayoutMetadata(String formName) {
        Map<String, String> params = Map.of("formName", formName);
        return jdbcTemplate.query(layoutMapper.getSql(), params, (rs, index) -> layoutMapper.apply(rs));
    }
}
