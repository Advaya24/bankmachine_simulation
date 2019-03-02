package bankmachine.FileManager;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Arrays;
import java.util.Date;
import java.time.*;

//THIS IS STILL UNDER CONSTRUCTION
// I know it's bulky at the moment but I'm just getting it to work, the optimizing it.
// Only has hours so far. Not dates.


public class TimeInfo {

    private int f_minute;
    private int f_second;
    private int f_hour;
    private int f_day;
    private int f_month;
    private int f_year;

    private Date set_time;
    private Date set_time_actual;


    //Set Time method. Only used once -> when Bank Manager sets initial time of ATM.
    public void setTime(String date/*int h, int m, int s, int day, int month, int year*/) throws Exception{

        set_time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(date);
        System.out.println(set_time);

        String curr_time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        set_time_actual = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(curr_time);
        System.out.println(set_time_actual);

        String[] split_time = date.split(" ");
        String[] cal = split_time[0].split("/");
        String[] ti = split_time[1].split(":");

        String[] curr_time_split = curr_time.split(" ");
        String[] cts_d = curr_time_split[0].split("/");
        String[] cts_t = curr_time_split[1].split(":");

        int d_days = Integer.parseInt(cal[0]) - Integer.parseInt(cts_d[0]);
        int d_months = Integer.parseInt(cal[1]) - Integer.parseInt(cts_d[1]);
        int d_years = Integer.parseInt(cal[2]) - Integer.parseInt(cts_d[2]);

        int d_hours = Integer.parseInt(ti[0]) - Integer.parseInt(cts_t[0]);
        int d_mins = Integer.parseInt(ti[1]) - Integer.parseInt(cts_t[1]);
        int d_sec = Integer.parseInt(ti[2]) - Integer.parseInt(cts_t[2]);



        String delta = d_days +","+d_months+"," +d_years+","+d_hours+","+d_mins+ "," +d_sec;

        WriteFile output_date = new WriteFile("time.txt");
        output_date.writeData(set_time + "#" + set_time_actual + "#" + delta,  false);

        long diff = set_time.getTime() - set_time_actual.getTime();
        System.out.println(diff + " diff");






    }

    public String toString(){ // toString method - not used for now.
        return f_hour + ":" + f_minute + ":" + f_second;
    }

    //Method where actual time of ATM is returned. Updated as of calling of method.
    public Date getTime() throws Exception {

        ReadFile time_input = new ReadFile("time.txt");
        String file_data = time_input.getData();
        //Splits Set time, actual time at set time, difference in set and actual time
        String[] split_times = file_data.split("#");
        String difference = split_times[2];

        String[] diff_elements = difference.split(",");
        int diff_day = Integer.parseInt(diff_elements[0]);
        int diff_month = Integer.parseInt(diff_elements[1]);
        int diff_year = Integer.parseInt(diff_elements[2]);

        int diff_hour = Integer.parseInt(diff_elements[3]);
        int diff_min = Integer.parseInt(diff_elements[4]);
        int diff_sec = Integer.parseInt(diff_elements[5]);

        //Gets actual current time of System (as of calling of this method)
        String curr_time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

        //Splits SimpleDateFormat (actual time) into Strings and then parses (hour, minute, second) into integers
        String[] timedate = curr_time.split(" ");
        String[] split_date = timedate[0].split("/");
        int curr_day = Integer.parseInt(split_date[0]);
        int curr_month = Integer.parseInt(split_date[1]);
        int curr_year = Integer.parseInt(split_date[2]);

        String[] split_time = timedate[1].split(":");

        int curr_hour = Integer.parseInt(split_time[0]);
        int curr_min = Integer.parseInt(split_time[1]);
        int curr_sec = Integer.parseInt(split_time[2]);

        f_day = curr_day + diff_day;
        f_month = curr_month + diff_month;
        f_year = curr_year + diff_year;
        f_hour = curr_hour + diff_hour;
        f_minute = curr_min + diff_min;
        f_second = curr_sec + diff_sec;

        String test = f_day+"/"+f_month+"/"+f_year+" "+f_hour+":"+f_minute+":"+f_second;

        Date ree = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(test);
        System.out.println(ree);

        return ree;

    }




}
