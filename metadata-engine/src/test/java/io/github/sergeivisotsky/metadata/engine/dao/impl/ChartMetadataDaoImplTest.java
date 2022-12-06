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

import io.github.sergeivisotsky.metadata.engine.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.engine.domain.chart.Category;
import io.github.sergeivisotsky.metadata.engine.domain.chart.ChartMetadata;
import io.github.sergeivisotsky.metadata.engine.domain.chart.ChartType;
import io.github.sergeivisotsky.metadata.engine.exception.MetadataStorageException;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static io.github.sergeivisotsky.metadata.engine.TestUtils.format;
import static io.github.sergeivisotsky.metadata.engine.dao.impl.ChartMetadataDaoImpl.GET_CHART_CATEGORY_METADATA_EXCEPTION_MSG;
import static io.github.sergeivisotsky.metadata.engine.dao.impl.ChartMetadataDaoImpl.GET_CHART_METADATA_EXCEPTION_MSG;
import static io.github.sergeivisotsky.metadata.engine.dao.impl.ChartMetadataDaoImpl.QUERY_CHART_CATEGORY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link ChartMetadataDaoImpl}.
 *
 * @author Sergei Visotsky
 */
class ChartMetadataDaoImplTest extends AbstractMetadataDao {

    private static final Long CHART_ID = 5L;
    private static final String CHART_NAME = "someChartName";
    private static final Integer GAP = 1;
    private static final Double INACTIVE_OPACITY = 3.5;
    private static final Double SPACING = 5.7;
    private static final String FIRST_CAT_KEY = "firstCatKey";
    private static final String FIRST_CAT_VALUE = "firstCategoryValue";
    private static final String SECOND_CAT_KEY = "secondCatKey";
    private static final String SECOND_CAT_VALUE = "secondCategoryValue";

    private static final String SQL = "SELECT c.id,\n" +
            "       c.name,\n" +
            "       c.gap,\n" +
            "       c.spacing,\n" +
            "       c.type,\n" +
            "       c.inactive_opacity\n" +
            "FROM chart c\n" +
            "WHERE name = :chartName";

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private MetadataMapper<ChartMetadata> chartMetadataMapper;

    @InjectMocks
    private ChartMetadataDaoImpl metadataDao;

    private ChartMetadata chartMetadata;

    private static final Category FIRST_CATEGORY = new Category(FIRST_CAT_KEY, FIRST_CAT_VALUE);
    private static final Category SECOND_CATEGORY = new Category(SECOND_CAT_KEY, SECOND_CAT_VALUE);
    private static final List<Category> CATEGORIES = List.of(FIRST_CATEGORY, SECOND_CATEGORY);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        chartMetadata = new ChartMetadata();
        chartMetadata.setName(CHART_NAME);
        chartMetadata.setType(ChartType.LINE);
        chartMetadata.setGap(GAP);
        chartMetadata.setInactiveOpacity(INACTIVE_OPACITY);
        chartMetadata.setSpacing(SPACING);

        chartMetadata.setCategories(CATEGORIES);
    }

    @Test
    void shouldGetChartMetadata() {
        //given
        when(chartMetadataMapper.getSql()).thenReturn(SQL);
        when(jdbcTemplate.queryForObject(eq(SQL), anyMap(), any(RowMapper.class))).thenReturn(chartMetadata);

        //when
        ChartMetadata actualMetadata = metadataDao.getChartMetadata(CHART_NAME);

        //then
        verify(chartMetadataMapper).getSql();
        assertEquals(CHART_NAME, actualMetadata.getName());
        assertEquals(GAP, actualMetadata.getGap());
        assertEquals(INACTIVE_OPACITY, actualMetadata.getInactiveOpacity());
        assertEquals(SPACING, actualMetadata.getSpacing());

        List<Category> categories = actualMetadata.getCategories();
        Category firstCategory = categories.get(0);
        Category secondCategory = categories.get(1);
        assertEquals(2, categories.size());
        assertEquals(FIRST_CAT_KEY, firstCategory.getKey());
        assertEquals(FIRST_CAT_VALUE, firstCategory.getValue());
        assertEquals(SECOND_CAT_KEY, secondCategory.getKey());
        assertEquals(SECOND_CAT_VALUE, secondCategory.getValue());
    }

    @Test
    void shouldThrowExceptionWhileGettingChartMetadata() {
        //given
        when(chartMetadataMapper.getSql()).thenReturn(SQL);
        when(jdbcTemplate.queryForObject(eq(SQL), anyMap(), any(RowMapper.class)))
                .thenThrow(RuntimeException.class);

        //when
        MetadataStorageException exception = assertThrows(MetadataStorageException.class,
                () -> metadataDao.getChartMetadata(CHART_NAME));

        //then
        String expectedMsg = format(GET_CHART_METADATA_EXCEPTION_MSG, CHART_NAME);
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    void shouldGetCategoryMetadata() {
        //given
        when(jdbcTemplate.query(eq(QUERY_CHART_CATEGORY), anyMap(), any(RowMapper.class)))
                .thenReturn(CATEGORIES);

        //when
        List<Category> categories = metadataDao.getChartCategoryMetadata(CHART_ID);

        //then
        assertEquals(2, categories.size());
        Category firstCategory = categories.get(0);
        Category secondCategory = categories.get(1);
        assertEquals(2, categories.size());
        assertEquals(FIRST_CAT_KEY, firstCategory.getKey());
        assertEquals(FIRST_CAT_VALUE, firstCategory.getValue());
        assertEquals(SECOND_CAT_KEY, secondCategory.getKey());
        assertEquals(SECOND_CAT_VALUE, secondCategory.getValue());
        verify(jdbcTemplate).query(eq(QUERY_CHART_CATEGORY), anyMap(), any(RowMapper.class));
    }

    @Test
    void shouldThrowExceptionWhileGettingCategoryMetadata() {
        //given
        when(jdbcTemplate.query(eq(QUERY_CHART_CATEGORY), anyMap(), any(RowMapper.class)))
                .thenThrow(RuntimeException.class);

        //when
        MetadataStorageException exception = assertThrows(MetadataStorageException.class,
                () -> metadataDao.getChartCategoryMetadata(CHART_ID));

        //then
        String expectedMsg = format(GET_CHART_CATEGORY_METADATA_EXCEPTION_MSG, CHART_ID);
        assertEquals(expectedMsg, exception.getMessage());
    }
}