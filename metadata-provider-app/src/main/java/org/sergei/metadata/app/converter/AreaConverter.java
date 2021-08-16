package org.sergei.metadata.app.converter;

import io.r2dbc.spi.Row;
import org.sergei.metadata.app.dto.Area;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class AreaConverter implements Converter<Row, Area> {

    @Override
    public Area convert(Row row) {
        return row.get(0, Area.class);
    }
}
