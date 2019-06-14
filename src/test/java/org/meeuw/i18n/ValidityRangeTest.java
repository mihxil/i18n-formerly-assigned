package org.meeuw.i18n;

import org.junit.Test;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Michiel Meeuwissen
 * @since ...
 */
public class ValidityRangeTest {

	@Test
	public void contains() {
		assertThat(ValidityRange.closed(Year.of(1973), Year.of(2019)).contains(Year.of(2019))).isTrue();
		assertThat(ValidityRange.closed(Year.of(1973), Year.of(2019)).contains(Year.of(2020))).isFalse();
		assertThat(ValidityRange.closed(Year.of(1973), Year.of(2019)).contains(Year.of(2000))).isTrue();
		assertThat(ValidityRange.closed(Year.of(1973), Year.of(2019)).contains(Year.of(1973))).isTrue();
		assertThat(ValidityRange.closed(null, Year.of(2019)).contains(Year.of(1973))).isTrue();

	}
}
