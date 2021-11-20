package appchoice.student.quiz.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import appchoice.student.quiz.R;
import appchoice.student.quiz.adapter.FacultyAdapter;
import appchoice.student.quiz.adapter.YearAdapter;
import appchoice.student.quiz.base.BaseDialog;
import appchoice.student.quiz.utils.DBAssetHelper;
import appchoice.student.quiz.utils.Pref;
import appchoice.student.quiz.views.AutoRecyclerView;

public class ToggleDialog extends BaseDialog {

    private Pref pref;
    private DBAssetHelper dbAssetHelper;
    @Override
    protected void getData() {
        pref = new Pref(getActivity());
        dbAssetHelper = new DBAssetHelper(getActivity());
    }

    @Override
    public boolean isCancelable() {
        return false;
    }

    @Override
    protected int layoutRes() {
        return R.layout.dialog_toggle;
    }

    @Override
    protected double dialogWidth() {
        return 1;
    }

    @Override
    protected double dialogHeight() {
        return 0;
    }

    @Override
    protected int dialogGravity() {
        return Gravity.BOTTOM;
    }

    private RelativeLayout layoutDialog;
    private AutoRecyclerView recyclerViewYear;
    private RecyclerView recyclerViewFaculty;
    private ImageButton btConfirm;
    @Override
    protected void initWidgets(View view) {
        layoutDialog = view.findViewById(R.id.layout_dialog);
        recyclerViewYear = view.findViewById(R.id.recycler_view_year);
        recyclerViewFaculty = view.findViewById(R.id.recycler_view_faculty);
        btConfirm = view.findViewById(R.id.bt_confirm);
    }

    @Override
    protected void configView() {
        layoutDialog.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up));
        initRecyclerViewYear();
        initRecyclerViewFaculty();
        initConfirm();
    }

    private YearAdapter yearAdapter;
    private void initRecyclerViewYear(){
        yearAdapter = new YearAdapter(pref.getYear(), dbAssetHelper.years());
        recyclerViewYear.setHasFixedSize(true);
        recyclerViewYear.setAdapter(yearAdapter);
        ViewCompat.setNestedScrollingEnabled(recyclerViewYear, false);
    }

    private FacultyAdapter facultyAdapter;
    private void initRecyclerViewFaculty(){
        facultyAdapter = new FacultyAdapter(pref.getFaculty(), dbAssetHelper.faculties());
        recyclerViewFaculty.setHasFixedSize(true);
        recyclerViewFaculty.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFaculty.setAdapter(facultyAdapter);
        ViewCompat.setNestedScrollingEnabled(recyclerViewFaculty, false);
    }

    private void initConfirm(){
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(hashMap());
                dismiss();
            }
        });
    }

    private HashMap<String, String> hashMap(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("filter", "1");
        hashMap.put("year", yearAdapter.getCurrentYear());
        hashMap.put("faculty", facultyAdapter.getCurrentFaculty());
        return hashMap;
    }
}
