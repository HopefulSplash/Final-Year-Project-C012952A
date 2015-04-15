package Proximity_Encryption_Suite;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TheThoetha
 */
public class RemoveRowThread implements Runnable {

    int index;
    ArrayList<File> filelists = new ArrayList<>();

    String path;
    JTable j1;

    public RemoveRowThread(int index, String path, JTable j1) {
        this.index = index;
        this.path = path;
        this.j1 = j1;
    }

    public void run() {

        filelists.remove(index);
        ((DefaultTableModel) j1.getModel()).removeRow(index);

        for (int i = 0; i < j1.getRowCount(); i++) {

            if (j1.getValueAt(i, 1).equals(path)) {
                ((DefaultTableModel) j1.getModel()).removeRow(i);
                filelists.remove(i);
                i = -1;
            }

        }
    }
}
