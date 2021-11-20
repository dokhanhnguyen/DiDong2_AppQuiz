package appchoice.student.quiz.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import appchoice.student.quiz.R;
import appchoice.student.quiz.activity.QuizActivity;
import appchoice.student.quiz.adapter.HistoryAdapter;
import appchoice.student.quiz.callback.OnRecyclerViewListener;
import appchoice.student.quiz.model.Exam;
import appchoice.student.quiz.model.History;
import appchoice.student.quiz.utils.DBAssetHelper;
import appchoice.student.quiz.utils.DBHelper;

public class HistoryFragment extends Fragment {

    public static HistoryFragment newInstance(int subjectID){
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt("SUBJECT", subjectID);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        initWidgets(view);
        return view;
    }

    private RecyclerView recyclerView;
    private TextView tvStatus;
    private ProgressBar progressBar;
    private void initWidgets(View view){
        recyclerView = view.findViewById(R.id.recycler_view);
        tvStatus = view.findViewById(R.id.tv_status);
        progressBar = view.findViewById(R.id.progressbar);
    }

    private DBHelper dbHelper;
    private DBAssetHelper dbAssetHelper;
    private int subjectID;
    private boolean isOneSubject;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dbHelper = new DBHelper(getActivity());
        dbAssetHelper = new DBAssetHelper(getActivity());
        if (getArguments() != null) {
            subjectID = getArguments().getInt("SUBJECT");
        }
        isOneSubject = subjectID != 0;
        initRecyclerView();
    }

    private HistoryAdapter adapter;
    private void initRecyclerView(){
        adapter = new HistoryAdapter(getActivity(), isOneSubject, new OnRecyclerViewListener() {
            @Override
            public void onItemChange(View view, int position) {


                Intent intent = new Intent(getActivity(), QuizActivity.class);
                Bundle bundle = new Bundle();
                Exam exam = exam(adapter.getItem(position));
                bundle.putString(QuizActivity.TYPE, adapter.getItem(position).getType());
                bundle.putParcelable(QuizActivity.EXAM, exam);
                bundle.putInt(QuizActivity.STATUS, 2);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.scale_in, R.anim.no_animation);
            }
        });
        recyclerView.setAdapter(adapter);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        loadDatabase();
    }

    private Exam exam(History history){
//        return new Exam(
//                history.getExamID(),
//                history.getSubjectID(),
//                0, 0, 1,
//                dbAssetHelper.exam(history.getExamID()).getName(),
//                history, null
//        );
        return new Exam(
                history.getExamID(),
                history.getSubjectID(),
                0, 0, 1,
                history.getName(),
                history, null
        );
    }

    private void loadDatabase(){
        ArrayList<History> histories = dbHelper.histories(subjectID);
        if (histories.size() == 0){
            tvStatus.setVisibility(View.VISIBLE);
        } else {
            tvStatus.setVisibility(View.GONE);
            adapter.addAll(histories);
        }
        progressBar.setVisibility(View.GONE);
    }
}
