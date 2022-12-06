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
import java.util.Map;
import java.util.stream.Collectors;

import io.github.sergeivisotsky.metadata.engine.dao.AbstractMetadataDao;
import io.github.sergeivisotsky.metadata.engine.dao.ComboBoxMetadataDao;
import io.github.sergeivisotsky.metadata.engine.domain.ComboBox;
import io.github.sergeivisotsky.metadata.engine.domain.ComboBoxContent;
import io.github.sergeivisotsky.metadata.engine.exception.MetadataStorageException;
import io.github.sergeivisotsky.metadata.engine.mapper.MetadataMapper;
import org.springframework.dao.DataAccessException;

/**
 * @author Sergei Visotsky
 */
public class ComboBoxMetadataDaoImpl extends AbstractMetadataDao implements ComboBoxMetadataDao {

    private final MetadataMapper<ComboBox> comboBoxMapper;

    public ComboBoxMetadataDaoImpl(MetadataMapper<ComboBox> comboBoxMapper) {
        this.comboBoxMapper = comboBoxMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ComboBox> getComboBoxesByFormMetadataId(Long id) {
        try {
            Map<String, Object> params = Map.of("viewMetadataId", id);
            List<ComboBox> combos = jdbcTemplate.query(comboBoxMapper.getSql(), params, (rs, index) -> {
                ComboBox comboBox = comboBoxMapper.map(rs);
                List<ComboBoxContent> comboContent = new ArrayList<>();
                comboContent.add(new ComboBoxContent(
                        rs.getString("content_key"),
                        rs.getString("content_value"),
                        rs.getLong("id")));
                comboBox.setComboContent(comboContent);
                return comboBox;
            });
            return normalizeCombos(combos);
        } catch (DataAccessException e) {
            throw new MetadataStorageException(e, "Unable to get a combo box metadata by the " +
                    "following parameters: id={}", id);
        }
    }

    protected List<ComboBox> normalizeCombos(List<ComboBox> combos) {
        List<ComboBox> resultingCombos = new ArrayList<>();
        List<ComboBoxContent> resultingComboContent = combos.stream()
                .flatMap(combo -> combo.getComboContent()
                        .stream()
                        .filter(comboContent -> combo.getId().equals(comboContent.getComboId())))
                .collect(Collectors.toList());
        for (int i = 0; i < combos.size() - 1; i++) {
            if (combos.get(i).getId().equals(combos.get(i++).getId())) {
                ComboBox resultingCombo = combos.get(i);
                resultingCombo.setComboContent(resultingComboContent
                        .stream()
                        .filter(cont -> cont.getComboId().equals(resultingCombo.getId()))
                        .collect(Collectors.toList()));
                resultingCombos.add(resultingCombo);
            }
        }
        return resultingCombos;
    }
}
