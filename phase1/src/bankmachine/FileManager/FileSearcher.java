package bankmachine.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearcher {

    private String fileNameToSearch;
    private List<String> result = new ArrayList<>();

    public String getFileNameToSearch() {
        return fileNameToSearch;
    }

    public void setFileNameToSearch(String fileNameToSearch) {
        this.fileNameToSearch = fileNameToSearch;
    }

    public List<String> getResult() {
        return result;
    }


    public void searchForDirectory(File file) {
        if (file.isDirectory()) {
            //do you have permission to read this directory?
            if (file.canRead()) {
                try {
                    for (File temp : file.listFiles()) {
                        if (temp.isDirectory() && !temp.getName().equalsIgnoreCase("out")) {
                            if (getFileNameToSearch().equalsIgnoreCase(temp.getName())) {
                                result.add(temp.getAbsoluteFile().toString());
                            }
                            searchForDirectory(temp);
                        }
                    }
                } catch (NullPointerException e) {
                    // Do nothing
                }

            } else {
                System.out.println(file.getAbsoluteFile() + "Permission Denied");
            }
        }
    }
}
