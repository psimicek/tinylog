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

package org.tinylog.format;

import org.junit.Rule;
import org.junit.Test;
import org.tinylog.Supplier;
import org.tinylog.rules.SystemStreamCollector;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LegacyMessageFormatter}.
 */
public final class LegacyMessageFormatterTest {

	/**
	 * Redirects and collects system output streams.
	 */
	@Rule
	public final SystemStreamCollector systemStream = new SystemStreamCollector(true);

	/**
	 * Verifies that a text message without any placeholders will be returned unchanged.
	 */
	@Test
	public void withoutPlaceholders() {
		assertThat(format("Hello World!")).isEqualTo("Hello World!");
	}

	/**
	 * Verifies that a placeholder without any context text will be replaced.
	 */
	@Test
	public void onlyPlaceholder() {
		assertThat(format("{}", 42)).isEqualTo("42");
	}

	/**
	 * Verifies that a single placeholder will be replaced.
	 */
	@Test
	public void singlePlaceholder() {
		assertThat(format("Hello {}!", "tinylog")).isEqualTo("Hello tinylog!");
	}

	/**
	 * Verifies that multiple placeholders will be replaced in the correct order.
	 */
	@Test
	public void multiplePlaceholders() {
		assertThat(format("{} + {} = {}", 1, 2, 3)).isEqualTo("1 + 2 = 3");
	}

	/**
	 * Verifies that lazy argument suppliers can be evaluated.
	 *
	 * @see Supplier
	 */
	@Test
	public void lazyArgumentSupplier() {
		Supplier<Integer> supplier = () -> 42;
		assertThat(format("It is {}", supplier)).isEqualTo("It is 42");
	}

	/**
	 * Verifies that text messages with more arguments than placeholders can be handled.
	 */
	@Test
	public void tooManyArguments() {
		assertThat(format("Hello {}!", "tinylog", "world")).isEqualTo("Hello tinylog!");
	}

	/**
	 * Verifies that text messages with less arguments than placeholders can be handled.
	 */
	@Test
	public void tooFewArguments() {
		assertThat(format("Hello {}!")).isEqualTo("Hello {}!");
	}

	/**
	 * Verifies that placeholders with a missing opening curly bracket can be handled.
	 */
	@Test
	public void missingOpeningCurlyBracket() {
		assertThat(format("} Hello", "tinylog")).isEqualTo("} Hello");
		assertThat(format("Hello }", "tinylog")).isEqualTo("Hello }");
	}

	/**
	 * Verifies that placeholders with a missing closing curly bracket can be handled.
	 */
	@Test
	public void missingClosingCurlyBracket() {
		assertThat(format("{ Hello", "tinylog")).isEqualTo("{ Hello");
		assertThat(format("Hello {", "tinylog")).isEqualTo("Hello {");
	}

	/**
	 * Verifies curly brackets with text inside will be ignored.
	 */
	@Test
	public void ignorePlaceholdersWithText() {
		assertThat(format("{foo} {}", "bar")).isEqualTo("{foo} bar");
	}

	/**
	 * Uses {@link LegacyMessageFormatter} for formatting a text message.
	 *
	 * @param message
	 *            Text message with placeholders
	 * @param arguments
	 *            Replacements for placeholders
	 * @return Formatted text message
	 */
	private static String format(final String message, final Object... arguments) {
		return new LegacyMessageFormatter().format(message, arguments);
	}

}
