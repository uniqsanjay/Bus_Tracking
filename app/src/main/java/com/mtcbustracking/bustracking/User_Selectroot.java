package com.mtcbustracking.bustracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User_Selectroot extends AppCompatActivity {

    EditText selct_root;
    Button search;
    RelativeLayout rootlayout;
    RecyclerView rootlist;
    ArrayList root, from, to;
    LinearLayoutManager linearLayoutManager;
    Custom_Rootlist custom_rootlist;
    DatabaseReference df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__selectroot);

        selct_root = findViewById(R.id.dest);
        search = findViewById(R.id.search);
        rootlayout = findViewById(R.id.rootlayout);
        rootlist = findViewById(R.id.user_root_list);

        root = new ArrayList();
        from = new ArrayList();
        to = new ArrayList();

        getRootList();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootlayout.setVisibility(View.VISIBLE);
            }
        });

    }

    public void getRootList(){

        df = FirebaseDatabase.getInstance().getReference();
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    root.add(snap.child("bus_no").getValue().toString());
                    from.add(snap.child("Src").getValue().toString());
                    to.add(snap.child("Dest").getValue().toString());
                    //Toast.makeText(User_Selectroot.this, root.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        linearLayoutManager = new LinearLayoutManager(User_Selectroot.this);
        rootlist.setLayoutManager(linearLayoutManager);
        custom_rootlist = new Custom_Rootlist(User_Selectroot.this, root, from, to);
        rootlist.setAdapter(custom_rootlist);
        custom_rootlist.notifyDataSetChanged();
    }
}
