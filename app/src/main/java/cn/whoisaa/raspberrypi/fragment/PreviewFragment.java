package cn.whoisaa.raspberrypi.fragment;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;

import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import cn.whoisaa.raspberrypi.R;
import cn.whoisaa.raspberrypi.base.BaseFragment;
import cn.whoisaa.raspberrypi.http.PreviewResponse;
import cn.whoisaa.raspberrypi.http.RspiApi;
import cn.whoisaa.raspberrypi.utils.DisplayUtils;
import cn.whoisaa.raspberrypi.utils.GlideUtils;
import cn.whoisaa.raspberrypi.utils.ItemSpaceUtils;
import cn.whoisaa.raspberrypi.utils.TimeUtils;

/**
 * @Description 预览页面fragment
 * @Author AA
 * @DateTime 2017/12/29 上午9:56
 */
public class PreviewFragment extends BaseFragment implements OnResponseListener<PreviewResponse> {

    public static final String PREVIEW_TYPE = "key_preview_type";
    public static final int PREVIEW_IMAGE = 1;
    public static final int PREVIEW_VIDEO = 2;

    private ViewStub mEmptyView;
    private RecyclerView mRecyclerView;
    private CommonAdapter<PreviewResponse.Data> mAdapter;


    public static PreviewFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(PREVIEW_TYPE, type);

        PreviewFragment fragment = new PreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_preview;
    }

    @Override
    protected void initData() {
        int type = getArguments().getInt(PREVIEW_TYPE);
        mEmptyView = findViewById(R.id.vs_empty_data);
        mRecyclerView = findViewById(R.id.rv_preview);
        mRecyclerView.addItemDecoration(new ItemSpaceUtils(DisplayUtils.dip2px(getContext(), 10)));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        RspiApi.getInstance().preview(RspiApi.REQ_PREVIEW, type, this);
    }

    @Override
    public void onStart(int what) {
        showLoading(getString(R.string.loading));
    }

    @Override
    public void onSucceed(int what, Response<PreviewResponse> response) {
        if(response != null && response.get() != null && !isEmptyList(response.get().getData())) {
            mAdapter = new CommonAdapter<PreviewResponse.Data>(getContext(), R.layout.item_preview, response.get().getData()) {
                @Override
                protected void convert(ViewHolder holder, PreviewResponse.Data data, int position) {
                    final int screenHeight = DisplayUtils.getScreenHeight(getContext());
                    final int itemHeight = screenHeight / 2 - DisplayUtils.dip2px(getContext(), 70);

                    CardView cardView = (CardView) holder.getConvertView();
                    cardView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));

                    ImageView imageView = holder.getView(R.id.iv_item_preview);
                    GlideUtils.getInstance().loadCornerImage(getContext(), imageView, data.getThumbnail());
                    holder.setText(R.id.tv_item_preview_name, data.getFilename());
                    holder.setVisible(R.id.tv_item_preview_time, data.isVideo());
                    if(data.isVideo()) {
                        holder.setText(R.id.tv_item_preview_time, TimeUtils.getTimeFromInt(data.getDuration()));
                    }
                }
            };
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailed(int what, Response<PreviewResponse> response) {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinish(int what) {
        hideLoading();
    }
}
