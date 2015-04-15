package Proximity_Encryption_Suite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author c012952a
 */
public class ProcessFilesThread implements Runnable {

    Object mainGUI;
    File speficDir;
    ArrayList<File> resultFiles;

    public ProcessFilesThread(Suite_Window mainGUI, File speficDir) {
        this.mainGUI = mainGUI;
        this.speficDir = speficDir;
    }
    
      public ProcessFilesThread(viewCurrentlyFolder mainGUI, File speficDir) {
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
                    // add to arraylist
                }

            }
        }
    }

}
