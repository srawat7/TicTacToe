package tictactoe.tictactoe;

import android.app.Application;
import android.content.Context;

import tictactoe.tictactoe.injection.component.ApplicationComponent;
import tictactoe.tictactoe.injection.component.DaggerApplicationComponent;
import tictactoe.tictactoe.injection.module.ApplicationModule;

public class TicTacToeApplication extends Application {
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
    }

    public static TicTacToeApplication get(Context context) {
        return (TicTacToeApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
