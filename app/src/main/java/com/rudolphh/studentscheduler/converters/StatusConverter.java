package com.rudolphh.studentscheduler.converters;

import androidx.room.TypeConverter;

import com.rudolphh.studentscheduler.course.database.CourseStatus;

public class StatusConverter {

    @TypeConverter
    public static CourseStatus toStatus(int code){
        switch (code) {
            case 3: return CourseStatus.PLAN_TO_TAKE;
            case 2: return CourseStatus.DROPPED;
            case 1: return CourseStatus.COMPLETED;
            default: return CourseStatus.IN_PROGRESS;
        }
    }

    @TypeConverter
    public static int toCode(CourseStatus status){
        return status.getCode();
    }

    public static String fromCourseStatus(CourseStatus courseStatus){
        switch (courseStatus){
            case PLAN_TO_TAKE: return "Plan to Take";
            case DROPPED: return "Dropped";
            case COMPLETED: return "Completed";
            default: return "In Progress";
        }
    }

    public static CourseStatus toCourseStatus(String status){
        switch (status){
            case "Plan to Take": return CourseStatus.PLAN_TO_TAKE;
            case "Dropped": return CourseStatus.DROPPED;
            case "Completed": return CourseStatus.COMPLETED;
            default: return CourseStatus.IN_PROGRESS;
        }
    }
}
