package cc.gilmore.apps.battlebot001;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private TextView infoTextView;
    private TextView logTextView;

    private Button flButton;
    private Button fButton;
    private Button frButton;
    private Button lButton;
    private Button stopButton;
    private Button rButton;
    private Button blButton;
    private Button bButton;
    private Button brButton;

    private final String hc05DeviceAddress = "98:D3:71:FD:8A:8D";

    public static final int REQUEST_ENABLE_BT = 1;

    String SERVICE_ID = "00001101-0000-1000-8000-00805f9b34fb"; //SPP UUID

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private OutputStream bluetoothOutputStream;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeControls();

        initializeBluetoothComponents();
    }

    private void initializeBluetoothComponents() {

        if(bluetoothManager == null){
            bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        }
        logTextView.setText("Got bluetooth manager\n" + logTextView.getText());

        if(bluetoothAdapter == null){
            bluetoothAdapter = bluetoothManager.getAdapter();
        }
        if(bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            bluetoothAdapter = bluetoothManager.getAdapter();
        }
        logTextView.setText("Got bluetooth adapter\n" + logTextView.getText());

        if(bluetoothDevice == null){
            bluetoothDevice = bluetoothAdapter.getRemoteDevice(hc05DeviceAddress);
        }
        logTextView.setText("Got bluetooth device\n" + logTextView.getText());

        try {
            BluetoothSocket socket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString(SERVICE_ID));
            socket.connect();
            logTextView.setText("Connected to bluetooth socket\n" + logTextView.getText());
            bluetoothOutputStream = socket.getOutputStream();
            logTextView.setText("Got bluetooth socket output stream\n" + logTextView.getText());
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
            infoTextView.setText("Error create");
        }
    }

    private void initializeControls() {
        infoTextView = (TextView)findViewById(R.id.infoTextView);
        logTextView = (TextView)findViewById(R.id.logTextView);

        flButton = (Button)findViewById(R.id.flButton);
        fButton = (Button)findViewById(R.id.fButton);
        frButton = (Button)findViewById(R.id.frButton);
        lButton = (Button)findViewById(R.id.lButton);
        stopButton = (Button)findViewById(R.id.buttonStop);
        rButton = (Button)findViewById(R.id.rButton);
        blButton = (Button)findViewById(R.id.blButton);
        bButton = (Button)findViewById(R.id.bButton);
        brButton = (Button)findViewById(R.id.brButton);

        View.OnTouchListener buttonTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN)
                    switch(view.getId()){
                        case R.id.flButton:
                            onFLClick(view);
                            break;
                        case R.id.fButton:
                            onFClick(view);
                            break;
                        case R.id.frButton:
                            onFRClick(view);
                            break;
                        case R.id.lButton:
                            onLClick(view);
                            break;
                        case R.id.buttonStop:
                            onStopClick(view);
                            break;
                        case R.id.rButton:
                            onRClick(view);
                            break;
                        case R.id.blButton:
                            onBLClick(view);
                            break;
                        case R.id.bButton:
                            onBClick(view);
                            break;
                        case R.id.brButton:
                            onBRClick(view);
                            break;
                        default:
                            logTextView.setText("Unexpected id in touch handler\n" + logTextView.getText());
                            break;
                    }
                else
                    onStopClick(stopButton);
                return true;
            }
        };

        flButton.setOnTouchListener(buttonTouchListener);
        fButton.setOnTouchListener(buttonTouchListener);
        frButton.setOnTouchListener(buttonTouchListener);
        lButton.setOnTouchListener(buttonTouchListener);
        stopButton.setOnTouchListener(buttonTouchListener);
        rButton.setOnTouchListener(buttonTouchListener);
        blButton.setOnTouchListener(buttonTouchListener);
        bButton.setOnTouchListener(buttonTouchListener);
        brButton.setOnTouchListener(buttonTouchListener);
    }

    public void onBClick(View view) {
        if(bluetoothOutputStream == null){
            initializeBluetoothComponents();
            if(bluetoothOutputStream == null) {
                infoTextView.setText("No socket");
                return;
            }
        }
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'b';
            bluetoothOutputStream.write(twoByteData);
            logTextView.setText("Backward\n" + logTextView.getText());
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
            infoTextView.setText("Error B");
        }
    }

    public void onBLClick(View view) {
        if(bluetoothOutputStream == null){
            initializeBluetoothComponents();
            if(bluetoothOutputStream == null) {
                infoTextView.setText("No socket");
                return;
            }
        }
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'b';
            bluetoothOutputStream.write(twoByteData);
            logTextView.setText("Backward left\n" + logTextView.getText());
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
            infoTextView.setText("Error BL");
        }
    }

    public void onBRClick(View view) {
        if(bluetoothOutputStream == null){
            initializeBluetoothComponents();
            if(bluetoothOutputStream == null) {
                infoTextView.setText("No socket");
                return;
            }
        }
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'b';
            bluetoothOutputStream.write(twoByteData);
            logTextView.setText("Backward right\n" + logTextView.getText());
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
            infoTextView.setText("Error BR");
        }
    }

    public void onFClick(View view) {
        if(bluetoothOutputStream == null){
            initializeBluetoothComponents();
            if(bluetoothOutputStream == null) {
                infoTextView.setText("No socket");
                return;
            }
        }
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'f';
            bluetoothOutputStream.write(twoByteData);
            logTextView.setText("Forward\n" + logTextView.getText());
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
            infoTextView.setText("Error F");
        }
    }

    public void onFLClick(View view) {
        if(bluetoothOutputStream == null){
            initializeBluetoothComponents();
            if(bluetoothOutputStream == null) {
                infoTextView.setText("No socket");
                return;
            }
        }
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'f';
            bluetoothOutputStream.write(twoByteData);
            logTextView.setText("Forward left\n" + logTextView.getText());
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
            infoTextView.setText("Error FL");
        }
    }

    public void onFRClick(View view) {
        if(bluetoothOutputStream == null){
            initializeBluetoothComponents();
            if(bluetoothOutputStream == null) {
                infoTextView.setText("No socket");
                return;
            }
        }
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'f';
            bluetoothOutputStream.write(twoByteData);
            logTextView.setText("Forward right\n" + logTextView.getText());
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
            infoTextView.setText("Error FR");
        }
    }

    public void onRClick(View view) {
        if(bluetoothOutputStream == null){
            initializeBluetoothComponents();
            if(bluetoothOutputStream == null) {
                infoTextView.setText("No socket");
                return;
            }
        }
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'r';
            bluetoothOutputStream.write(twoByteData);
            logTextView.setText("Right\n" + logTextView.getText());
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
            infoTextView.setText("Error R");
        }
    }

    public void onLClick(View view) {
        if(bluetoothOutputStream == null){
            initializeBluetoothComponents();
            if(bluetoothOutputStream == null) {
                infoTextView.setText("No socket");
                return;
            }
        }
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'l';
            bluetoothOutputStream.write(twoByteData);
            logTextView.setText("Left\n" + logTextView.getText());
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
            infoTextView.setText("Error L");
        }
    }

    public void onStopClick(View view) {
        if(bluetoothOutputStream == null){
            initializeBluetoothComponents();
            if(bluetoothOutputStream == null) {
                infoTextView.setText("No socket");
                return;
            }
        }
        try {
            byte[] twoByteData = new byte[1];
            twoByteData[0] = (byte)'s';
            bluetoothOutputStream.write(twoByteData);
            logTextView.setText("Stop\n" + logTextView.getText());
        }
        catch(IOException e) {
            Log.e("BT MG", e.getMessage());
            infoTextView.setText("Error S");
        }
    }
}
