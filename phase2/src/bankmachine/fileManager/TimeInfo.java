package bankmachine.fileManager;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class TimeInfo {

    private long offset;
    /**
     * Last monthly execution
     */
    private int lastMonth;

    /**
     * The filename for the file where the data for time is stored
     */
    private static final String filename = "time.txt";

    public TimeInfo() {
        String dataFileName;
        try {
            ReadFile timeInput = new ReadFile(filename);
            dataFileName = timeInput.getData();
        } catch (IOException e) {
            offset = lastMonth = 0;
            return;
        }
        String[] splitTimes = dataFileName.split("\n");
        try {
            offset = Long.parseLong(splitTimes[0]);
        } catch (NumberFormatException e) {
            offset = 0;
        }
        try {
            lastMonth = Integer.parseInt(splitTimes[1]);
        } catch (NumberFormatException e) {
            lastMonth = 0;
        }
        saveFile();
    }

    /**
     * Get a savable format for the time info
     *
     * @return a string suitable for saving
     */
    public String getSavableString() {
        return offset + "\n" + lastMonth;
    }

    /**
     * Write time info data to the file
     */
    public void saveFile() {
        WriteFile outputDate = new WriteFile(filename);
        outputDate.writeData(getSavableString(), false);
    }


    /**
     * Set Time method. Only used once, when Bank Manager sets initial time of ATM.
     *
     * @param date the current date
     */
    public void setTime(LocalDateTime date) {
        offset = ChronoUnit.SECONDS.between(date, LocalDateTime.now());
        saveFile();
    }

    public void setTime(String date) {
        setTime(LocalDateTime.parse(date));
    }

    public String toString() { // toString method - not used for now.
        return getTime().toString();
    }

    /**
     * Method where actual time of ATM is returned. Updated as of calling of method.
     *
     * @return actual time of ATM
     */
    public LocalDateTime getTime() {
        return LocalDateTime.now().plusSeconds(offset);
    }

    public int getCurrentMonth() {
        return getTime().getMonthValue();
    }

    public int getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(int month) {
        lastMonth = month;
        saveFile();
    }
}
