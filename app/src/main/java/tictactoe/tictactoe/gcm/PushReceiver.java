package tictactoe.tictactoe.gcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tictactoe.tictactoe.Board;

public class PushReceiver extends GcmListenerService {

    private static final String TAG = PushReceiver.class.getSimpleName();

    @Override
    public void onMessageReceived(String from, Bundle data) {
        if(from != null) {
            try {
                Intent broadcastIntent = new Intent(GcmConstants.PUSH_NOTIFICATION);
                Board board = getBoardFromData(data);
                broadcastIntent.putExtra("board", board);
                LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
            } catch (JSONException e) {
                Log.e(TAG, "Failed to handle push");
            }
        }
    }

    public Board getBoardFromData(Bundle data) throws JSONException {
        final JSONObject jsonObject = new JSONObject(data.getString("board"));
        int winner = jsonObject.getInt("winner");

        final JSONArray jsonArray = jsonObject.getJSONArray("board_states");
        ArrayList<Integer> newStates = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            newStates.add(jsonArray.getInt(i));
        }
        return new Board(newStates, winner);
    }
}
