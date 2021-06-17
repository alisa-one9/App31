package com.example.app31.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app31.App;
import com.example.app31.Prefs;
import com.example.app31.R;
import com.example.app31.interfaces.OnItemClickListener;
import com.example.app31.models.Note;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemClickListener {

    NavController navController;
    private boolean chekAddData = false;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private int position;
    private OnItemClickListener onItemClickListener;
    private Note note;


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter(HomeFragment.this);
        setHasOptionsMenu(true);
        ImputData();

    }

    private void ImputData() {
        List<Note> list = App.getAppDatabase().taskDao().getAll();
        adapter.setList(list);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.fab).setOnClickListener(v -> openTaskFragment());
        recyclerView.setAdapter(adapter);

        setResultListener();
        App.getAppDatabase().taskDao().getAllLive().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.addApdateList(notes);
            }
        });
    }


    private void setResultListener() {
        getParentFragmentManager().setFragmentResultListener("rk_task",
                getViewLifecycleOwner(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = (Note) result.getSerializable("keyModel");
                        if (note != null) {
                            adapter.addItem(note);
                        }
                    }
                });
    }


    private void openTaskFragment() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_navigation_home_to_taskFragment);
    }

    @Override
    public void onClick(Note note, int position) {
        Toast.makeText(requireContext(), position + " : " + note.getName(), Toast.LENGTH_SHORT).show();

    }

    public void onLongClick(Note note, int position) {
        this.note = note;
        this.position = position;

    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.popup_menu_for_home_fragment, menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sortForHomeFragment) {
            adapter.sortList((ArrayList<Note>) App.getAppDatabase().taskDao().sortAll());
            return true;
        } else if (item.getItemId() == R.id.clear_data) {
            Prefs prefs = new Prefs(requireContext());
            prefs.clear();
            App.getAppDatabase().taskDao().delete(note);
            return true;
        } else return super.onOptionsItemSelected(item);
    }
}





