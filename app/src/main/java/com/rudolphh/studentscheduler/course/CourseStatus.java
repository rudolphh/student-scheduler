package com.rudolphh.studentscheduler.course;

public enum CourseStatus {
    IN_PROGRESS(0), COMPLETED(1), DROPPED(2), PLAN_TO_TAKE(3);

    private int code;

    CourseStatus(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
