package tictactoe.tictactoe.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class GcmTokenRefreshService extends InstanceIDListenerService {

    private static final String TAG = GcmTokenRefreshService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        Intent intent = new Intent(this, GcmIntentService.class);
        startService(intent);
    }
}
