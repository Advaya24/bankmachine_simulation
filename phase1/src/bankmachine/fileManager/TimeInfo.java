package bankmachine.fileManager;


import org.mockito.internal.matchers.Null;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.time.*;


public class TimeInfo {

    private long offset;
    /* Last monthly execution */
    private int lastMonth;

    /* The filename for the file where the data for time is stored */
    private static final String filename = "time.txt";

    public TimeInfo(){
        String dataFileName;
        try{
            ReadFile time_input = new ReadFile(filename);
            dataFileName = time_input.getData();
        } catch(IOException e){
            offset = lastMonth = 0;
            return;
        }
        String[] splitTimes = dataFileName.split("\n");
        try{
            offset = Long.parseLong(splitTimes[0]);
        } catch (NumberFormatException e){
            offset = 0;
        }
        try{
            lastMonth = Integer.parseInt(splitTimes[1]);
        } catch (NumberFormatException e){
            lastMonth = 0;
        }
        saveFile();
    }

    public String getSavableString(){
        return Long.toString(offset) + "\n" + Integer.toString(lastMonth);
    }

    public void saveFile(){
        WriteFile outputDate = new WriteFile(filename);
        outputDate.writeData(getSavableString(), false);
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
