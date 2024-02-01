package com.example.school.domain.enums;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum AlertType {
    @Enumerated(EnumType.STRING)
    THREE_DAYS_BEFORE(3 * 24 * 60),
    ONE_DAY_BEFORE(20),
    THIRTY_MINUTES_BEFORE(19),
    TEN_MINUTES_BEFORE(18)
    ;

    private final int minutesBefore;

    AlertType(int minutesBefore) {
        this.minutesBefore = minutesBefore;
    }

    public int getMinutesBefore() {
        return minutesBefore;
    }
}