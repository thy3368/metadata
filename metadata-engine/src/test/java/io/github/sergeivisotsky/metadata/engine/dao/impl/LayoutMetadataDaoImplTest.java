/*
 * Copyright 2022 the original author or authors.
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

import java.util.ArrayList;
import java.util.List;

import io.github.sergeivisotsky.metadata.engine.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.engine.domain.Area;
import io.github.sergeivisotsky.metadata.engine.domain.Layout;
import io.github.sergeivisotsky.metadata.engine.exception.MetadataStorageException;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static io.github.sergeivisotsky.metadata.engine.TestUtils.format;
import static io.github.sergeivisotsky.metadata.engine.dao.impl.LayoutMetadataDaoImpl.EXCEPTION_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * A unit test for {@link LayoutMetadataDaoImpl}.
 *
 * @author Sergejs Visockis
 */
class LayoutMetadataDaoImplTest extends AbstractMetadataDao {

    private static final String TEST_SQL = "SELECT * FROM some_table WHERE id = 1";
    private static final String TEST_VIEW_NAME = "some_view_name";

    private static final String FIRST_LAYOUT_FONT = "Verdana";
    private static final Integer FIRST_LAYOUT_WEIGHT = 30;
    private static final Integer FIRST_LAYOUT_HEIGHT = 500;
    private static final Integer FIRST_LAYOUT_FONT_SIZE = 25;

    private static final String SECOND_LAYOUT_FONT = "Times New Roman";
    private static final Integer SECOND_LAYOUT_WEIGHT = 12;
    private static final Integer SECOND_LAYOUT_HEIGHT = 200;
    private static final Integer SECOND_LAYOUT_FONT_SIZE = 12;

    private static final String THIRD_LAYOUT_FONT = "Calibri";
    private static final Integer THIRD_LAYOUT_WEIGHT = 90;
    private static final Integer THIRD_LAYOUT_HEIGHT = 1000;
    private static final Integer THIRD_LAYOUT_FONT_SIZE = 16;

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private MetadataMapper<Layout> layoutMapper;

    @InjectMocks
    private LayoutMetadataDaoImpl dao;

    private static List<Layout> layouts;

    @BeforeAll
    static void beforeAll() {
        layouts = new ArrayList<>();

        Layout layoutOne = new Layout();
        layoutOne.setArea(Area.TOP);
        layoutOne.setFont(FIRST_LAYOUT_FONT);
        layoutOne.setWeight(FIRST_LAYOUT_WEIGHT);
        layoutOne.setHeight(FIRST_LAYOUT_HEIGHT);
        layoutOne.setFontSize(FIRST_LAYOUT_FONT_SIZE);
        layouts.add(layoutOne);

        Layout layoutTwo = new Layout();
        layoutTwo.setArea(Area.LEFT);
        layoutTwo.setFont(SECOND_LAYOUT_FONT);
        layoutTwo.setWeight(SECOND_LAYOUT_WEIGHT);
        layoutTwo.setHeight(SECOND_LAYOUT_HEIGHT);
        layoutTwo.setFontSize(SECOND_LAYOUT_FONT_SIZE);
        layouts.add(layoutTwo);

        Layout layoutThree = new Layout();
        layoutThree.setArea(Area.RIGHT);
        layoutThree.setFont(THIRD_LAYOUT_FONT);
        layoutThree.setWeight(THIRD_LAYOUT_WEIGHT);
        layoutThree.setHeight(THIRD_LAYOUT_HEIGHT);
        layoutThree.setFontSize(THIRD_LAYOUT_FONT_SIZE);
        layouts.add(layoutThree);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetLayoutMetadata() {
        //given
        when(layoutMapper.getSql()).thenReturn(TEST_SQL);
        when(jdbcTemplate.query(anyString(), anyMap(), any(RowMapper.class))).thenReturn(layouts);

        //when
        List<Layout> layouts = dao.getLayoutMetadata(TEST_VIEW_NAME);

        Layout firstLayout = layouts.get(0);
        Layout secondLayout = layouts.get(1);
        Layout thirdLayout = layouts.get(2);

        //then
        assertEquals(3, layouts.size());
        assertEquals(Area.TOP, firstLayout.getArea());
        assertEquals(FIRST_LAYOUT_FONT, firstLayout.getFont());
        assertEquals(FIRST_LAYOUT_WEIGHT, firstLayout.getWeight());
        assertEquals(FIRST_LAYOUT_HEIGHT, firstLayout.getHeight());
        assertEquals(FIRST_LAYOUT_FONT_SIZE, firstLayout.getFontSize());

        assertEquals(Area.LEFT, secondLayout.getArea());
        assertEquals(SECOND_LAYOUT_FONT, secondLayout.getFont());
        assertEquals(SECOND_LAYOUT_WEIGHT, secondLayout.getWeight());
        assertEquals(SECOND_LAYOUT_HEIGHT, secondLayout.getHeight());
        assertEquals(SECOND_LAYOUT_FONT_SIZE, secondLayout.getFontSize());

        assertEquals(Area.RIGHT, thirdLayout.getArea());
        assertEquals(THIRD_LAYOUT_FONT, thirdLayout.getFont());
        assertEquals(THIRD_LAYOUT_WEIGHT, thirdLayout.getWeight());
        assertEquals(THIRD_LAYOUT_HEIGHT, thirdLayout.getHeight());
        assertEquals(THIRD_LAYOUT_FONT_SIZE, thirdLayout.getFontSize());
    }

    @Test
    void shouldThrowExceptionWhileGettingLayout() {
        //given
        when(layoutMapper.getSql()).thenReturn(TEST_SQL);
        when(jdbcTemplate.query(anyString(), anyMap(), any(RowMapper.class)))
                .thenThrow(RuntimeException.class);

        //when
        MetadataStorageException exception = assertThrows(MetadataStorageException.class,
                () -> dao.getLayoutMetadata(TEST_VIEW_NAME));

        //then
        assertEquals(format(EXCEPTION_MESSAGE, TEST_VIEW_NAME), exception.getMessage());
    }
}
