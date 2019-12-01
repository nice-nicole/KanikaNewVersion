package com.example.kanikanavbar.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.kanikanavbar.MainActivity;
import com.example.kanikanavbar.R;
import com.example.kanikanavbar.View.SignupActivity;
import com.example.kanikanavbar.ui.home.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {
    private FirebaseAuth authentication;

    private HomeViewModel homeViewModel;
    private Button registerMech;
    ViewFlipper v_flipper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        authentication = FirebaseAuth.getInstance();

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        int images[] = {R.drawable.image4, R.drawable.image11, R.drawable.images12, R.drawable.image13};
        v_flipper = root.findViewById(R.id.v_flipper);
        registerMech= root.findViewById(R.id.registerM);
        for (int image : images) {
            flipperImages(image);
        }
        FirebaseUser adminUser = authentication.getCurrentUser();
        if (adminUser != null && adminUser.getEmail().equals("nicenicky2019@gmail.com")){
            registerMech.setVisibility(View.VISIBLE);

            registerMech.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(getContext(), SignupActivity.class));
                }
            });
        }
        return root;
    }

    public void flipperImages(int image){
        ImageView imageView= new ImageView(getActivity());
        imageView.setBackgroundResource(image);
        v_flipper.setAutoStart(true);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(getActivity(), android.R.anim.slide_out_right);

    }




}