package com.rudolphh.studentscheduler;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.rudolphh.studentscheduler.assessment.AssessmentDao;
import com.rudolphh.studentscheduler.assessment.AssessmentEntity;
import com.rudolphh.studentscheduler.converters.AssessmentTypeConverter;
import com.rudolphh.studentscheduler.converters.DateConverter;
import com.rudolphh.studentscheduler.converters.StatusConverter;
import com.rudolphh.studentscheduler.course.database.CourseDao;
import com.rudolphh.studentscheduler.course.database.CourseEntity;
import com.rudolphh.studentscheduler.course.database.CourseStatus;
import com.rudolphh.studentscheduler.mentor.MentorDao;
import com.rudolphh.studentscheduler.mentor.MentorEntity;
import com.rudolphh.studentscheduler.term.database.TermDao;
import com.rudolphh.studentscheduler.term.database.TermEntity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class, MentorEntity.class}, version = 1, exportSchema = false)
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

            // Sample term data
            TermDao termDao = instance.termDao();
            databaseWriteExecutor.execute(()-> termDao.insert(
                    new TermEntity("Term 1",
                    new GregorianCalendar(2020, Calendar.AUGUST, 11).getTime(),
                    new GregorianCalendar(2020, Calendar.NOVEMBER, 30).getTime())));

            databaseWriteExecutor.execute(()-> termDao.insert(
                    new TermEntity("Term 2",
                            new GregorianCalendar(2020, Calendar.DECEMBER, 1).getTime(),
                            new GregorianCalendar(2021, Calendar.MAY, 31).getTime())));


            // Sample Course Data
            CourseDao courseDao = instance.courseDao();
            databaseWriteExecutor.execute(()-> courseDao.insert(
                    new CourseEntity(1, "C196",
                            new GregorianCalendar(2020, Calendar.AUGUST, 11).getTime(),
                            new GregorianCalendar(2020, Calendar.AUGUST, 28).getTime(),
                            CourseStatus.IN_PROGRESS)
            ));
        }


    };

}
