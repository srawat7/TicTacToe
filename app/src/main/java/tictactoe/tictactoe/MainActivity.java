package tictactoe.tictactoe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tictactoe.tictactoe.base.BaseActivity;
import tictactoe.tictactoe.base.MvpView;
import tictactoe.tictactoe.data.DataManager;
import tictactoe.tictactoe.gcm.GcmConstants;

public class MainActivity extends BaseActivity implements MvpView, BoardAdapter.OnBoardClickListener{
    @Inject DataManager dataManager;
    @Inject MainPresenter presenter;
    @BindView(R.id.board_gridview) RecyclerView boardGridView;
    GridLayoutManager gridLayoutManager;
    Board board;
    BoardAdapter adapter;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityComponent().inject(this);
        ButterKnife.bind(this);
        presenter.attachView(this);
        initBoardUI();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(GcmConstants.REGISTRATION_COMPLETE)) {
                    presenter.registerGcmToken(intent.getStringExtra("token"));
                } else if(intent.getAction().equals(GcmConstants.PUSH_NOTIFICATION)) {
                    Board board = intent.getParcelableExtra("board");
                    updateBoard(board);
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(GcmConstants.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(GcmConstants.PUSH_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(board != null){
            GamePreference.saveBoard(this, board);
        }
        presenter.detachView();
    }

    public void initBoardUI() {
        // If a board for this user has not yet been created then create it and request a new game from the server. The server will return a gameID as response.
        if(GamePreference.getBoard(this) == null || "".equalsIgnoreCase(GamePreference.getBoard(this))) {
            board = new Board();
            board.setGameOngoing(1);
            presenter.newGame("j2hfbfgo", board); //first param should be a backend generated token that the user is holding
        } else {
            //If a board already exists, use it.
            String boardString = GamePreference.getBoard(this);
            board = (new Gson()).fromJson(boardString, Board.class);
        }

        adapter = new BoardAdapter(this, board.getBoardStates());
        adapter.setBoardClickListener(this);
        gridLayoutManager = new GridLayoutManager(this, 3);
        boardGridView.setAdapter(adapter);
        boardGridView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onBoardClick(ArrayList<Integer> boardStates) {
        checkGameConditions(boardStates);
    }

    public void checkGameConditions(ArrayList<Integer> boardStates) {
        //2 end conditions for current user. After making a move check if they meet win conditions or if the board is full then its a draw. Else, keep going.
        boolean boardFull = !(boardStates.contains(0));
        if(checkRowWinConditions(boardStates) || checkColumnWinConditions(boardStates)) {
            showMessage("You won!");
            board.setWinner(1);
            presenter.updateBoard("aisdfhh", board);
            resetBoard();
        } else if(boardFull) {
            showMessage("Game ended in a draw");
            resetBoard();
        } else {
            board.updateBoard(boardStates);
            presenter.updateBoard("ohasdfoh", board);
        }
    }

    public void resetBoard(){
        board.resetBoard();
        board.setGameOngoing(0);
        adapter.updateBoard(board.getBoardStates());
        presenter.newGame("adkdfskfd", board);
        GamePreference.saveBoard(this, board);
    }

    public void updateBoardId(String boardId) {
        board.setGameId(Integer.valueOf(boardId));
    }

    public boolean checkRowWinConditions(ArrayList<Integer> boardStates) {
        boolean gameWon = true;
        for(int i = 0; i < 3; i++) {
            gameWon = gameWon && (boardStates.get(i) == 1);
        }
        return gameWon;
    }

    public boolean checkColumnWinConditions(ArrayList<Integer> boardStates) {
        boolean gameWon = true;
        for(int i = 0; i < 7; i += 3) {
            gameWon = gameWon && (boardStates.get(i) == 1);
        }
        return gameWon;
    }

    public boolean checkDiagonalWinConditions(ArrayList<Integer> boardStates) {
        return true;
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //Received a notification that player 2 has made a move. We received the updated board and check if user 2 has won the game on their last move, if so then reset and find a new game
    //else update current players board and keep going.
    public void updateBoard(Board board) {
        if(board.getWinner() != 0) {
            showMessage("Player 2 has won the game!");
            resetBoard();
        }
        else {
            //update player board
            this.board = board;
            adapter.updateBoard(board.getBoardStates());
            GamePreference.saveBoard(this, board);
        }
    }
}
