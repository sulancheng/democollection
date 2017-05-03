package com.example.jackhsueh.ble_ota;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "BLE OTA";

    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattService mService;
    private BluetoothGattCharacteristic mCharacteristic;


    public static final UUID UPDATE_SERVICE_UUID = UUID.fromString("0000ff00-0000-1000-8000-00805f9b34fb"); // The UUID for service "FF00"
    public static final UUID UPDATE_CHARACTERISTIC_UUID = UUID.fromString("0000ff01-0000-1000-8000-00805f9b34fb"); // The UUID for service "FF00"

    private Button Update_button;
    private Button Exit_button;

    private TextView Status_textView;
    private TextView Update_textView;
    private Object SyncObj = new Object();
    private int mBinFileIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Update_button = (Button) findViewById(R.id.Update_button);
        Exit_button = (Button) findViewById(R.id.Exit_button);
        Status_textView = (TextView)  findViewById(R.id.Status_textView);
        Update_textView = (TextView) findViewById(R.id.Update_textView);


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        else
            mBluetoothAdapter. startLeScan(mLeScanCallback);

        Update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        ProcessOTA();
                    }
                }).start();
            }
        });

        Exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        finish();
                        System.exit(0);
                    }
                }).start();
            }
        });

        //ReadBinary();
    }



    final byte CMD_FW_ERASE = 0x16;
    final byte CMD_FW_WRITE = 0x17;
    final byte CMD_FW_UPGRADE = 0x18;
    final int MAX_TRANS_COUNT = 15;
    private byte Current_Command = 0;

    void ProcessOTA()
    {
        byte [] ReadData;
        int CRC =0;
        ReadData = ReadBinary();
        int i=0;

        EnableButton(Update_button,false);

        for(i=0;i<ReadData.length;i++)
        {
            int CC = ReadData[i];
            CC &= 0x000000FF;
            CRC += CC;
            CRC = CRC & 0x0000FFFF;
        }
        Log.i(TAG,"CRC ==>"+CRC);
        OTA_Erase_Flash();
        WriteFlash_All(ReadData);
        ShowStatus("Update over, please reset the device");
        OTA_Upgrade_Flash(ReadData.length,CRC);


        EnableButton(Exit_button,true);

    }

    void WriteFlash_All(byte []binArray)
    {
        int count = 0;
        byte [] DataArray = new byte[MAX_TRANS_COUNT];
        int WriteCount;
        int cc=0;
        int i;

        WriteCount = binArray.length/MAX_TRANS_COUNT;

        for(i=0;i<binArray.length;i++)
        {
            DataArray[count] = binArray[i];
            count+=1;

            if(count == MAX_TRANS_COUNT)
            {
                int CurrentAddress = i-(count-1);
                cc += 1;
                //Log.i(TAG,"CurrentAddress ==>"+CurrentAddress);
                OTA_Write_Flash(DataArray,CurrentAddress);
                count = 0;
                ShowStatus("Update to ..."+cc+"/"+WriteCount);
            }
            else
            {
                if(i==binArray.length-1)
                {
                    // last time
                    int CurrentAddress = i-(count-1);

                    Log.i(TAG,"Last count==> " + count + " ==> Address==>"  + CurrentAddress + "==> CC ==>"  + cc);
                    cc+=1;
                    OTA_Write_Flash(DataArray,CurrentAddress);
                    count = 0;
                    ShowStatus("Update to ..."+cc+"/"+WriteCount);
                }
            }
        }


    }

    public void OTA_Erase_Flash()
    {
        byte [] WriteData = new byte[2];
        WriteData[0] = CMD_FW_ERASE;
        WriteData[1] = 0x00;

        Current_Command = CMD_FW_ERASE;

        Log.i(TAG, "Process OTA");
        writeCharacteristic(WriteData);
        readCharacteristic();
    }

    public void OTA_Write_Flash(byte[] ProgramData, int Address)
    {
        byte [] WriteData = new byte[20];
        WriteData[0] = CMD_FW_WRITE;
        WriteData[1] = 0x13;
        WriteData[2] = (byte)(Address & 0x000000FF);
        WriteData[3] = (byte)((Address & 0x0000FF00)>>8);
        WriteData[4] = (byte)ProgramData.length;

        Current_Command = CMD_FW_WRITE;

        int i=0;
        for(i=0;i<ProgramData.length;i++)
        {
            WriteData[i+5] = ProgramData[i];
        }
        writeCharacteristic(WriteData);
        readCharacteristic();
    }

    public void OTA_Upgrade_Flash(int Size, int CRC)
    {
        byte [] WriteData = new byte[6];
        WriteData[0] = CMD_FW_UPGRADE;
        WriteData[1] = 0x04;
        WriteData[2] = (byte)(Size & 0x000000FF);
        WriteData[3] = (byte)((Size & 0x0000FF00)>>8);
        WriteData[4] = (byte)(CRC & 0x000000FF);
        WriteData[5] = (byte)((CRC & 0x0000FF00)>>8);

        Current_Command = CMD_FW_UPGRADE;
        writeCharacteristic(WriteData);
        readCharacteristic();

    }

    byte [] ReadBinary() {
        InputStream ins;

        if (mBinFileIndex == 1) {
            ins = getResources().openRawResource(R.raw.pixart1);
            Log.i(TAG, "Read bin 1 ");
            ShowStatus_2("Update to pixart1");
        }
        else
        {
            ins = getResources().openRawResource(R.raw.pixart2);
            Log.i(TAG, "Read bin 2 ");
            ShowStatus_2("Update to pixart2");
        }

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        int size = 0;
        // Read the entire resource into a local byte buffer.
        byte[] buffer = new byte[1024];

        try {
            while((size=ins.read(buffer,0,1024))>=0){
                outputStream.write(buffer,0,size);
            }
            ins.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte []BinaryData;
        BinaryData=outputStream.toByteArray();
        Log.i(TAG, "Binary data size : "+BinaryData.length);

        return BinaryData;
    }

    // For read/write operation
    public void writeCharacteristic(byte[] value)
    {
        mCharacteristic.setValue(value);
        boolean status = mBluetoothGatt.writeCharacteristic(mCharacteristic);
        WaitForSync();
    }

    public byte[] readCharacteristic() {
        mBluetoothGatt.readCharacteristic(mCharacteristic);
        WaitForSync();

        byte [] ReadData = mCharacteristic.getValue();

        if(ReadData[0] != 0x0E)
            Log.i(TAG, "Error at byte 0" +ReadData[0] );

        if(ReadData[1] != 0x02)
            Log.i(TAG, "Error at byte 1" +ReadData[1] );

        if(ReadData[2] != Current_Command)
            Log.i(TAG, "Error at byte 2" +ReadData[2] );

        if(ReadData[3] != 0x00)
            Log.i(TAG, "Error at byte 3" +ReadData[3] );


        return ReadData;
    }

    void WaitForSync()
    {
        synchronized (SyncObj)
        {
            try {
                SyncObj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void NotifyForSync()
    {
        synchronized (SyncObj)
        {
            SyncObj.notify();
        }
    }



    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
                    final String name = device.getName();
                    Log.i(TAG, "Discover device : "+name);
                    if(name != null ) {
                        if (name.equals("JULI-OBU-PIXART1") || name.equals("JULI-OBU-PIXART2")) {

                            if(name.equals("JULI-OBU-PIXART1"))
                                mBinFileIndex = 2;
                            else
                                mBinFileIndex = 1;


                            //Status_textView.setText( "Discover device : " + name);
                            mBluetoothAdapter.stopLeScan(mLeScanCallback);
                            Context cc= getApplication();
                            mBluetoothGatt = device.connectGatt(cc,true,mGattCallback);
                            ShowStatus("Discover device : " + name);
                        }
                    }
                }
            };


    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.i(TAG, "Connected to GATT server.");
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());
                //Toast.makeText(getApplication(), "Connect to  device!  ", Toast.LENGTH_SHORT).show();
                String name = gatt.getDevice().getName();
                ShowStatus(name);
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.i(TAG, "Disconnected from GATT server.");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            List<BluetoothGattService> CurrentServices;

            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.i(TAG, "mBluetoothGatt = " + mBluetoothGatt );
                CurrentServices = gatt.getServices();

                for(BluetoothGattService ss:CurrentServices)
                {
                    if(UPDATE_SERVICE_UUID.equals(ss.getUuid()))
                    {
                        mService = ss;
                        Log.i(TAG, "Find Service : "+ss.getUuid());
                        for( BluetoothGattCharacteristic character : ss.getCharacteristics())
                        {
                            Log.i(TAG, "Character UUID : "+character.getUuid());
                            if(UPDATE_CHARACTERISTIC_UUID.equals(character.getUuid()))
                            {
                                mCharacteristic = character;
                                Log.i(TAG, "Find Characteristics");
                                ShowStatus("Find Characteristics");

                                // Enable the button
                                EnableButton(Update_button,true);
                            }
                        }
                    }
                }
            } else {
                Log.i(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            NotifyForSync();
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //Log.i(TAG, "onCharacteristicRead success" );
            }
            else
                Log.i(TAG, "onCharacteristicRead fail" );
        }

        public void onCharacteristicWrite (BluetoothGatt gatt,
                                    BluetoothGattCharacteristic characteristic,
                                    int status)
        {
            NotifyForSync();
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //Log.i(TAG, "onCharacteristicWrite success");
            }
            else
                Log.i(TAG, "onCharacteristicWrite fail" );
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            Log.i(TAG, "onCharacteristicChanged" );

        }
    };

    void ShowStatus(final String status)
    {
        runOnUiThread( new Runnable( )   // 這個執行緒是為了 UI 畫面顯示
        {    @Override
        public void run( )
        {
            Status_textView.setText(status);
        }
        });
    }
    void ShowStatus_2(final String status)
    {
        runOnUiThread( new Runnable( )   // 這個執行緒是為了 UI 畫面顯示
        {    @Override
        public void run( )
        {
            Update_textView.setText(status);
        }
        });
    }

    void EnableButton(final Button bt,final Boolean IsEnable)
    {
        runOnUiThread( new Runnable( )   // 這個執行緒是為了 UI 畫面顯示
        {    @Override
        public void run( )
        {
            bt.setEnabled(IsEnable);
        }
        });
    }
}