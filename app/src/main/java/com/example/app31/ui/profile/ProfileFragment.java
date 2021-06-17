package com.example.app31.ui.profile;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Messenger;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.app31.Prefs;
import com.example.app31.R;


public class ProfileFragment extends Fragment {
    private ImageView imageFromGallery;
    private EditText name;
    private Button btnTell;
    private ImageView imageBProfile;
    private ActivityResultLauncher<String> content;
    private SharedPreferences sharedPreferences;
    private NavController navController;
    Prefs prefs;
    private String str;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prefs = new Prefs(requireContext());
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageFromGallery = view.findViewById(R.id.imageGallery);

        imageBProfile = view.findViewById(R.id.imageBProfile);
        btnTell = view.findViewById(R.id.btnTell);
        name = view.findViewById(R.id.nameProfile);
        if (prefs != null) {
            if (prefs.getAvatar() != null)
//                imageFromGallery.setImageURI(Uri.parse(prefs.getAvatar()));

            name.setText(prefs.getStringData());
        }
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prefs.saveString(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageBProfile.setOnClickListener(v -> {
            ProfileFragment.this.content.launch("image/*");
        });
        Bundle bundle = new Bundle();
        imageFromGallery.setOnClickListener(v -> {
            bundle.putString("avatarKey", str);
            goAvatarFragment(bundle);
        });

        content = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            prefs.saveImage(result.toString());
                            imageFromGallery.setImageURI(result);
                        }

                        str = result.toString();
                    }
                });

    }

    private void goAvatarFragment(Bundle bundle) {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_profileFragment_to_avatar1Fragment, bundle);
    }


}