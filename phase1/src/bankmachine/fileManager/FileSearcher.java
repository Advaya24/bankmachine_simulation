package bankmachine.fileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearcher {

    /**
     * Name of the file to search
     */
    private String fileNameToSearch;
    /**
     * List of strings giving paths for the required file
     */
    private List<String> result = new ArrayList<>(1);

    public String getFileNameToSearch() {
        return fileNameToSearch;
    }

    public void setFileNameToSearch(String fileNameToSearch) {
        this.fileNameToSearch = fileNameToSearch;
    }

    public List<String> getResult() {
        return result;
    }


    /**
     * Searches for the required file with fileNameToSearch and stores it in result
     *
     * @param file the file/directory in which to search
     */
    public void searchForDirectoryIn(File file) {
        if (file.isDirectory()) {
            //do you have permission to read this directory?
            if (file.canRead()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    for (File temp : listFiles) {
                        if (temp.isDirectory() && !temp.getName().equalsIgnoreCase("out")) {
                            if (getFileNameToSearch().equalsIgnoreCase(temp.getName())) {
                                result.add(temp.getAbsoluteFile().toString());
                            }
                            searchForDirectoryIn(temp);
                        }
                    }
                }


            } else {
                System.out.println(file.getAbsoluteFile() + "Permission Denied");
            }
        }
    }

    /**
     * Clears all the results
     */
    public void clearResults() {
        result.clear();
    }
}
