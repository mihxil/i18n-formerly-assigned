package org.meeuw.i18n.formerlyassigned;

import java.time.Year;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Michiel Meeuwissen
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

	@Test
	public void testToString() {
		assertThat(ValidityRange.closed(Year.of(1973), Year.of(2019)).toString()).isEqualTo("1973 - 2019");
		assertThat(ValidityRange.to(Year.of(2019)).toString()).isEqualTo("- 2019");
		assertThat(ValidityRange.from(Year.of(1973)).toString()).isEqualTo("1973 -");
		assertThat(new ValidityRange(null, null).toString()).isEqualTo("");



	}
}
