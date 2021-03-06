package appchoice.student.quiz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import appchoice.student.quiz.R;
import appchoice.student.quiz.adapter.ScoreAdapter;
import appchoice.student.quiz.model.Score;
import appchoice.student.quiz.utils.DBHelper;

public class ScoreboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        initWidgets(view);
        return view;
    }

    private RecyclerView recyclerView;
    private TextView tvStatus;
    private void initWidgets(View view){
        recyclerView = view.findViewById(R.id.recycler_view);
        tvStatus = view.findViewById(R.id.tv_status);
    }

    private DBHelper dbHelper;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dbHelper = new DBHelper(getActivity());
        initData();
    }

    private void initData(){
        ArrayList<Score> scores = dbHelper.scores(getActivity());
        if (scores.size() == 0){
            tvStatus.setVisibility(View.VISIBLE);
        } else {
            tvStatus.setVisibility(View.GONE);
            ScoreAdapter adapter = new ScoreAdapter(dbHelper.scores(getActivity()));
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }
    }
}
