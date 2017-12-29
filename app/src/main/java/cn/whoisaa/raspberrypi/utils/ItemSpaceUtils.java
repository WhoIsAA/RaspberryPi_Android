package cn.whoisaa.raspberrypi.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Description 布局间隔（如：网格布局
 * @Author AA
 * @DateTime 2017/12/26 下午3:48
 */
public class ItemSpaceUtils extends RecyclerView.ItemDecoration {

    private int space;

    public ItemSpaceUtils(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;

        // Add top margin only for the first item to avoid double space between items
//            if (parent.getChildPosition(view) == 0)
//                outRect.top = space;
    }
}
