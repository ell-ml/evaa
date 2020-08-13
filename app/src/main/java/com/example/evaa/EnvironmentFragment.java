package com.example.evaa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnvironmentFragment extends Fragment {

    private ProgressBar progressBarAnim;
    private ObjectAnimator progressAnimator;
    private Button btnLogger, btnHelp;
    private ImageView background;
    private Integer numLogged = 0;
    private ArrayList<List<Integer>> backgrounds;
    private ImageView ivCongrats;
    private Integer currentBackground;
    private Integer percentProgress = 0;

    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_environment, container, false);

        initializeProgressBar(rootView);
        btnLogger = rootView.findViewById(R.id.btnLogger);
        btnHelp = rootView.findViewById(R.id.btnHelp);
        background = rootView.findViewById(R.id.background);
        ivCongrats = rootView.findViewById(R.id.ivCongrats);

        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        Cursor data = databaseHelper.getListContents();

        while (data.moveToNext()) {
            numLogged++;
        }

        constructBackground();
        setBackground(rootView);
        setProgressBar();
        initializeProgressBar(rootView);

//        progressAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator anim) {
//                super.onAnimationEnd(anim);
//                Toasty.success(getActivity(), "Good Karma", Toast.LENGTH_SHORT, true).show();
//                initializeProgressBar(rootView);
//            }
//        });

        btnLogger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List fragments = getActivity().getSupportFragmentManager().getFragments();
                Fragment mFragment = (Fragment) fragments.get(fragments.size() - 1);

                Fragment fragment = new LoggedListFragment();
                FragmentManager manager = mFragment.getFragmentManager();
                manager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, fragment).commit();
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
                builder.setMessage("Welcome to your environment! When you properly dispose of an item, log the item through Item Search. Click \"Track\" to see your list of items logged. Clean the entire screen to unlock a new environment!");
                final AlertDialog alert = builder.create();
                alert.show();

                TextView textView = (TextView) alert.findViewById(android.R.id.message);
                Typeface typeFace = ResourcesCompat.getFont(getActivity(), R.font.cgothic);
                textView.setTypeface(typeFace);
                textView.setPadding(25,30,25,30);
                textView.setTextSize(18);
            }
        });

        return rootView;
    }

    public void initializeProgressBar(View rootView) {
        progressBarAnim = rootView.findViewById(R.id.progressBar);
        progressAnimator = ObjectAnimator.ofInt(progressBarAnim, "progress", 0, 100);
    }

    public void constructBackground() {
        final List<Integer> e1 = Arrays.asList(R.drawable.ic_park_1artboard_1, 0);
        final List<Integer> e2 = Arrays.asList(R.drawable.ic_park_2artboard_1, 25);
        final List<Integer> e3 = Arrays.asList(R.drawable.ic_park_3artboard_1, 50);
        final List<Integer> e4 = Arrays.asList(R.drawable.ic_park_4artboard_1, 75);
        final List<Integer> e5 = Arrays.asList(R.drawable.ic_park_5artboard_1, 100);
        final List<Integer> e6 = Arrays.asList(R.drawable.ic_desert_1artboard_1, 0);
        final List<Integer> e7 = Arrays.asList(R.drawable.ic_desert_2artboard_1, 25);
        final List<Integer> e8 = Arrays.asList(R.drawable.ic_desert_3artboard_1, 50);
        final List<Integer> e9 = Arrays.asList(R.drawable.ic_desert_4artboard_1, 75);
        final List<Integer> e10 = Arrays.asList(R.drawable.ic_desert_5artboard_1, 100);
        final List<Integer> e11 = Arrays.asList(R.drawable.ic_beach_1artboard_1, 0);
        final List<Integer> e12 = Arrays.asList(R.drawable.ic_beach_2artboard_1, 25);
        final List<Integer> e13 = Arrays.asList(R.drawable.ic_beach_3artboard_1, 50);
        final List<Integer> e14 = Arrays.asList(R.drawable.ic_beach_4artboard_1, 75);
        final List<Integer> e15 = Arrays.asList(R.drawable.ic_beach_5artboard_1, 100);

        backgrounds = new ArrayList<List<Integer> >() {
            {
                add(e1);
                add(e2);
                add(e3);
                add(e4);
                add(e5);
                add(e6);
                add(e7);
                add(e8);
                add(e9);
                add(e10);
                add(e11);
                add(e12);
                add(e13);
                add(e14);
                add(e15);
            }
        };
    }

    public void setBackground(View rv) {
        currentBackground = backgrounds.get(numLogged%backgrounds.size()).get(0);
        rv.setBackgroundResource(currentBackground);
    }

    public void setProgressBar() {
        percentProgress = backgrounds.get(numLogged%backgrounds.size()).get(1);
        progressBarAnim.setProgress(percentProgress);
        if (percentProgress == 100) {
            ivCongrats.setVisibility(View.VISIBLE);
        } else {
            ivCongrats.setVisibility(View.GONE);
        }
    }

}