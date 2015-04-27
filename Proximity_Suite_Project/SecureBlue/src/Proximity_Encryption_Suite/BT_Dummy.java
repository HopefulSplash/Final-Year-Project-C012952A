package Proximity_Encryption_Suite;

import java.io.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.bluetooth.*;
import javax.microedition.io.*;

/**
 * Class responsible for the configuration/usage of bluetooth.
 *
 * As a client it will try to connect to all devices with the name prefix "BT_"
 * and send "Hello world" to them.
 *
 * @author Fernando Alexandre
 */
public class BT_Dummy extends Thread implements DiscoveryListener {

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
    private Vector<ServiceRecord> services;

    public BT_Dummy() {
        services = new Vector<ServiceRecord>();
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
            devices = new Vector<RemoteDevice>();
            LocalDevice local = LocalDevice.getLocalDevice();
            DiscoveryAgent agent = local.getDiscoveryAgent();

            agent.startInquiry(DiscoveryAgent.GIAC, this);
        } catch (Exception e) {
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
        } catch (Exception e) {
        }
    }

    public synchronized void broadcastCommand() {
        for (ServiceRecord sr : services) {
            String url = sr.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);

            conn = null;

            try {

                conn = (StreamConnection) Connector.open(url);
                didConnect = 0;
                String buffer;

                DataInputStream in = new DataInputStream(conn.openDataInputStream());

                String c = null;
                try {
                    while (true) {

                        c = in.readUTF();
                        if (!c.equals("Go")) {
                            connected = false;
                            in.close();
                            conn.close();
                            break;
                        }
                        c = "";
                    }
                    in.close();
                    conn.close();
                    didConnect = 1;
                    connected = false;
                    break;
                } catch (IOException wq) {
                    connected = false;
                    in.close();
                    conn.close();
                    break;
                }

            } catch (IOException e) {
                connected = false;
                break;
            }

        }
        didConnect = 1;
        connected = false;
    }

    public boolean isConnected() {

        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public int getDidConnect() {
        return didConnect;
    }

    public void setDidConnect(int didConnect) {
        this.didConnect = didConnect;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    static volatile boolean connected = true;
    int didConnect = -1;
    String deviceAddress;

    @Override
    public void deviceDiscovered(RemoteDevice arg0, DeviceClass arg1) {
        try {
            String address = arg0.getBluetoothAddress();
            String name = arg0.getFriendlyName(true);
            if (address.equals(deviceAddress) && name.startsWith("Proximity_")) {
                devices.add(arg0);
            }
        } catch (IOException ex) {
            Logger.getLogger(BT_Dummy.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    @Override
    public void servicesDiscovered(int arg0, ServiceRecord[] arg1) {
        for (ServiceRecord x : arg1) {
            services.add(x);
        }
    }

}