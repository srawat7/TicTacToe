package tictactoe.tictactoe;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Board implements Parcelable{
    ArrayList<Integer> boardStates;
    private int gameOngoing = 0;
    private int playerTurn; // boolean to keep track of if it is THIS players turn. == true if this players turn.
    private int winner;
    private int gameId;

    public Board() {
        boardStates = new ArrayList<>();
        gameOngoing =  1;
        playerTurn = 1;
        winner = 0;
        resetBoard();
    }

    public Board(ArrayList<Integer> boardStates, int winner) {

    }

    public void updateBoard(ArrayList<Integer> boardStates) {
//        this.boardStates.clear();
//        for(int i = 0; i < boardStates.size(); i++){
//            //this.boardStates.set(i, boardStates.get(i));
//            this.boardStates.add(boardStates.get(i));
//        }
        this.boardStates = boardStates;
    }

    public void resetBoard() {
        this.boardStates.clear();
        for(int i = 0; i < 9; i++) {
            boardStates.add(i, 0);
        }
    }

    public int getGameOngoing() {
        return gameOngoing;
    }

    public void setGameOngoing(int gameOngoing) {
        this.gameOngoing = gameOngoing;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public ArrayList<Integer> getBoardStates() {
        return boardStates;
    }

    public void setBoardStates(ArrayList<Integer> boardStates) {
        this.boardStates = boardStates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(boardStates);
        parcel.writeInt(gameOngoing);
        parcel.writeInt(playerTurn);
        parcel.writeInt(winner);
    }

    protected Board(Parcel in) {
        boardStates = in.readArrayList(Integer.class.getClassLoader());
        gameOngoing = in.readInt();
        playerTurn = in.readInt();
        winner = in.readInt();
    }

    public static final Creator<Board> CREATOR = new Creator<Board>() {
        @Override
        public Board createFromParcel(Parcel in) {
            return new Board(in);
        }

        @Override
        public Board[] newArray(int size) {
            return new Board[size];
        }
    };
}
