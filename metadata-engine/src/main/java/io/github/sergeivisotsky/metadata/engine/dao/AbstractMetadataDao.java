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

package io.github.sergeivisotsky.metadata.engine.dao;

import java.util.List;
import java.util.Map;

import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * An abstract class to hold a common methods and beans for all metadata provider DAOs.
 *
 * @author Sergei Visotsky
 */
public abstract class AbstractMetadataDao {

    protected NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Execute an SQL query and extract a {@link java.sql.ResultSet} into a collection of elements.
     *
     * @param params SQL query parameter map.
     * @param mapper {@link java.sql.ResultSet} extraction mapper.
     * @param <T>    type of returned collection.
     * @return collection of elements.
     */
    protected <T> List<T> executeQuery(Map<String, Object> params, MetadataMapper<T> mapper) {
        return jdbcTemplate.query(mapper.getSql(), params, (rs, index) -> mapper.map(rs));
    }

    /**
     * Execute an SQL query for a single element as a return type.
     * Should be used when one and only one element should be returned.
     *
     * @param params SQL query parameter map.
     * @param mapper {@link java.sql.ResultSet} extraction mapper.
     * @param <T>    return type.
     * @return single element.
     */
    protected <T> T executeQueryForSingle(Map<String, Object> params, MetadataMapper<T> mapper) {
        return jdbcTemplate.queryForObject(mapper.getSql(), params, (rs, rowNum) -> mapper.map(rs));
    }

    @Autowired
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
