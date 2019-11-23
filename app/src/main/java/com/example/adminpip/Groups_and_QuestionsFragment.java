package com.example.adminpip;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Groups_and_QuestionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Groups_and_QuestionsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText CreateGroup_ET,Question_ET;
    Button CreateGroup_BT,AddQuestion_BT;
    private com.google.firebase.database.DatabaseReference mDatabase;
    private com.google.firebase.database.DatabaseReference mDatabase2;
    private com.google.firebase.database.DatabaseReference groupReff;
    Question question;
    Group group;
    String GroupName;
    RecyclerView recyclerView;
    ArrayList<Question> questionList;
    Question activeQuestion;
    RecyclerViewAdaptor recyclerViewAdaptor;

    public Groups_and_QuestionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Groups_and_QuestionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Groups_and_QuestionsFragment newInstance(String param1, String param2) {
        Groups_and_QuestionsFragment fragment = new Groups_and_QuestionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups_and__questions, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        questionList = new ArrayList<Question>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("admin").child("questions");
        groupReff = FirebaseDatabase.getInstance().getReference().child("admin").child("groups");

        CreateGroup_ET = view.findViewById(R.id.CreateGroup_ET);
        CreateGroup_BT = view.findViewById(R.id.CreateGroup_BT);

        getGroups();
        listQuestions();

        CreateGroup_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupName = CreateGroup_ET.getText().toString();
                int random = new Random().nextInt(61) + 20;

                group = new Group(GroupName,random);

                mDatabase.child("admin").child("groups").child(GroupName).setValue(group);
            }
        });

        Question_ET = view.findViewById(R.id.Question_ET);
        AddQuestion_BT = view.findViewById(R.id.AddQuestion_BT);

        AddQuestion_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Question_text = Question_ET.getText().toString();

                question = new Question(Question_text,false);

                mDatabase.child("admin").child("questions").child(Question_text).setValue(question);


            }
        });



        return view;
    }

    public void listQuestions(){

        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionList.clear();
                for(DataSnapshot iterator : dataSnapshot.getChildren())
                {
                    Question q = iterator.getValue(Question.class);
                    questionList.add(q);

                    if(q.getState() == true)
                    {
                        Log.d("uzenet", "onDataChange: ");
                        group.setQuestion(q);
                        refreshGroup();
                    }
                }
                recyclerViewAdaptor = new RecyclerViewAdaptor(getContext(),questionList);
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                recyclerView.setAdapter(recyclerViewAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void refreshGroup(){
        mDatabase.child("admin").child("groups").child(GroupName).setValue(group);
    }

    public void getGroups(){

        groupReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot iterator : dataSnapshot.getChildren())
                {
                    Group g = iterator.getValue(Group.class);
                    GroupName = g.getName();
                    group = g;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}
