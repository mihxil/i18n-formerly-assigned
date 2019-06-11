package org.meeuw.i18n;

import java.time.Year;

/**
 * @author Michiel Meeuwissen
 * @since 0.3
 */
public class ValidityRange {

    private final Year from;

    private final Year to;

    public static ValidityRange closed(Year from, Year to) {
        return new ValidityRange(from, to);
    }

    public ValidityRange(Year from, Year to) {
        this.from = from;
        this.to = to;
    }


    public Year getFrom() {
        return from;
    }

    public Year getTo() {
        return to;
    }


    public Year upperEndpoint() {
        return getTo();
    }

    public Year lowerEndpoint() {
        return getFrom();
    }
}


