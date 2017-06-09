package com.example.pooja.bhumi;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by shyam on 21/4/17.
 */

public class BhumiFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "Bhumi";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: send new registration to raspberry Pi sensors
        //sendRegistrationToServer(refreshedToken);
    }

}
