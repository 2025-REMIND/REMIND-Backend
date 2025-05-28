package com.remind.memorylog.domain.Suggestion.web.dto;

import com.remind.memorylog.domain.Suggestion.entity.Suggestion;
import com.remind.memorylog.domain.SuggestionImage.Entity.SuggestionImage;
import com.remind.memorylog.domain.course.entity.Course;
import com.remind.memorylog.domain.course.entity.CourseDetail;
import com.remind.memorylog.domain.course.web.dto.GetCourseRes;
import com.remind.memorylog.domain.mission.entity.Mission;
import com.remind.memorylog.domain.mission.web.dto.GetMissionRes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public record GetArchiveSuggestionRes(
        Long suggestionId,
        LocalDate date,
        String missionDescription,
        String place,
        String course1,
        String course2,
        String course3,
        List<String> images

) {
    public static GetArchiveSuggestionRes from(Suggestion suggestion) {
        Mission mission = suggestion.getMission();
        Course course = suggestion.getCourse();

        List<CourseDetail> courseDetailList = course != null ? course.getCourseDetails() : List.of();
        List<String> courseContents = courseDetailList.stream()
                .sorted(Comparator.comparing(CourseDetail::getId))
                .map(CourseDetail::getContent)
                .collect(Collectors.toList());

        String course1 = courseContents.size() > 0 ? courseContents.get(0) : null;
        String course2 = courseContents.size() > 1 ? courseContents.get(1) : null;
        String course3 = courseContents.size() > 2 ? courseContents.get(2) : null;


        List<String> images = suggestion.getSuggestionImages().stream()
                .map(SuggestionImage::getImageUrl)
                .collect(Collectors.toList());


        return new GetArchiveSuggestionRes(
                suggestion.getSuggestionId(),
                suggestion.getCreatedAt().toLocalDate(),
                mission != null ? mission.getDescription() : null,
                course.getPlace(),
                course1,
                course2,
                course3,
                images
        );
    }

}
