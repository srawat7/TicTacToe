package tictactoe.tictactoe.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tictactoe.tictactoe.data.remote.TicTacToeService;
import tictactoe.tictactoe.injection.ApplicationContext;

@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    TicTacToeService provideRedditNowService() {
        return TicTacToeService.Factory.makeTicTacToeService(mApplication);
    }
}
