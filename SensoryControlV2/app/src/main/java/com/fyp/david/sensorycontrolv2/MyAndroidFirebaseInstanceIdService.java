package com.fyp.david.sensorycontrolv2;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by DAVID on 3/21/2017.
 */

public class MyAndroidFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private final String TAG = "MyAndroidFIIdService";

    public MyAndroidFirebaseInstanceIdService() {

    }
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        //for if you want to store token on server
    }
}
