/*
 * Copyright 2020 Martin Winandy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.tinylog.core.formats;

import java.util.Locale;

/**
 * Builder for creating {@link ValueFormat ValueFormats}.
 *
 * <p>
 *     New value format builders can be provided as {@link java.util.ServiceLoader service} in
 *     {@code META-INF/services}.
 * </p>
 */
public interface ValueFormatBuilder {

	/**
	 * Checks if this value format is available on the current environment.
	 *
	 * @return {@code true} if available, {@code false} if not
	 */
	boolean isCompatible();

	/**
	 * Creates a new instance of the value format.
	 *
	 * @param locale Locale for language or country depending format outputs
	 * @return New instance of the value format
	 */
	ValueFormat create(Locale locale);

}
