package com.rudolphh.studentscheduler;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import com.rudolphh.studentscheduler.assessment.details.AssessmentDetailsActivity;
import com.rudolphh.studentscheduler.assessment.main.AssessmentMainActivity;
import com.rudolphh.studentscheduler.course.details.CourseDetailsActivity;
import com.rudolphh.studentscheduler.course.main.CourseMainActivity;

import static com.rudolphh.studentscheduler.StudentSchedulerApp.CHANNEL_GOAL_DATES_ID;

public class AlertBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        long id_assessment = 0;
        long id_course = 0;

        if(extras != null){
            id_assessment = extras.getLong("id_assessment");
            id_course = extras.getLong("id_course");
        }

        if(id_assessment > 0){
            notifyAssessmentDueDate(context, intent);
        } else if (id_course > 0){
            notifyCourseStartOrEnd(context, intent);
        }

    }// end onReceive


    private void notifyCourseStartOrEnd(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        long id_course = 0;
        String courseTitle = "";
        boolean courseStart = false;
        int notificationId = 0;

        if(extras != null){
            id_course = extras.getLong("id_course");
            courseTitle = extras.getString("course_title");
            courseStart = extras.getBoolean("course_start");
            notificationId = extras.getInt("notification_id");
        }

        // Create an explicit intent for an Activity in your app
        Intent pIntent = new Intent(context, CourseDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id_course", id_course);
        pIntent.putExtras(bundle);

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(pIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(notificationId, PendingIntent.FLAG_UPDATE_CURRENT);

        // set up the notification
        String title;
        String message;

        if(courseStart) {
            title = "Course start reminder";
            message = courseTitle + " has started";
        } else {
            title = "Course end reminder";
            message = courseTitle + " has ended";
        }

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_GOAL_DATES_ID)
                .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, notification);

    }

    private void notifyAssessmentDueDate(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        long id_assessment = 0;
        String assessmentTitle = "";
        int notificationId = 0;

        if(extras != null){
            id_assessment = extras.getLong("id_assessment");
            assessmentTitle = extras.getString("assessment_title");
            notificationId = extras.getInt("notification_id");
        }

        // Create an explicit intent for an Activity in your app
        Intent pIntent = new Intent(context, AssessmentDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id_assessment", id_assessment);
        pIntent.putExtras(bundle);

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(pIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(notificationId, PendingIntent.FLAG_UPDATE_CURRENT);

        // set up the notification
        String title = "Assessment Due";
        String message = assessmentTitle + " assessment is due";

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_GOAL_DATES_ID)
                .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, notification);
    }


}
