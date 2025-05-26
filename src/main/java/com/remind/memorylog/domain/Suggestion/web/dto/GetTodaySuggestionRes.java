package com.remind.memorylog.domain.Suggestion.web.dto;

import com.remind.memorylog.domain.Suggestion.entity.Suggestion;
import com.remind.memorylog.domain.course.web.dto.GetCourseRes;
import com.remind.memorylog.domain.mission.web.dto.GetMissionRes;

public record GetTodaySuggestionRes(
        Long suggestionId,
        GetMissionRes mission,
        GetCourseRes course
) {
    public static GetTodaySuggestionRes of(Suggestion suggestion, GetMissionRes mission, GetCourseRes course) {
        return new GetTodaySuggestionRes(suggestion.getSuggestionId(), mission, course);
    }
}
