package com.example.app31.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app31.R;
import com.example.app31.interfaces.OnItemClickListener;
import com.example.app31.models.Note;

public class HomeFragment extends Fragment implements OnItemClickListener {

    NavController navController;
    private boolean chekAddData = false;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private int position;
    private OnItemClickListener onItemClickListener;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTaskFragment();
            }
        });
        initList();
        setResultListener();
    }

    private void initList() {
        adapter = new NoteAdapter(this);
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onItemClick(String name, int position) {
        Toast.makeText(requireContext(), position + " : " + name, Toast.LENGTH_SHORT).show();

    }

    public void onLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Are sure to remove that?");
        builder.setMessage("Are you sure??");
        builder.setPositiveButton("Yes!", (dialog, which) -> {
            adapter.remove(position);
        });
        builder.setNegativeButton("No...", null);
        builder.show();
    }

    private void openTaskFragment() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_navigation_home_to_taskFragment);
    }
}





