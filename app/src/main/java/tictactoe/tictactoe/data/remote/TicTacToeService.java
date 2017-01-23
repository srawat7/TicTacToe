package tictactoe.tictactoe.data.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import tictactoe.tictactoe.Board;

public interface TicTacToeService {
    String ENDPOINT = "https://api.tictactoe.com/";
    String ACCESS_TOKEN = "";

    class Factory {
        public static TicTacToeService makeTicTacToeService(Context context) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(ACCESS_TOKEN))
                    .build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TicTacToeService.ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(TicTacToeService.class);
        }
    }

    @POST("v1/register/gcm")
    Observable<String> registerGcmToken(@Query("token") String token);

    @POST("v1/update")
    Observable<Board> updateBoard(@Query("token") String token,
                                 @Body Board board);

    @POST("v1/game/new")
    Observable<String> newGame(@Query("token") String token,
                               @Body Board board);
}
