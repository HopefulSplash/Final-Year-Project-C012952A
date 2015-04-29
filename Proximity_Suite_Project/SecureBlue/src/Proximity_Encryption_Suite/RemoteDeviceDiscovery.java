package Proximity_Encryption_Suite;

import java.util.Vector;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

public class RemoteDeviceDiscovery {

    /**
     *
     * @return
     */
    public Vector getDevices() {
        /* Create Vector variable */
        final Vector devicesDiscovered = new Vector();
        try {
            final Object inquiryCompletedEvent = new Object();
            /* Clear Vector variable */
            devicesDiscovered.clear();

            /* Create an object of DiscoveryListener */
            DiscoveryListener listener = new DiscoveryListener() {

                @Override
                public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                    /* Get devices paired with system or in range(Without Pair) */
                    devicesDiscovered.addElement(btDevice);
                }

                @Override
                public void inquiryCompleted(int discType) {
                    /* Notify thread when inquiry completed */
                    synchronized (inquiryCompletedEvent) {
                        inquiryCompletedEvent.notifyAll();
                    }
                }

                /* To find service on bluetooth */
                @Override
                public void serviceSearchCompleted(int transID, int respCode) {
                }

                /* To find service on bluetooth */
                @Override
                public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
                }
            };

            synchronized (inquiryCompletedEvent) {
                /* Start device discovery */
                boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
                if (started) {
                    inquiryCompletedEvent.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* Return list of devices */
        return devicesDiscovered;
    }
}
