package bankmachine.fileManager;


import java.time.temporal.ChronoUnit;
import java.time.*;


public class TimeInfo {

    private long offset;
    /* Last monthyl execution */
    private int lastMonth;

    private static final String file = "time.txt";

    public TimeInfo(){
        String file_data;
        try{
            ReadFile time_input = new ReadFile(file);
            file_data = time_input.getData();
        } catch(Exception e){
            offset = lastMonth = 0;
            return;
        }
        String[] split_times = file_data.split("\n");
        try{
            offset = Long.parseLong(split_times[0]);
        } catch (Exception e){
            offset = 0;
        }
        try{
            lastMonth = Integer.parseInt(split_times[1]);
        } catch (Exception e){
            lastMonth = 0;
        }
        saveFile();
    }

    public String getSavableString(){
        return Long.toString(offset) + "\n" + Integer.toString(lastMonth);
    }

    public void saveFile(){
        try {
            WriteFile output_date = new WriteFile(file);
            output_date.writeData(getSavableString(), false);
        } catch (Exception e){
            return;
        }
    }

    //Set Time method. Only used once -> when Bank Manager sets initial time of ATM.
    public void setTime(LocalDateTime date) {
        offset = ChronoUnit.SECONDS.between(date, LocalDateTime.now());
        saveFile();
    }

    public void setTime(String date) {
        setTime(LocalDateTime.parse(date));
    }

    public String toString(){ // toString method - not used for now.
        return getTime().toString();
    }

    //Method where actual time of ATM is returned. Updated as of calling of method.
    public LocalDateTime getTime()  {
        return LocalDateTime.now().plusSeconds(offset);
    }

    public int getCurrentMonth(){
        return getTime().getMonthValue();
    }

    public int getLastMonth(){
        return lastMonth;
    }
    public void setLastMonth(int month){
        lastMonth = month;
        saveFile();
    }
}
