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

import java.util.ArrayList;
import java.util.List;

import io.github.sergeivisotsky.metadata.engine.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.LayoutMetadataDao;
import io.github.sergeivisotsky.metadata.engine.domain.ComboBox;
import io.github.sergeivisotsky.metadata.engine.domain.ComboBoxContent;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link ComboBoxMetadataDaoImpl}.
 *
 * @author Sergei Visotsky
 */
class ComboBoxMetadataDaoImplTest extends AbstractMetadataDao {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private MetadataMapper<ComboBox> comboBoxMapper;

    @Mock
    private LayoutMetadataDao layoutMetadataDao;

    @InjectMocks
    private ComboBoxMetadataDaoImpl dao;

    private static List<ComboBox> comboBoxes;

    @BeforeAll
    static void beforeAll() {
        comboBoxes = new ArrayList<>();

        ComboBox comboBoxOne = new ComboBox();
        comboBoxOne.setId(1L);
        comboBoxOne.setComboContent(List.of(new ComboBoxContent("firstKey", "firstValue", 1L)));

        comboBoxes.add(comboBoxOne);

        ComboBox comboBoxTwo = new ComboBox();
        comboBoxTwo.setId(1L);
        comboBoxTwo.setComboContent(List.of(new ComboBoxContent("secondKey", "SecondValue", 1L)));

        comboBoxes.add(comboBoxTwo);

        ComboBox comboBoxThree = new ComboBox();
        comboBoxThree.setId(1L);
        comboBoxThree.setComboContent(List.of(new ComboBoxContent("thirdKey", "thirdValue", 1L)));

        comboBoxes.add(comboBoxThree);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetComboBoxesByFormMetadataId() {
        //given
        when(comboBoxMapper.getSql()).thenReturn("SELECT * FROM some_table WHERE id = 1");
        when(jdbcTemplate.query(anyString(), anyMap(), any(RowMapper.class))).thenReturn(comboBoxes);

        //when
        List<ComboBox> combos = dao.getComboBoxesByFormMetadataId(1L);

        //then
        assertEquals(1, combos.size());
        assertNotEquals(0, combos.get(0).getComboContent().size());
        verify(comboBoxMapper).getSql();
        verify(jdbcTemplate).query(anyString(), anyMap(), any(RowMapper.class));
    }

    @Test
    void shouldNormalizeCombos() {
        //when
        List<ComboBox> resultCombos = dao.normalizeCombos(comboBoxes);

        //then
        assertEquals(1, resultCombos.size());
        assertEquals(3, resultCombos.get(0).getComboContent().size());
    }
}
