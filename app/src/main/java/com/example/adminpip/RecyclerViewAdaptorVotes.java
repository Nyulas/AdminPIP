package com.example.adminpip;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecyclerViewAdaptorVotes extends RecyclerView.Adapter<RecyclerViewAdaptorVotes.ViewHolder> {

    private static final String TAG ="RecyclerViewAdapter";

    List<User> User;
    Context mContext;


    public RecyclerViewAdaptorVotes(Context mContext, List<User>User)
    {
        this.User = User;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_listvotes_rv, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.Name.setText(User.get(position).getName());
        holder.Vote_value.setText(User.get(position).getVote_value());
        holder.Answered_question.setText(User.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {

        return User.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Name;
        private TextView Vote_value;
        private TextView Answered_question;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.Name_ET);
            Vote_value = itemView.findViewById(R.id.Votevalue_ET);
            Answered_question = itemView.findViewById(R.id.Answeredquestion_ET);

        }


    }
}
