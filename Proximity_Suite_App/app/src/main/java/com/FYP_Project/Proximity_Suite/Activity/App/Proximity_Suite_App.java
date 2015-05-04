/**
 * Defines the package to class belongs to.
 */
package com.FYP_Project.Proximity_Suite.Activity.App;
/**
 * Import all of the necessary libraries.
 */
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.FYP_Project.Proximity_Suite.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;
/**
 * A Bluetooth application that will act as a server for authentication.
 *
 * @author Harry Clewlow
 */
public class Proximity_Suite_App extends Activity implements OnClickListener {
    /**
     * Default Serial-Port UUID
     */
    private String defaultUUID = "00001101-0000-1000-8000-00805F9B34FB";
    /**
     * Default bluetooth adapter on the device.
     */
    public BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    /**
     * The prefix to identify devices of interest.
     */
    private final static String PREFIX = "Proximity_";
    /**
     * The Server thread.
     */
    public AcceptThread server;
    /**
     * the device name
     */
    private String originalName = mBluetoothAdapter.getName();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //checks if the device can support Bluetooth.
        if(mBluetoothAdapter == null) {
            this.finish();
        }


        setHandlers();
    }

    @Override
    public void onBackPressed() {
        this.onPause();
    }
    @Override
    public void onPause() {
        super.onPause();
        //clears all the data and resets the device name then closes the application.
       this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
    }

    @Override
    public void onClick(View v) {
        //sets the devices name for testing
        if (mBluetoothAdapter.getName().startsWith(PREFIX)) {
            String t = mBluetoothAdapter.getName().substring(10);
            mBluetoothAdapter.setName(t);
        }
        //creates a button variable.
        Button btn = (Button) v;
        //checks if the start button is pressed.
        if(btn.getId() == R.id.btn_start_server) {
            //checks if bluetooth is enabled.
            if (!mBluetoothAdapter.isEnabled()) {
                //enables the bluetooth and will wait until it enabled.
                while(!mBluetoothAdapter.isEnabled()){
                    mBluetoothAdapter.enable();
                    try
                    {
                        Thread.sleep(1L);
                    }
                    catch (InterruptedException ie)
                    {
                        // unexpected interruption while enabling bluetooth
                        Thread.currentThread().interrupt(); // restore interrupted flag
                        this.onPause();
                        return;
                    }
                }
            }
            //checks if the device name has been alter if not it will alter it.
            if(! mBluetoothAdapter.getName().startsWith(PREFIX)) {
                mBluetoothAdapter.setName(PREFIX + mBluetoothAdapter.getName());
            }
            //makes the device stay awake while its connected.
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            //starts the server thread.
            server = new AcceptThread();
            server.start();
            //sets the start button to be unclickable.
            btn.setEnabled(false);
            //enbables the stop button to be clickable.
            ((Button) this.findViewById(R.id.btn_stop_server)).setEnabled(true);
        }
        //checks if the stop button had been pressed.
        else if(btn.getId() == R.id.btn_stop_server) {
            //closes the server
            server.cancel();
            //set the stop button to be uclickable.
            btn.setEnabled(false);
            //sets the start button to be clickable.
            ((Button) this.findViewById(R.id.btn_start_server)).setEnabled(true);
            //disables the bluetooth
            while(mBluetoothAdapter.isEnabled()){
                mBluetoothAdapter.disable();
                try
                {
                    Thread.sleep(1L);
                }
                catch (InterruptedException ie)
                {
                    // unexpected interruption while enabling bluetooth
                    Thread.currentThread().interrupt(); // restore interrupted flag
                    this.onPause();
                    return;
                }
            }
            //closes the application
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            this.onPause();
        }
    }
    /**
     * Sets the interface button handlers.
     */
    public void setHandlers() {
        Button start_server = (Button) this.findViewById(R.id.btn_start_server);
        start_server.setOnClickListener(this);

        Button stop_server = (Button) this.findViewById(R.id.btn_stop_server);
        stop_server.setOnClickListener(this);
    }
    /**
     * Thread that handles an incoming connection.
     */
    public class AcceptThread extends Thread {
        /**
         * Tag that will appear in the log.
         */
        private final String ACCEPT_TAG = AcceptThread.class.getName();
        /**
         * The bluetooth server socket.
         */
        private final BluetoothServerSocket mServerSocket;

        public AcceptThread() {

            //setup the socket
            BluetoothServerSocket tmp = null;
            try {
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(ACCEPT_TAG,
                        UUID.fromString(defaultUUID));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            //continues to send data while there is an active connection.
            while (true) {
                try {
                    socket = mServerSocket.accept();

                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // sends information
                    try {
                        String buffer = "Go";
                        DataOutputStream in = new DataOutputStream(socket.getOutputStream());
                        //while the connection is alive it will send messages.
                        while(true) {
                            in.writeUTF(buffer);
                        }

                    } catch (IOException e) {
                        //when the connection is lost the application will close.
                        Button stop = (Button) findViewById(R.id.btn_stop_server);
                        stop.performClick();
                        try {
                            mServerSocket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    }

                }
            }
        }
        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mServerSocket.close();
            } catch (IOException e) { }
        }
    }

}