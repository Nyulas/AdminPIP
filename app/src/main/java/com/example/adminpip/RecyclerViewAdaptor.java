package com.example.adminpip;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder> {

    private static final String TAG ="RecyclerViewAdapter";

    List<Question> Question;
    Context mContext;
    private com.google.firebase.database.DatabaseReference mDatabase;


    public RecyclerViewAdaptor(Context mContext, List<Question>Question)
    {
        this.Question = Question;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_listquestions, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Log.d(TAG,"onBindViewHolder: Called");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        holder.State.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                        if(isChecked == true)
                        {
                            Question.get(position).setState(true);
                            mDatabase.child("admin").child("questions").child(Question.get(position).getQuestion()).setValue(Question.get(position));
                        }
                        else {
                            Question.get(position).setState(false);
                            mDatabase.child("admin").child("questions").child(Question.get(position).getQuestion()).setValue(Question.get(position));
                        }

                    }
                });

        holder.State.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.State.getParent().requestDisallowInterceptTouchEvent(true);
                }
                // always return false since we don't care about handling tapping, flinging, etc.
                return false;
            }
        });


        holder.Questions.setText(Question.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {

        return Question.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Questions;
        private Switch State;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Questions= itemView.findViewById(R.id.Question_id);
            State = itemView.findViewById(R.id.State_SW);

        }


    }
}
