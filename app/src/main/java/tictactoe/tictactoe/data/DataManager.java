package tictactoe.tictactoe.data;

import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

import tictactoe.tictactoe.Board;
import tictactoe.tictactoe.data.remote.TicTacToeService;

@Singleton
public class DataManager {

    private TicTacToeService ticTacToeService;

    @Inject
    public DataManager(TicTacToeService ticTacToeService) {
        this.ticTacToeService = ticTacToeService;
    }

    public Observable<String> newGame(String token, Board board) {
        return ticTacToeService.newGame(token, board);
    }

    public Observable<Board> updateBoard(String token, Board board) {
        return ticTacToeService.updateBoard(token, board);
    }

    public Observable<String> registerGcmToken(String token) {
        return ticTacToeService.registerGcmToken(token);
    }
}
