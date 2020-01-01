package cc.gilmore.apps.battlebot001;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private String data;
    byte[] messageBytes = null;

    private final String deviceNameHC05 = "HC-05";
    private final String deviceNameHC08 = "HC-08";
    private final String deviceNameHM10 = "HM-10";
    private final String hc05DeviceAddress = "98:D3:71:FD:8A:8D";
    private final String hc08DeviceAddress = "C8:FD:19:51:1C:20";

    private boolean scan = false;

    public static final int REQUEST_ENABLE_BT = 1;

    public final static String ACTION_GATT_CONNECTED = "ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "ACTION_DATA_AVAILABLE";
    public final static String EXTRA_UUID = "EXTRA_UUID";
    public final static String EXTRA_DATA = "EXTRA_DATA";

    String SERVICE_ID = "00001101-0000-1000-8000-00805f9b34fb"; //SPP UUID

    //BLE Scanner app
    private DeviceInformationService deviceInformation; //180A
    private GenericAccessService genericAccess;         //1800
    private GenericAttributeService genericAttribute;   //1801
    private CustomServiceE serviceE;                    //FFE0
    private CustomServiceF serviceF;                    //FFF0

    ListView listView;

    //https://developer.android.com/guide/topics/connectivity/bluetooth#ConnectDevices
    private BluetoothAdapter mmAdapter;
    private BluetoothGatt mmGatt;
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    private Set<BluetoothDevice> scannedDevices = new HashSet<>();
    BluetoothGattService mmService;
    BluetoothGattCharacteristic mmCharacteristic;
    private UUID serviceUuid;
    private UUID characteristicUuid;
    private OutputStream out;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceInformation = new DeviceInformationService(); //180A
        genericAccess = new GenericAccessService();         //1800
        genericAttribute = new GenericAttributeService();   //1801
        serviceF = new CustomServiceF();                    //FFF0
        serviceE = new CustomServiceE();                    //FFE0

        serviceUuid = serviceF.uuid;
        characteristicUuid = serviceF.getCharacteristic("1").uuid;

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mmAdapter = bluetoothManager.getAdapter();

        if(mmAdapter == null || !mmAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        if(scan) {
            mmAdapter.startDiscovery();
        }
        else {
            mmDevice = mmAdapter.getRemoteDevice(hc05DeviceAddress);
            mmGatt = mmDevice.connectGatt(this, true, gattCallback);

            try {
                BluetoothSocket socket = mmDevice.createRfcommSocketToServiceRecord(UUID.fromString(SERVICE_ID));
                socket.connect();
                out = socket.getOutputStream();
                //byte[] twoByteData = new byte[1];
                //twoByteData[0] = (byte)'r';
                //out.write(twoByteData);
            }
            catch(IOException e) {
                Log.e("BT MG", e.getMessage());
            }

            /*
            ParcelUuid[] uuids = mmDevice.getUuids();

            for(ParcelUuid id : uuids) {
                Log.d("U", id.getUuid().toString());
            }
            */
        }
    }

    private BluetoothAdapter.LeScanCallback scanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if(!scannedDevices.contains(device) && null != device.getName()) {
                if(null == mmDevice) {
                    mmDevice = device;
                }
            };
        }
    };

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                broadcastUpdate(intentAction);
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.

                boolean startedDiscovery = mmGatt.discoverServices();
                Log.i(TAG, "Attempting to start service discovery:" + startedDiscovery);

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if(status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);

                if (gatt == mmGatt) {
                    Log.d(TAG, "");
                } else {
                    Log.d(TAG, "");
                }

                writeDataToServiceCharacteristic();
            }
        }

        @Override
        public void onCharacteristicWrite (BluetoothGatt gatt,
                                           BluetoothGattCharacteristic characteristic,
                                           int status) {
            Log.d(TAG, "");

        }
    };

    private void writeDataToServiceCharacteristic() {
        mmService = mmGatt.getService(serviceUuid);
        mmCharacteristic = mmService.getCharacteristic(characteristicUuid);
        boolean setNotification = mmGatt.setCharacteristicNotification(mmCharacteristic, true);

        byte[] twoByteData = new byte[1];
        twoByteData[0] = (byte)'r';
        //twoByteData[1] = (byte)'\r';
        mmCharacteristic.setValue(twoByteData);

        if (mmGatt.writeCharacteristic(mmCharacteristic)) {
            Log.e(TAG, "");
        }
        else {
            Log.e(TAG, "");
        }
    }

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    public void onBClick(View view) {
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'b';
            out.write(twoByteData);
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
        }
    }

    public void onFClick(View view) {
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'f';
            out.write(twoByteData);
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
        }
    }

    public void onRClick(View view) {
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'r';
            out.write(twoByteData);
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
        }
    }

    public void onLClick(View view) {
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'l';
            out.write(twoByteData);
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
        }
    }

    public void onStopClick(View view) {
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'s';
            out.write(twoByteData);
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
        }
    }
}
