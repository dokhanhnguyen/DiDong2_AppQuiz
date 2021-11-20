package appchoice.student.quiz.callback;

import appchoice.student.quiz.model.Exam;

public interface OnFirebaseListener {

    void onLoading(boolean isLoading);

    void showExam(Exam exam);

}
