package org.meeuw.i18n.formerlyassigned;

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
    public static ValidityRange to(Year to) {
        return new ValidityRange(null, to);
    }
    public static ValidityRange from(Year from) {
        return new ValidityRange(from, null);
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (lowerEndpoint != null) {
            builder.append(lowerEndpoint);
            builder.append(" -");
        }
        if (upperEndpoint != null) {
            if (builder.length() == 0) {
                builder.append("- ");
            } else {
                builder.append(' ');
            }
            builder.append(upperEndpoint);
        }
        return builder.toString() ;
    }
}


