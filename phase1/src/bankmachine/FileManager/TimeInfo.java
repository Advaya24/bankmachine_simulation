package bankmachine.FileManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeInfo {

    private int hour;
    private int minute;
    private int second;


    private int f_minute;
    private int f_second;
    private int f_hour;


    public void setTime(int h, int m, int s) throws Exception{
        if(h >= 0 && h <= 24) {
            hour = h;
        }
        if(m >= 0 && m <= 60) {
            minute = m;
        }
        if (s >= 0 && s <= 60) {
            second = s;
        }
        WriteFile output_date = new WriteFile("time.txt");

        String formatted_date = hour + ":" + minute + ":" + second+ " ";

        output_date.writeData(formatted_date, false);

        String curr_time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        output_date.writeData(curr_time+" ", true);


        String[] splitdates = curr_time.split(":");
        int hd = hour - Integer.parseInt(splitdates[0]);
        int md = minute - Integer.parseInt(splitdates[1]);
        int sd = second - Integer.parseInt(splitdates[2]);

        String difference = hd + ":" + md + ":" + sd;

        output_date.writeData(difference, true);

    }

    public String toString(){
        return f_hour + ":" + f_minute + ":" + f_second;
    }


    public String getTime() throws Exception{
        ReadFile time_input = new ReadFile("time.txt");
        String file_data = time_input.getData();
        String[] split_times = file_data.split(" ");
        String difference = split_times[2];
        //System.out.println(difference + "Difference");

        String curr_time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        String[] splitdates = curr_time.split(":");
        int curr_h = Integer.parseInt(splitdates[0]);
        int curr_m = Integer.parseInt(splitdates[1]);
        int curr_s = Integer.parseInt(splitdates[2]);

        String[] split_diff = difference.split(":");
        int diff_h = Integer.parseInt(split_diff[0]);
        int diff_m = Integer.parseInt(split_diff[1]);
        int diff_s = Integer.parseInt(split_diff[2]);

        f_hour= curr_h + diff_h;
        f_minute = curr_m + diff_m;
        f_second = curr_s + diff_s;



        return f_hour + ":" + f_minute + ":" + f_second;

    }




}
