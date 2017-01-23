package tictactoe.tictactoe.injection.component;

import dagger.Component;
import tictactoe.tictactoe.MainActivity;
import tictactoe.tictactoe.injection.PerActivity;
import tictactoe.tictactoe.injection.module.ActivityModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}

