package com.rudolphh.studentscheduler.converters;

import androidx.room.TypeConverter;

import com.rudolphh.studentscheduler.assessment.database.AssessmentType;

public class AssessmentTypeConverter {

    @TypeConverter
    public static AssessmentType codeToAssessmentType(int code){
        if (code == 1) {
            return AssessmentType.PERFORMANCE;
        }
        return AssessmentType.OBJECTIVE;
    }

    @TypeConverter
    public static int assessmentTypeToCode(AssessmentType assessmentType){
        return assessmentType.getCode();
    }

}

