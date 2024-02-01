package com.example.school.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum FacilityTag {
    QUIET("#조용한"), MEETING("#회의"), TEAMPLAY("#팀플"), STUDYROOM("#스터디룸"), PRINT("#프린트");

    private final String tag;
}
