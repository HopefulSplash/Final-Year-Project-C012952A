/**
 * Defines the package to class belongs to.
 */
package Proximity_Encryption_Suite;
/**
 * Import all of the necessary libraries.
 */
import java.io.File;
import java.util.ArrayList;
/**
 * The File_Loading_Thread.Java Class implements an method to gather the related
 * files a user whats to add or view on the system and gets there details for each
 * in the background.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class File_Loading_Thread implements Runnable {
    
    Object mainGUI;
    File speficDir;
    ArrayList<File> resultFiles;

    
    public File_Loading_Thread(Suite_Window mainGUI, File speficDir) {
        this.mainGUI = mainGUI;
        this.speficDir = speficDir;
    }
    
    public File_Loading_Thread(Folder_Current mainGUI, File speficDir) {
        this.mainGUI = mainGUI;
        this.speficDir = speficDir;
    }
    
    public File_Loading_Thread(Folder_Management mainGUI, File speficDir) {
        this.mainGUI = mainGUI;
        this.speficDir = speficDir;
    }
   
    public File getSpeficDir() {
        return speficDir;
    }
    
    public void setSpeficDir(File speficDir) {
        this.speficDir = speficDir;
    }
    
    public void setResultFiles(ArrayList<File> resultFiles) {
        this.resultFiles = resultFiles;
    }
    
    @Override
    public void run() {
       
     Search(speficDir);
    }
    
    public void Search(File file) {
        
        if (file.exists()) {
            
            if (file.isDirectory()) {
                if (file.canRead()) {
                    
                    File[] listOfFiles = file.listFiles();
                    if (listOfFiles != null) {
                        for (int i = 0; i < listOfFiles.length; i++) {
                            Search(listOfFiles[i]);
                        }
                    }
                }
            } else if (file.isFile()) {
                
                if (file.canRead()) {
                    
                    resultFiles.add(file);
                    
                }
                
            }
        }
    }
}