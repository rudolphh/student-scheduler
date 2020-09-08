package com.rudolphh.studentscheduler.course.create;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.course.database.Course;
import com.rudolphh.studentscheduler.course.database.CourseRepository;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;
import com.rudolphh.studentscheduler.mentor.database.Mentor;
import com.rudolphh.studentscheduler.mentor.database.MentorRepository;
import com.rudolphh.studentscheduler.term.database.TermRepository;
import com.rudolphh.studentscheduler.term.database.TermWithCourses;

import java.util.List;

public class CourseCreateViewModel extends AndroidViewModel {

    private CourseRepository courseRepository;
    private MentorRepository mentorRepository;
    private TermRepository termRepository;

    public CourseCreateViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
        mentorRepository = new MentorRepository(application);
        termRepository = new TermRepository(application);
    }

    public void insert(Course course){
        courseRepository.insert(course);
    }

    public void insertWithMentor(Course course, Mentor mentor){
        courseRepository.insertWithMentor(course, mentor);
    }

    public void update(Course course){
        courseRepository.update(course);
    }

    public void update(Mentor mentor){
        mentorRepository.update(mentor);
    }


    public LiveData<CourseWithMentorAndAssessments> getCourseById(long id_course){
        return courseRepository.getCourseById(id_course);
    }

    public LiveData<List<TermWithCourses>> getAllTerms(){
        return termRepository.getAllTerms();
    }

}
