package com.rudolphh.studentscheduler.assessment.database;

public enum AssessmentType {
    OBJECTIVE(0), PERFORMANCE(1);

    private int code;

    AssessmentType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
