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
package io.github.sergeivisotsky.metadata.engine;

/**
 * Test utility class.
 *
 * @author Sergejs Visockis
 */
public final class TestUtils {

    private TestUtils() {
        // noop
    }

    /**
     * Creates a {@link String} with parameters substituted. Similar to {@link String#format(String, Object...)}.
     *
     * @param str    string to format.
     * @param params parameters vararg to set.
     * @return properly formatted {@link String}.
     */
    public static String format(String str, Object... params) {
        return String.format(replaceBracketsWithFormatter(str), params);
    }

    /**
     * Replaces brackets "{}" sign with String formatter "%s".
     *
     * @param str string to replace in.
     * @return {@link String} where all replacements made.
     */
    public static String replaceBracketsWithFormatter(String str) {
        return str.replace("{}", "%s");
    }
}
