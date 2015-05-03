package com.FYP_Project.Proximity_Suite.Activity.Test.Proximity_Suite_App;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.ImageView;

import com.FYP_Project.Proximity_Suite.Activity.App.Proximity_Suite_App;
import com.FYP_Project.Proximity_Suite.R;

public class test extends ActivityInstrumentationTestCase2<Proximity_Suite_App> {

    private Proximity_Suite_App testActivity;

    public test() {
        super(Proximity_Suite_App.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testActivity = getActivity();

    }

    /**
     * Testing that the buttons have been initialized correctly.
     */
    @SmallTest
    public void test_Buttons() {
        Button b1 = (Button) testActivity.findViewById(R.id.btn_start_server);
        Button b2 = (Button) testActivity.findViewById(R.id.btn_stop_server);

        assertNotNull(b1);
        assertNotNull(b2);

        assertEquals("Enable Proximity Detection", b1.getText());
        assertEquals("Disable Proximity Detection", b2.getText());
    }

    /**
     * Testing that the image view has been initialized correctly.
     */
    @SmallTest
    public void test_ImageView() {
        ImageView i1 = (ImageView) testActivity.findViewById(R.id.img_Logo);

        assertNotNull(i1);
    }

    /**
     * Testing if the device has Bluetooth support.
     */
    @SmallTest
    public void test_BluetoothSupport() {
        assertNotNull(testActivity.mBluetoothAdapter);
    }

    /**
     * Testing if the application enables the Bluetooth when the "Enable Proximity Detection"
     * button is pressed.
     */
    @UiThreadTest
    public void test_BluetoothEnabled() {
        Button b1 = (Button) testActivity.findViewById(R.id.btn_start_server);
        b1.performClick();
        assertTrue(testActivity.mBluetoothAdapter.isEnabled());
    }

    /**
     * Testing if the application disables the Bluetooth on exit.
     */
    @UiThreadTest
    public void test_BluetoothDisabled() {
        Button b1 = (Button) testActivity.findViewById(R.id.btn_start_server);
        b1.performClick();
        Button b2 = (Button) testActivity.findViewById(R.id.btn_stop_server);
        b2.performClick();
        assertTrue(!testActivity.mBluetoothAdapter.isEnabled());
    }
    /**
     * Testing if the application changes the device's Bluetooth name.
     */
    @UiThreadTest
    public void test_BluetoothName() {
        Button b1 = (Button) testActivity.findViewById(R.id.btn_start_server);
        b1.performClick();
        boolean b2 = testActivity.mBluetoothAdapter.getName().startsWith("Proximity_");
        assertTrue(b2);
    }

    /**
     * Testing if the application starts the Bluetooth thread.
     */
    @UiThreadTest
    public void test_BluetoothStartThread() {
        Button b1 = (Button) testActivity.findViewById(R.id.btn_start_server);
        Button b2 = (Button) testActivity.findViewById(R.id.btn_stop_server);
        assertTrue(!b2.isEnabled());
        b1.performClick();
        assertTrue(b2.isEnabled());
        assertTrue(testActivity.server.isAlive());
    }

    /**
     * Testing if the application stops the Bluetooth thread.
     */
    @UiThreadTest
    public void test_BluetoothStopThread() {
        Button b1 = (Button) testActivity.findViewById(R.id.btn_start_server);
        Button b2 = (Button) testActivity.findViewById(R.id.btn_stop_server);
        assertTrue(!b2.isEnabled());
        b1.performClick();
        assertTrue(b2.isEnabled());
        assertTrue(testActivity.server.isAlive());
        b2.performClick();
        assertTrue(!testActivity.server.isAlive());

    }
}

