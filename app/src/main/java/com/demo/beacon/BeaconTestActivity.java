package com.demo.beacon;

import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.demo.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class BeaconTestActivity extends AppCompatActivity implements BeaconConsumer {

    private String TAG = "BeaconTestActivity";

    public static final String mProximityUuidTEST = "FDA50693-A4E2-4FB1-AFCF-C6EB07647825";

    public static final String mProximityUuidTEST1 = "FDA50693-A4E2-4FB1-AFCF-C6EB07647822";
    public static final String mProximityUuidTEST2 = "FDA50693-B4E2-4FB1-AFCF-C6EB07647822";
    public static final String mProximityUuidTEST3 = "FDA50693-C4E2-4FB1-AFCF-C6EB07647822";

    /**
     * 重新调整格式
     */
    public static final String IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";

    private BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_test);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
//         beaconManager.getBeaconParsers().add(new BeaconParser().
//                setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(IBEACON_FORMAT));

        beaconManager.bind(this);

    }

    @Override
    public void onBeaconServiceConnect() {
//        startMonitor();
        startRange();
    }

    private void startMonitor() {

        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.e(TAG, "I just saw an beacon for the first time!");
                startRange();
            }

            @Override
            public void didExitRegion(Region region) {
                Log.e(TAG, "I no longer see an beacon");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.e(TAG, "I have just switched from seeing/not seeing beacons: " + state);
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region(mProximityUuidTEST,
                    null, null, null));
        } catch (RemoteException e) {

        }
    }

    private void startRange() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                Log.e(TAG, " didRangeBeaconsInRegion size = " + beacons.size());
                if (beacons.size() > 0) {
//                    Log.e(TAG, "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");
                    for (Beacon beacon : beacons) {
                        Log.e(TAG, beacon.toString() + "\n");
                    }
//                    Log.e(TAG, " didRangeBeaconsInRegion ---> \n" +   beacons.toString());
                }
            }
        });

        try {
//            beaconManager.startRangingBeaconsInRegion(new Region("test", null, null, null));

//            beaconManager.startRangingBeaconsInRegion(new Region("test1", Identifier.parse(mProximityUuidTEST1), null, null));
//            beaconManager.startRangingBeaconsInRegion(new Region("test2", Identifier.parse(mProximityUuidTEST2), null, null));
//            beaconManager.startRangingBeaconsInRegion(new Region("test1", Identifier.parse(mProximityUuidTEST3), null, null));
//
//            Identifier id1 = Identifier.fromUuid(UUID.fromString("fda50693-a4e2-4fb1-afcf-c6eb07647825"));
              Identifier id1 = Identifier.parse(mProximityUuidTEST);
              Identifier id2 = Identifier.fromInt(10096);
              Identifier id3 = Identifier.fromInt(60999);

            beaconManager.startRangingBeaconsInRegion(new Region(mProximityUuidTEST, id1, id2, id3));

        } catch (RemoteException e) {

        }
    }
}
