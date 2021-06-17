package com.example.app31.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.example.app31.R;
import com.example.app31.interfaces.OnItemClickListener;
import com.example.app31.models.Note;
import com.example.app31.ui.home.HomeFragment;
import com.example.app31.ui.home.NoteAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements OnItemClickListener {
    private FirebaseFirestore firebaseFirestore;
    NoteAdapter adapter;
    private RecyclerView rvDashboard;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter(this);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDashboard = view.findViewById(R.id.recyclerViewDash);
        initList();
        firebaseFirestore = FirebaseFirestore.getInstance();
        getAllDocuments();
    }

    public void getAllDocuments() {
        firebaseFirestore.collection("task")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Note> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Note note = new Note(document.getData().get("name").toString());
                                note.setDocId(document.getId());
                                list.add(note);
                            }
                            adapter.setList(list);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void initList() {
        rvDashboard.setAdapter(adapter);
    }

    private void deleteFromFireStore(Note note, int position) {
        Log.e("TAG", "deleteFromFireStore: " + note.getDocId());
        firebaseFirestore.collection("task").document(note.getDocId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    adapter.remove(position);
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error deleting document", e);
                });

    }

    @Override
    public void onClick(Note note, int position) {

    }

    @Override
    public void onLongClick(Note note, int position) {
        deleteFromFireStore(note, position);
    }
}