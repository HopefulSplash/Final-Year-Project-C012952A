package Proximity_Encryption_Suite;


import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.swing.JComboBox;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TheThoetha
 */
public class BluetoothThread implements Runnable {

    static ArrayList<RemoteDevice> devicesDiscovered = new ArrayList<>();
    JComboBox j1;

    public JComboBox getJ1() {
        return j1;
    }

    public void setJ1(JComboBox j1) {
        this.j1 = j1;
    }

    public static ArrayList<RemoteDevice> getDevicesDiscovered() {
        return devicesDiscovered;
    }

    public static void setDevicesDiscovered(ArrayList<RemoteDevice> devicesDiscovered) {
        BluetoothThread.devicesDiscovered = devicesDiscovered;

    }

    @Override
    public void run() {
        final Object inquiryCompletedEvent = new Object();

        devicesDiscovered.clear();

        DiscoveryListener listener = new DiscoveryListener() {

            @Override
            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                try {
                    if (!btDevice.getFriendlyName(true).isEmpty()) {

                        devicesDiscovered.add(btDevice);
                        System.out.println(devicesDiscovered.size());
                        j1.insertItemAt(btDevice.getFriendlyName(true), j1.getItemCount() - 1);
                    }

                } catch (IOException ex) {
                    Logger.getLogger(BluetoothThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void inquiryCompleted(int discType) {

                if (devicesDiscovered.isEmpty()) {
                    j1.removeItemAt(0);
                    j1.insertItemAt("No Devices Found", 0);
                    j1.setSelectedIndex(0);
                    j1.setEnabled(true);
                } else {
                    j1.removeItemAt(0);
                    j1.insertItemAt("Please Select A Device", 0);
                    j1.setSelectedIndex(0);
                    j1.setEnabled(true);
                }
                synchronized (inquiryCompletedEvent) {
                    inquiryCompletedEvent.notifyAll();
                }
            }

            @Override
            public void serviceSearchCompleted(int transID, int respCode) {
            }

            @Override
            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            }
        };

        synchronized (inquiryCompletedEvent) {
            try {
                boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
                if (started) {

                    inquiryCompletedEvent.wait();

                }
            } catch (BluetoothStateException | InterruptedException ex) {
                // Logger.getLogger(BluetoothThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
