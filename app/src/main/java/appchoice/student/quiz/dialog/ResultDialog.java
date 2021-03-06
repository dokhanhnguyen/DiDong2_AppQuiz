package appchoice.student.quiz.dialog;

import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import appchoice.student.quiz.R;
import appchoice.student.quiz.base.BaseDialog;
import appchoice.student.quiz.model.Exam;
import appchoice.student.quiz.model.Question;
import appchoice.student.quiz.utils.QuizHelper;
import appchoice.student.quiz.utils.Units;

public class ResultDialog extends BaseDialog {

    public static ResultDialog newInstance(Exam exam, ArrayList<Question> questions){
        ResultDialog dialog = new ResultDialog();
        Bundle args = new Bundle();
        args.putParcelable("EXAM", exam);
        args.putParcelableArrayList("QUESTIONS", questions);
        dialog.setArguments(args);
        return dialog;
    }

    private Exam exam;
    private ArrayList<Question> questions;
    @Override
    protected void getData() {
        if (getArguments() != null) {
            exam = getArguments().getParcelable("EXAM");
            questions = getArguments().getParcelableArrayList("QUESTIONS");
        }
    }

    @Override
    protected int layoutRes() {
        return R.layout.dialog_result;
    }

    @Override
    protected double dialogWidth() {
        if (dialogSize().x <= Units.dpToPx(500)) {
            return 1.0;
        } else {
            return 0.6;
        }
    }

    @Override
    protected double dialogHeight() {
        return 0;
    }

    @Override
    protected int dialogGravity() {
        return Gravity.CENTER;
    }

    private TextView tvName;
    private TextView tvScore;
    private TextView tvResultInfo;
    @Override
    protected void initWidgets(View view) {
        tvName = view.findViewById(R.id.tv_name);
        tvScore = view.findViewById(R.id.tv_score);
        tvResultInfo = view.findViewById(R.id.tv_result_info);
        view.findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected void configView() {
        tvName.setText(exam.getName());
        tvScore.setText(QuizHelper.getStringScore(QuizHelper.getScore(questions)));
        long timePlay = exam.getLastHistory().getTimePlay();
        long timePerQues = timePlay/questions.size();
        String info = "&#9758; Tr??? l???i ????ng: " + QuizHelper.getAnsTrue(questions) + "/" + questions.size() +
                "<br>&#9758; Th???i gian l??m b??i: " + QuizHelper.getTimeResult(timePlay) +
                "<br>&#9758; Trung b??nh: " + QuizHelper.getTimeResult(timePerQues) + "/m???i c??u";
        tvResultInfo.setText(Html.fromHtml(info));
    }
}
