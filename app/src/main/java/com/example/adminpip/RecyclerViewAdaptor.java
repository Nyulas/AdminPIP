package com.example.adminpip;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder> {

    private static final String TAG ="RecyclerViewAdapter";

    List<Question> Question;
    Context mContext;
    private com.google.firebase.database.DatabaseReference mDatabase;
    private String selected_group="";


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

        Log.d(TAG, "onBindViewHolder: Called");

        holder.State.setChecked(Question.get(position).getState());

        mDatabase = FirebaseDatabase.getInstance().getReference();

        holder.State.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                        if(isChecked == true)
                        {
                            if(selected_group.equals("")) {
                                Question.get(position).setState(false);
                                Toast.makeText(mContext,"please add a group where to put the message!",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Question.get(position).setState(true);
                                Question.get(position).setGroup(selected_group);
                                mDatabase.child("admin").child("questions").child(Question.get(position).getQuestion()).setValue(Question.get(position));
                            }
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
        private EditText Selected_Group;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Questions= itemView.findViewById(R.id.Question_id);
            State = itemView.findViewById(R.id.State_SW);
            Selected_Group = itemView.findViewById(R.id.Selected_Group_ET);

            Selected_Group.addTextChangedListener(new TextWatcher(){


                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                            selected_group=Selected_Group.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }


    }
}
