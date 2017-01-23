package tictactoe.tictactoe.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tictactoe.tictactoe.TicTacToeApplication;
import tictactoe.tictactoe.injection.component.ActivityComponent;
import tictactoe.tictactoe.injection.component.DaggerActivityComponent;
import tictactoe.tictactoe.injection.module.ActivityModule;

public class BaseActivity extends AppCompatActivity {
    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(TicTacToeApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }
}
