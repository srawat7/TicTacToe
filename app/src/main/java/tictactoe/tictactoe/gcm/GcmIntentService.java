package tictactoe.tictactoe.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import tictactoe.tictactoe.R;

public class GcmIntentService extends IntentService {

    private static final String TAG = GcmIntentService.class.getSimpleName();

    public GcmIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Intent broadcastIntent = new Intent(GcmConstants.REGISTRATION_COMPLETE);
            broadcastIntent.putExtra("token", token);
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        } catch (Exception e){
            Log.e(TAG, "GCM token registration failed");
        }
    }
}
