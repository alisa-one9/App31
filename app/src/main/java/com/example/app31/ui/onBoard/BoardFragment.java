        package com.example.app31.ui.onBoard;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.app31.Prefs;
import com.example.app31.R;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;


public class BoardFragment extends Fragment {
    ViewPager2 viewPager2;
    SpringDotsIndicator springDotsIndicator;
    Button btnSkip;
    Button btnStart;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         viewPager2=view.findViewById(R.id.viewPager);
         springDotsIndicator =view.findViewById(R.id.dots);
         btnSkip =view.findViewById(R.id.btnSkip);
         btnStart = view.findViewById(R.id.btnStart);

        BoardAdapter adapter=new BoardAdapter();
        viewPager2.setAdapter(adapter);
        springDotsIndicator.setViewPager2(viewPager2);
        btnSkip.setOnClickListener(v -> close());


        requireActivity().getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });
    }

    private void close() {
        new Prefs(requireContext()).saveBoardState();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }

}