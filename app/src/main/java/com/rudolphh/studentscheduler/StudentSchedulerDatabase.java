package com.rudolphh.studentscheduler;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.rudolphh.studentscheduler.assessment.database.Assessment;
import com.rudolphh.studentscheduler.assessment.database.AssessmentDao;
import com.rudolphh.studentscheduler.assessment.database.AssessmentType;
import com.rudolphh.studentscheduler.converters.AssessmentTypeConverter;
import com.rudolphh.studentscheduler.converters.DateConverter;
import com.rudolphh.studentscheduler.converters.StatusConverter;
import com.rudolphh.studentscheduler.course.database.Course;
import com.rudolphh.studentscheduler.course.database.CourseDao;
import com.rudolphh.studentscheduler.course.database.CourseStatus;
import com.rudolphh.studentscheduler.mentor.MentorDao;
import com.rudolphh.studentscheduler.mentor.Mentor;
import com.rudolphh.studentscheduler.term.database.Term;
import com.rudolphh.studentscheduler.term.database.TermDao;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Term.class, Course.class, Assessment.class, Mentor.class}, version = 1, exportSchema = false)
@TypeConverters({AssessmentTypeConverter.class, DateConverter.class, StatusConverter.class })
public abstract class StudentSchedulerDatabase extends RoomDatabase {

    public abstract TermDao termDao();// access term dao
    public abstract CourseDao courseDao();// access term dao
    public abstract AssessmentDao assessmentDao();// access term dao
    public abstract MentorDao mentorDao();// access term dao

    private static volatile StudentSchedulerDatabase instance;// singleton
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized StudentSchedulerDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    StudentSchedulerDatabase.class, "student_scheduler_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }
        return instance;
    }

    // callback for executing code (in this case add sample data) after creation of database
    // can also override onOpen or onDestructiveMigration
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            TermDao termDao = instance.termDao();
            CourseDao courseDao = instance.courseDao();
            AssessmentDao assessmentDao = instance.assessmentDao();

            databaseWriteExecutor.execute(()-> {

                // Sample term data
                termDao.insert(
                        new Term("Term 1",
                                new GregorianCalendar(2020, Calendar.AUGUST, 11).getTime(),
                                new GregorianCalendar(2020, Calendar.NOVEMBER, 30).getTime()));

                termDao.insert(
                        new Term("Term 2",
                                new GregorianCalendar(2020, Calendar.DECEMBER, 1).getTime(),
                                new GregorianCalendar(2021, Calendar.MAY, 31).getTime()));

                // Sample course Data
                courseDao.insert(
                        new Course(1, "C196",
                                new GregorianCalendar(2020, Calendar.AUGUST, 11).getTime(),
                                new GregorianCalendar(2020, Calendar.AUGUST, 31).getTime(),
                                "",
                                CourseStatus.IN_PROGRESS));

                courseDao.insert(
                        new Course(1, "C191",
                                new GregorianCalendar(2020, Calendar.JULY, 3).getTime(),
                                new GregorianCalendar(2020, Calendar.AUGUST, 7).getTime(),
                                "",
                                CourseStatus.COMPLETED));

                // Sample assessment Data
                assessmentDao.insert(
                        new Assessment(1, "Mobile Application",
                                new GregorianCalendar(2020, Calendar.AUGUST, 31).getTime(),
                                AssessmentType.PERFORMANCE));

            });

        }// end onCreate


    };

}
