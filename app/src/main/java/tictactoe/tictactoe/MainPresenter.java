package tictactoe.tictactoe;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tictactoe.tictactoe.base.BasePresenter;
import tictactoe.tictactoe.base.MvpView;
import tictactoe.tictactoe.data.DataManager;

public class MainPresenter extends BasePresenter<MainActivity> {
    private final DataManager dataManager;
    private MvpView mvpView;
    private CompositeSubscription compositeSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainActivity mvpView) {
        this.mvpView = mvpView;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mvpView = null;
        compositeSubscription.unsubscribe();
    }

    public void newGame(String token, Board board) {
        compositeSubscription.add(dataManager.newGame(token, board).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showMessage("Waiting for player 2");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String gameId) {
                        //Backend creates a gameID and returns it to the user once the user has been matched with another user and a game created
                        getMvpView().updateBoardId(gameId);
                    }
                })
        );
    }

    public void updateBoard(String token, Board board) {
        compositeSubscription.add(dataManager.updateBoard(token, board)
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Board>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showMessage("Waiting for player 2");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Board board) {

                    }
                })
        );
    }

    public void registerGcmToken(String token) {
        compositeSubscription.add(dataManager.registerGcmToken(token)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {

                    }
                }));
    }
}
