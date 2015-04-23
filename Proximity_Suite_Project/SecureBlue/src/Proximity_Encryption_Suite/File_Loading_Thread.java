package Proximity_Encryption_Suite;

import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JProgressBar;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author c012952a
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