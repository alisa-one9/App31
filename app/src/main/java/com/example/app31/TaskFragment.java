package com.example.app31;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app31.models.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ThrowOnExtraProperties;

import java.util.Date;


public class TaskFragment extends Fragment {
    private EditText editText;
    private NavController navController;
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        String text = editText.getText().toString();
        if(text.isEmpty()) return;

        Note note = new Note(text, new Date());
        App.getAppDatabase().taskDao().insert(note);
        saveToFireStore(note);

        Bundle bundle = new Bundle();
        bundle.putSerializable("keyModel", note);
        getParentFragmentManager().setFragmentResult("rk_task", bundle);

    }

    private void saveToFireStore(Note note) {
        FirebaseFirestore.getInstance()
                .collection("task")
                .add(note)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> t) {
                        if (t.isSuccessful()) {
                            Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show();
                            close();
                        } else {
                            Toast.makeText(requireActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void close() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}