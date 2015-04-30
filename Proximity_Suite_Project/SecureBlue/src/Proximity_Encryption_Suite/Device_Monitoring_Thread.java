/**
 * Defines the package to class belongs to.
 */
package Proximity_Encryption_Suite;

/**
 * Import all of the necessary libraries.
 */
import java.io.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.*;
import javax.microedition.io.*;

/**
 * The Device_Monitoring_Thread.Java Class implements an method of checking the
 * status of a connection between a mobile device and the application.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Device_Monitoring_Thread extends Thread implements DiscoveryListener {

    /**
     * Service serial-port UUID
     */
    protected UUID defaultUUID = new UUID(0x1101);

    /**
     * Local bluetooth device.
     */
    private LocalDevice local;

    /**
     * Agent responsible for the discovery of bluetooth devices.
     */
    private DiscoveryAgent agent;

    /**
     * Output stream used to send information to the bluetooth.
     */
    private DataOutputStream dout;

    /**
     * Bluetooth Connection.
     */
    private StreamConnection conn;

    /**
     * List of bluetooth devices of interest. (name starting with the defined
     * token)
     */
    private Vector<RemoteDevice> devices;

    /**
     * Services of interest (defined in UUID) of each device.
     */
    private final Vector<ServiceRecord> services;

    private volatile boolean connected = true;
    private int didConnect = -1;
    private String deviceAddress;

    public Device_Monitoring_Thread() {
        services = new Vector<>();
    }

    @Override
    public void run() {
        findDevices();

    }

    /**
     * Find all the discoverable devices in range.
     */
    protected void findDevices() {
        try {
            devices = new Vector<>();
            LocalDevice local_B = LocalDevice.getLocalDevice();
            DiscoveryAgent agent_B = local_B.getDiscoveryAgent();

            agent_B.startInquiry(DiscoveryAgent.GIAC, this);
        } catch (BluetoothStateException e) {
        }
    }

    /**
     * Obtains a list of services with the UUID defined from a device.
     *
     * @param device Device to obtain the service list.
     */
    protected void findServices(RemoteDevice device) {
        try {
            UUID[] uuids = new UUID[1];
            uuids[0] = defaultUUID;    //The UUID of the each service
            local = LocalDevice.getLocalDevice();
            agent = local.getDiscoveryAgent();

            agent.searchServices(null, uuids, device, this);
        } catch (BluetoothStateException e) {
        }
    }

    /**
     * a method that will receive signals from a mobile device.
     */
    public synchronized void broadcastCommand() {

        // connects and recives singals from the desired device
        for (ServiceRecord sr : services) {
            String url = sr.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
            conn = null;
            try {
                conn = (StreamConnection) Connector.open(url);

                if (conn == null) {
                    didConnect = 1;
                    connected = false;
                    conn.close();
                }
                String buffer;
                //opening inputstream 
                DataInputStream in = new DataInputStream(conn.openDataInputStream());
                String c = null;
                try {
                    didConnect = 0;

                    while (true) {
                        //read input stream
                        c = in.readUTF();
                        //ends the program when stream is cancelled 
                        if (c.isEmpty()) {
                            didConnect = 1;
                            connected = false;
                            in.close();
                            conn.close();
                            break;
                        }
                        c = "";
                    }
                    //ends the program when stream is cancelled 
                    didConnect = 1;
                    connected = false;
                    in.close();
                    conn.close();
                    break;
                } catch (IOException wq) {
                    //ends the program when stream is cancelled 
                    didConnect = 1;
                    connected = false;
                    in.close();
                    conn.close();
                    break;
                }

            } catch (IOException e) {
                //ends the program when stream is cancelled 
                didConnect = 1;
                connected = false;

                break;
            }

        }
        //ends the program when stream is cancelled 
        didConnect = 1;
        connected = false;

    }

    public void closeConn() {
        didConnect = 1;
        connected = false;
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (IOException ex) {
        }
    }

    /**
     * a method to get is the connection is still active
     *
     * @return
     */
    public boolean isConnected() {

        return connected;
    }

    /**
     * a method to set the connection status
     *
     * @param connected
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    /**
     * a method to get if the device did connect
     *
     * @return
     */
    public int getDidConnect() {
        return didConnect;
    }

    /**
     * a method to set the status if the device was connected to
     *
     * @param didConnect
     */
    public void setDidConnect(int didConnect) {
        this.didConnect = didConnect;
    }

    /**
     * a method the set the device address
     *
     * @param deviceAddress
     */
    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    @Override
    public void deviceDiscovered(RemoteDevice arg0, DeviceClass arg1) {
        try {
            String address = arg0.getBluetoothAddress();
            String name = arg0.getFriendlyName(true);
            //only store desired device
            if (address.equals(deviceAddress) && name.startsWith("Proximity_")) {
                devices.add(arg0);
            }
        } catch (IOException ex) {
            Logger.getLogger(Device_Monitoring_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void inquiryCompleted(int arg0) {

        // Start service probing
        if (!devices.isEmpty()) {
            for (RemoteDevice d : devices) {
                findServices(d);
            }
        } else {
            connected = false;
            didConnect = 1;
        }
    }

    @Override
    public void serviceSearchCompleted(int arg0, int arg1) {

        broadcastCommand();
        connected = false;
        didConnect = 1;
    }

    @Override
    public void servicesDiscovered(int arg0, ServiceRecord[] arg1) {
        for (ServiceRecord x : arg1) {
            services.add(x);
        }
    }

}
