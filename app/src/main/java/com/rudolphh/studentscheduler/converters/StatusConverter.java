package com.rudolphh.studentscheduler.converters;

import androidx.room.TypeConverter;

import com.rudolphh.studentscheduler.course.CourseStatus;

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
}
