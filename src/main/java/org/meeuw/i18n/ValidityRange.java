package org.meeuw.i18n;

import java.time.Year;

/**
 * @author Michiel Meeuwissen
 * @since 0.3
 */
public class ValidityRange {

    private final Year lowerEndpoint;

    private final Year upperEndpoint;

    public static ValidityRange closed(Year from, Year to) {
        return new ValidityRange(from, to);
    }

    public ValidityRange(Year lowerEndpoint, Year upperEndpoint) {
        this.lowerEndpoint= lowerEndpoint;
        this.upperEndpoint = upperEndpoint;
    }

    public Year upperEndpoint() {
        return upperEndpoint;
    }

    public Year lowerEndpoint() {
        return lowerEndpoint;
    }

    public boolean contains(Year year) {
        return (lowerEndpoint == null || ! lowerEndpoint.isAfter(year)) &&
                (upperEndpoint == null || ! upperEndpoint.isBefore(year));
    }
}


