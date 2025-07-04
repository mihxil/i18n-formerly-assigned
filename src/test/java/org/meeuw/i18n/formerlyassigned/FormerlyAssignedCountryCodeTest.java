package org.meeuw.i18n.formerlyassigned;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class FormerlyAssignedCountryCodeTest {

    @Test
    public void list() {
        Stream.of(FormerlyAssignedCountryCode.values()).forEach(f ->
            System.out.println(f + " " + f.getName() + " " + f.getValidity()));

        assertThat(FormerlyAssignedCountryCode.values()).hasSize(31);
    }
}
