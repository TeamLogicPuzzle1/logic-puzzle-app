package com.cookandroid.test_ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment {

    public interface OnDateSetListener {
        void onDateSet(int year, int month, int day);
    }

    private OnDateSetListener listener;

    public void setOnDateSetListener(OnDateSetListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // 현재 날짜를 기본값으로 사용
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), (view, yearSelected, monthSelected, daySelected) -> {
            // 날짜 선택 시 리스너 호출
            if (listener != null) {
                listener.onDateSet(yearSelected, monthSelected + 1, daySelected); // month는 0부터 시작하므로 +1
            }
        }, year, month, day);
    }
}
