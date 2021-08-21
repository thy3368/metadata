package org.sergei.metadata.app.dao.impl;

import java.util.function.BiFunction;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.sergei.metadata.app.dao.MetadataDao;
import org.sergei.metadata.app.dto.Area;
import org.sergei.metadata.app.dto.FormMetadata;
import org.sergei.metadata.app.dto.Language;
import org.sergei.metadata.app.dto.Layout;
import org.sergei.metadata.app.dto.ViewField;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MetadataDaoImpl implements MetadataDao {

    private static final String QUERY_FORM_METADATA = "SELECT fm.form_name,\n" +
            "       fm.cardinality,\n" +
            "       fm.language,\n" +
            "       fm.offset,\n" +
            "       fm.padding,\n" +
            "       fm.font,\n" +
            "       fm.font_size,\n" +
            "       fm.description,\n" +
            "       vf.enabled_by_default,\n" +
            "       vf.ui_control\n" +
            "FROM form_metadata fm\n" +
            "         LEFT JOIN view_field vf on fm.id = vf.form_metadata_id\n" +
            "WHERE fm.form_name = :formName\n" +
            "  AND fm.language = :lang";

    private static final String QUERY_LAYOUT_METADATA = "SELECT l.area,\n" +
            "       l.weight,\n" +
            "       l.height,\n" +
            "       l.font,\n" +
            "       l.font_size\n" +
            "FROM layout l\n" +
            "WHERE l.form_name = :formName";

    private final DatabaseClient client;

    public MetadataDaoImpl(DatabaseClient client) {
        this.client = client;
    }

    @Override
    public Mono<FormMetadata> getFormMetadata(String formName, String lang) {
        return getLayoutMetadata(formName)
                .collectList()
                .flatMap(layout -> client.sql(QUERY_FORM_METADATA)
                        .bind("formName", formName)
                        .bind("lang", lang)
                        .map((row, rowMetadata) -> FormMetadata.builder()
                                .formName(row.get("form_name", String.class))
                                .cardinality(row.get("cardinality", String.class))
                                .lang(Language.valueOf(row.get("language", String.class)))
                                .offset(row.get("offset", Integer.class))
                                .padding(row.get("padding", Integer.class))
                                .font(row.get("font", String.class))
                                .fontSize(row.get("font_size", Integer.class))
                                .description(row.get("description", String.class))
                                .viewField(ViewField.builder()
                                        .enabledByDefault(row.get("enabled_by_default", Integer.class))
                                        .uiControl(row.get("ui_control", String.class))
                                        .build())
                                .layouts(layout)
                                .build())
                        .one());
    }

    private Flux<Layout> getLayoutMetadata(String formName) {
        return client.sql(QUERY_LAYOUT_METADATA)
                .bind("formName", formName)
                .map((BiFunction<Row, RowMetadata, Object>) (row, rowMetadata) ->
                        Layout.builder()
                                .font(row.get("font", String.class))
                                .fontSize(row.get("font_size", Integer.class))
                                .area(Area.valueOf(row.get("area", String.class)))
                                .weight(row.get("weight", Integer.class))
                                .height(row.get("height", Integer.class))
                                .build())
                .all()
                .cast(Layout.class);
    }
}
