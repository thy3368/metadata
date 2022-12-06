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
import io.github.sergeivisotsky.metadata.engine.dao.ChartMetadataDao;
import io.github.sergeivisotsky.metadata.engine.domain.chart.Category;
import io.github.sergeivisotsky.metadata.engine.domain.chart.ChartMetadata;
import io.github.sergeivisotsky.metadata.engine.exception.MetadataStorageException;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;

/**
 * @author Sergei Visotsky
 */
public class ChartMetadataDaoImpl extends AbstractMetadataDao implements ChartMetadataDao {

    static final String GET_CHART_METADATA_EXCEPTION_MSG = "Exception occurred while getting chart metadata. chartName={}";
    static final String GET_CHART_CATEGORY_METADATA_EXCEPTION_MSG = "Exception occurred while getting chart category metadata. chartId={}";

    static final String QUERY_CHART_CATEGORY = "SELECT ct.key, ct.value FROM category ct WHERE chart_id = :chartId";

    private final MetadataMapper<ChartMetadata> chartMetadataMapper;

    public ChartMetadataDaoImpl(MetadataMapper<ChartMetadata> chartMetadataMapper) {
        this.chartMetadataMapper = chartMetadataMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChartMetadata getChartMetadata(String chartName) {
        try {
            Map<String, Object> params = Map.of("chartName", chartName);
            return jdbcTemplate.queryForObject(chartMetadataMapper.getSql(), params,
                    (rs, index) -> {
                        ChartMetadata metadata = chartMetadataMapper.map(rs);
                        metadata.setCategories(getChartCategoryMetadata(rs.getLong("id")));
                        return metadata;
                    });
        } catch (Exception e) {
            throw new MetadataStorageException(e, GET_CHART_METADATA_EXCEPTION_MSG, chartName);
        }
    }

    List<Category> getChartCategoryMetadata(Long chartId) {
        try {
            Map<String, Object> params = Map.of("chartId", chartId);
            return jdbcTemplate.query(QUERY_CHART_CATEGORY, params, (rs, rowNum) ->
                    new Category(rs.getString("key"), rs.getString("value")));
        } catch (Exception e) {
            throw new MetadataStorageException(e, GET_CHART_CATEGORY_METADATA_EXCEPTION_MSG, chartId);
        }
    }
}
