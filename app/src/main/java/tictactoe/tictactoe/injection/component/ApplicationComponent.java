package tictactoe.tictactoe.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import tictactoe.tictactoe.TicTacToeApplication;
import tictactoe.tictactoe.data.DataManager;
import tictactoe.tictactoe.data.remote.TicTacToeService;
import tictactoe.tictactoe.injection.ApplicationContext;
import tictactoe.tictactoe.injection.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(TicTacToeApplication ticTacToeApplication);

    @ApplicationContext
    Context context();
    Application application();
    TicTacToeService ticTacToeService();
    DataManager dataManager();
}
