/**
 * Defines the package to class belongs to.
 */
package Proximity_Encryption_Suite;
/**
 * Import all of the necessary libraries.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.bluetooth.*;
/**
 * The Device_Service.Java Class implements an method to check what services the 
 * mobile device can offer to determine if it can be used with the application.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Device_Service {

    /** 
     * UUID used to find specific service supported by bluetooth device
    */
    /* To find push object service */
    private final UUID OBEX_OBJECT_PUSH_PROFILE = new UUID(0x1105);
    /* To find file transfer service */
    private final UUID OBEX_FILE_TRANSFER_PROFILE = new UUID(0x1106);
    /* To find hands free service */
    private final UUID HANDS_FREE = new UUID(0x111E);
    /* Get URL attribute from bluetooth service */
    private final int URL_ATTRIBUTE = 0X0100;
    
    public Map<String, List<String>> getBluetoothDevices() {        
        /**
         * Find service on bluetooth device 
        /* Initialize UUID Array */
        UUID[] searchUuidSet = new UUID[]{HANDS_FREE};
        final Object serviceSearchCompletedEvent = new Object();
        int[] attrIDs = new int[]{URL_ATTRIBUTE};
        
        /* Create an object to get list of devices in range or paired */
        Device_Discovery remoteDeviceDiscovery = new Device_Discovery();
        /* Create map to return Bluetooth device address, name and URL */
        final Map<String, List<String>> mapReturnResult = new HashMap<>(); 

        try {
            /* Create an object of DiscoveryListener */
            DiscoveryListener listener;
            listener = new DiscoveryListener() {
                
                /* To find bluetooth devices */
                @Override
                public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                }

                /* To find bluetooth devices */
                @Override
                public void inquiryCompleted(int discType) {
                }

                /* Find service URL of bluetooth device */
                @Override
                public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
                    for (int i = 0; i < servRecord.length; i++) {
                        /* Find URL of bluetooth device */
                        String url = servRecord[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
                        if (url == null) {
                            continue;
                        }
                        String temporaryString = "";
                        /* Get object of bluetooth device */
                        RemoteDevice rd = servRecord[i].getHostDevice();
                        /* Get attribute from ServiceRecord */
                        DataElement serviceName = servRecord[i].getAttributeValue(URL_ATTRIBUTE);
                        if (serviceName != null) {         
                            temporaryString = serviceName.getValue() + "\n" + url;
                            /* Put it in map */
                            mapReturnResult.get(rd.getBluetoothAddress()).add(temporaryString);
                        } else {
                            temporaryString = "Uknown service \n" + url;
                            /* Put it in map */
                            mapReturnResult.get(rd.getBluetoothAddress()).add(temporaryString);
                        }
                    }
                }

                @Override
                public void serviceSearchCompleted(int transID, int respCode) {
                    /* Notify thread when search completed */
                    synchronized (serviceSearchCompletedEvent) {
                        serviceSearchCompletedEvent.notifyAll();
                    }
                }
            };

            /* Get list of bluetooth device from class Device_Discovery */
            for (Enumeration en = remoteDeviceDiscovery.getDevices().elements(); en.hasMoreElements();) {
                /* Get RemoteDevice object */
                RemoteDevice btDevice = (RemoteDevice) en.nextElement();
                /* Create list to return details */
                List<String> listDeviceDetails = new ArrayList<>();
                
                try {
                    /* Add bluetooth device name and address in list */
                    listDeviceDetails.add(btDevice.getFriendlyName(false));
                    listDeviceDetails.add(btDevice.getBluetoothAddress());
                } catch (IOException e) {
                }
                
                /* Put bluetooth device details in map */
                mapReturnResult.put(btDevice.getBluetoothAddress(), listDeviceDetails);
                synchronized (serviceSearchCompletedEvent) {
                    LocalDevice.getLocalDevice().getDiscoveryAgent().searchServices(attrIDs, searchUuidSet, btDevice, listener);
                    serviceSearchCompletedEvent.wait();
                }
            }
        } catch (BluetoothStateException | InterruptedException e) {
        }
        /* Return bluetooth devices detail */
        return mapReturnResult;
    }
}
