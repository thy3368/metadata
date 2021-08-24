/*
 *
 *    Copyright 2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package org.sergei.metadata.selector;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An interface to be implemented by mappers from {@link ResultSet} to DTO.
 *
 * @param <F> map from.
 * @param <T> map to.
 * @author Sergei Visotsky
 */
public interface MetadataMapper<F, T> extends Function<F, T> {

    /**
     * Get an SQL containing a new fields added during customization.
     *
     * @return SQL to execute.
     */
    String getSql();

    /**
     * Map a collection of elements.
     *
     * @param fromList collection to map.
     * @return returning collection with mapped elements.
     */
    default List<T> applyList(List<F> fromList) {
        return fromList.stream().map(this).collect(Collectors.toList());
    }

}
