package com.example.android.davadose;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Created by ASUS on 3/27/2018.
 */

public class Reminder {
    public static boolean isAfter=false;
    public static String morning;
    public static String noon;
    public static String night;

    public static void setAlarm(Context context,String j1,String j2,String j3){
        String i1=j1.substring(0,5);
        String i2=j2.substring(0,5);
        String i3=j3.substring(0,5);
        String i4=j1.substring(6);
        String i5=j2.substring(6);
        String i6=j3.substring(6);
        int m_t,a_t,n_t;
        if(i4.charAt(0)=='A')
            m_t=0;
        else
            m_t=1;
        if(i5.charAt(0)=='A')
            a_t=0;
        else
            a_t=1;
        if(i6.charAt(0)=='A')
            n_t=0;
        else
            n_t=1;
        String a[]=i1.split(":");
        String b[]=i2.split(":");
        String c[]=i3.split(":");
        int m_h=Integer.parseInt(a[0]);
        int m_m=Integer.parseInt(a[1]);
        int a_h=Integer.parseInt(b[0]);
        int a_m=Integer.parseInt(b[1]);
        int n_h=Integer.parseInt(c[0]);
        int n_m=Integer.parseInt(c[1]);
        if(m_t==1 && m_h!=12)
            m_h=m_h+12;
        if(a_t==1 && a_t!=12)
            a_h=a_h+12;
        if(n_t==1 && n_h!=12)
            n_h=n_h+12;
        if(a_m>60){
            a_h++;
            a_m=a_m%60;
        }
        if(m_m>60){
            m_h++;
            m_m=m_m%60;
        }
        if(n_m>60){
            n_h++;
            n_m=n_m%60;
        }
        NotificationScheduler.setReminder(context,AlarmReceiver.class,m_h,m_m,0);
        NotificationScheduler.setReminder(context,AlarmReceiver.class,a_h,a_m,1);
        NotificationScheduler.setReminder(context,AlarmReceiver.class,n_h,n_m,2);
        //Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    }
}
