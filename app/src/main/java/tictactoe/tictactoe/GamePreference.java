package tictactoe.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import tictactoe.tictactoe.Board;

public class GamePreference {
    private static final String KEY = "GAME_PREFERENCE";
    private static final String BOARD = "BOARD";

    private static SharedPreferences getSharedPrefences(Context context) {
        return context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public static void saveBoard(Context context, Board board) {
        SharedPreferences.Editor editor = getSharedPrefences(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(board);
        editor.putString(BOARD, json);
        editor.apply();
    }

    public static String getBoard(Context context) {
        SharedPreferences preferences = getSharedPrefences(context);
        return preferences.getString(BOARD, "");
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = getSharedPrefences(context).edit();
        editor.clear();
        editor.commit();
        //editor.putString(BOARD, "");
    }
}
