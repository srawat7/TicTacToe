package tictactoe.tictactoe;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Activity activity;
    private final LayoutInflater layoutInflater;
    ArrayList<Integer> boardStates;
    OnBoardClickListener onBoardClickListener;

    public BoardAdapter(Activity activity, ArrayList<Integer> boardStates) {
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
        this.boardStates = boardStates;
    }

    public void setBoardClickListener(OnBoardClickListener onBoardClickListener) {
        this.onBoardClickListener = onBoardClickListener;
    }

    public void updateBoard(ArrayList<Integer> boardStates) {
        this.boardStates = boardStates;
        notifyDataSetChanged();
    }

    public void renderAdapter() {
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BoardItemViewHolder viewHolder = new BoardItemViewHolder(
                layoutInflater.inflate(R.layout.board_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BoardItemViewHolder viewHolder = (BoardItemViewHolder) holder;
        final int currentState = boardStates.get(position);
        if(currentState == 1) {
            viewHolder.state.setText("X");
        } else if (currentState == 2){
            viewHolder.state.setText("O");
        } else if(currentState == 0) {
            viewHolder.state.setText("-");
        }

        viewHolder.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newState = (currentState == 0) ? 1: 0;
                boardStates.set(position, newState);
                renderAdapter();
                onBoardClickListener.onBoardClick(boardStates);
            }
        });
    }

    @Override
    public int getItemCount() {
        return boardStates.size();
    }

    static class BoardItemViewHolder extends RecyclerView.ViewHolder {
        TextView state;
        BoardItemViewHolder(View itemView) {
            super(itemView);
            state = (TextView) itemView;
        }
    }

    public interface OnBoardClickListener {
        void onBoardClick(ArrayList<Integer> boardStates);
    }
}
