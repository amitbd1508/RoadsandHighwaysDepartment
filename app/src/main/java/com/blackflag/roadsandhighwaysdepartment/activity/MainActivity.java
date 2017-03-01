package com.blackflag.roadsandhighwaysdepartment.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blackflag.roadsandhighwaysdepartment.fragment.EGPTender;
import com.blackflag.roadsandhighwaysdepartment.fragment.MainFragment;
import com.blackflag.roadsandhighwaysdepartment.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import io.palaima.smoothbluetooth.Device;
import io.palaima.smoothbluetooth.SmoothBluetooth;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static SmoothBluetooth mSmoothBluetooth;
    public static final int ENABLE_BT__REQUEST = 1;


    private void askForPermission(String permission, Integer requestCode) {
        try{
            if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                    //This is called if user has denied the permission before
                    //In this case I am just asking the permission again
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

                } else {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
                }
            } else {
                // Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            
        }
    }
    private SmoothBluetooth.Listener mListener = new SmoothBluetooth.Listener() {
        @Override
        public void onBluetoothNotSupported() {
            Toast.makeText(MainActivity.this, "Bluetooth not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onBluetoothNotEnabled() {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, ENABLE_BT__REQUEST);
        }

        @Override
        public void onConnecting(Device device) {

        }

        @Override
        public void onConnected(Device device) {

            Toast.makeText(MainActivity.this, "Conected to  :  "+device.getName()+"\n"+device.getAddress(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onDisconnected() {
            Toast.makeText(MainActivity.this, "Device Disconnected", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onConnectionFailed(Device device) {
//            mStateTv.setText("Disconnected");
//            mDeviceTv.setText("");
//            Toast.makeText(MainActivity.this, "Failed to connect to " + device.getName(), Toast.LENGTH_SHORT).show();
//            if (device.isPaired()) {
//                mSmoothBluetooth.doDiscovery();
//            }
        }

        @Override
        public void onDiscoveryStarted() {
            Toast.makeText(getApplicationContext(), "Searching", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDiscoveryFinished() {

        }

        @Override
        public void onNoDevicesFound() {
            Toast.makeText(getApplicationContext(), "No device found", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDevicesFound(final List<Device> deviceList,
                                   final SmoothBluetooth.ConnectionCallback connectionCallback) {

            //Toast.makeText(MainActivity.this, "Connected to"+deviceList.get(0).getName()+"", Toast.LENGTH_SHORT).show();


            /*final MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this)
                    .title("Devices")
                    .adapter(new DevicesAdapter(getApplicationContext(), deviceList),null)
                    .build();

            ListView listView = dialog.getRecyclerView();
            if (listView != null) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        connectionCallback.connectTo(deviceList.get(position));
                        dialog.dismiss();
                    }

                });
            }*/
//            new MaterialDialog.Builder(getApplicationContext())
//                    .title("List")
//                    .items(deviceList)
//                    .itemsCallback(new MaterialDialog.ListCallback() {
//                        @Override
//                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
//                            Toast.makeText(getApplicationContext(), which + ": " + text + ", ID = " + view.getId(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .show();



            String names[] ={"A","B","C","D"};
            List<String> list=new ArrayList<String>();
            for (Device d:deviceList)
                list.add(d.getName()+"\n"+d.getAddress()+"\n Paired : "+d.isPaired());
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            final AlertDialog OptionDialog = alertDialog.create();
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.custom, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("Device List");
            ListView lv = (ListView) convertView.findViewById(R.id.listView1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);
            lv.setAdapter(adapter);
            alertDialog.show();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(MainActivity.this, deviceList.get(i).getName(), Toast.LENGTH_SHORT).show();
                    connectionCallback.connectTo(deviceList.get(i));
                    OptionDialog.dismiss();

                }
            });




        }

        @Override
        public void onDataReceived(int data) {

        }


    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //end region
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_connect) {
            mSmoothBluetooth.tryConnection();
            return true;
        } else if (id == R.id.action_disconect) {
            mSmoothBluetooth.disconnect();
            return true;
        } else if (id == R.id.action_about) {

            return true;
        }
        else if(id==R.id.action_status){
            mSmoothBluetooth.tryConnection();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSmoothBluetooth.stop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENABLE_BT__REQUEST) {
            if (resultCode == RESULT_OK) {
                mSmoothBluetooth.tryConnection();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       mSmoothBluetooth = new SmoothBluetooth(this, SmoothBluetooth.ConnectionTo.OTHER_DEVICE, SmoothBluetooth.Connection.SECURE,mListener);
        //mSmoothBluetooth.setListener(mListener);

        askForPermission("android.permission.BLUETOOTH",101);
        askForPermission("android.permission.BLUETOOTH_ADMIN",102);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment(), "Control");
        adapter.addFragment(new EGPTender(), "Settings");

        viewPager.setAdapter(adapter);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
