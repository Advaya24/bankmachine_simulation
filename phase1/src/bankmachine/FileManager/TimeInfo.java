package bankmachine.FileManager;


import java.text.SimpleDateFormat;
import java.util.Calendar;

//THIS IS STILL UNDER CONSTRUCTION
// I know it's bulky at the moment but I'm just getting it to work, the optimizing it.
// Only has hours so far. Not dates.


public class TimeInfo {

    private int hour;
    private int minute;
    private int second;
    private int f_minute;
    private int f_second;
    private int f_hour;


    //Set Time method. Only used once -> when Bank Manager sets initial time of ATM.
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

        //Writes the Bank Manager's time set into the time.txt file.
        WriteFile output_date = new WriteFile("time.txt");
        output_date.writeData(hour + ":" + minute + ":" + second+ " ", false);

        //Writes the Computer actual time into the time.txt file (when Bank Manager set time).
        String curr_time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        output_date.writeData(curr_time+" ", true);

        //Computes difference in System actual time and time set by Manager, and adds this to the time.txt file.
        String[] split_time = curr_time.split(":");
        int hd = hour - Integer.parseInt(split_time[0]);
        int md = minute - Integer.parseInt(split_time[1]);
        int sd = second - Integer.parseInt(split_time[2]);
        String difference = hd + ":" + md + ":" + sd;
        output_date.writeData(difference, true);

    }

    public String toString(){ // toString method - not used for now.
        return f_hour + ":" + f_minute + ":" + f_second;
    }

    //Method where actual time of ATM is returned. Updated as of calling of method.
    public String getTime() throws Exception{
        //Access time.txt file
        ReadFile time_input = new ReadFile("time.txt");
        String file_data = time_input.getData();
        //Splits Set time, actual time at set time, difference in set and actual time
        String[] split_times = file_data.split(" ");
        String difference = split_times[2];

        //Gets actual current time of System (as of calling of this method)
        String curr_time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        //Splits SimpleDateFormat (actual time) into Strings and then parses (hour, minute, second) into integers
        String[] split_time = curr_time.split(":");
        int curr_h = Integer.parseInt(split_time[0]);
        int curr_m = Integer.parseInt(split_time[1]);
        int curr_s = Integer.parseInt(split_time[2]);

        //Splits difference into Strings and then parses (hour, minute, second) into integers
        String[] split_diff = difference.split(":");
        int diff_h = Integer.parseInt(split_diff[0]);
        int diff_m = Integer.parseInt(split_diff[1]);
        int diff_s = Integer.parseInt(split_diff[2]);

        //Assigns final hour, minute and second (current time + difference)
        f_hour= curr_h + diff_h;
        if(f_hour > 24){
            f_hour -= 24;
        }
        if(f_hour < 0){
            f_hour += 24;
        }
        f_minute = curr_m + diff_m; // Here
        f_second = curr_s + diff_s;

        return f_hour + ":" + f_minute + ":" + f_second;

    }




}
