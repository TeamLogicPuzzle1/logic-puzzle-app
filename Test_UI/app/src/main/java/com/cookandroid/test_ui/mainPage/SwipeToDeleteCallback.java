package com.cookandroid.test_ui.mainPage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.test_ui.R;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private final ProductAdapter productAdapter;
    private final Drawable deleteIcon;
    private final ColorDrawable background;

    // 아이콘 크기 설정
    private final int iconSize = 140; // 원하는 아이콘 크기 (픽셀 단위)

    public SwipeToDeleteCallback(ProductAdapter productAdapter, Context context) {
        super(0, ItemTouchHelper.RIGHT);
        this.productAdapter = productAdapter;

        // deleteIcon과 background 초기화
        deleteIcon = ContextCompat.getDrawable(context, R.drawable.trash_delete);
        background = new ColorDrawable(Color.rgb(224, 224, 224));
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        productAdapter.removeItem(position);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int iconMargin = (itemView.getHeight() - iconSize) / 2; // 아이콘을 중앙에 위치시키는 마진
        int iconTop = itemView.getTop() + iconMargin;
        int iconBottom = iconTop + iconSize;

        // 배경 설정 (오른쪽으로 스와이프)
        if (dX > 0) {
            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX), itemView.getBottom());
            background.draw(c);

            // 아이콘 위치 및 크기 설정
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = iconLeft + iconSize;
            deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            deleteIcon.draw(c);
        }
    }
}
