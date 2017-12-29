package cn.whoisaa.raspberrypi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import cn.whoisaa.raspberrypi.R;
import cn.whoisaa.raspberrypi.adapter.ViewPagerAdapter;
import cn.whoisaa.raspberrypi.base.BaseActivity;
import cn.whoisaa.raspberrypi.fragment.PreviewFragment;

/**
 * @Description 预览
 * @Author AA
 * @DateTime 2017/12/26 上午11:26
 */
public class PreviewActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        mTabLayout = findViewById(R.id.tl_preview);
        mViewPager = findViewById(R.id.vp_preview);
        //在ViewPager中加入Fragment
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(PreviewFragment.newInstance(PreviewFragment.PREVIEW_IMAGE), "相片");
        mPagerAdapter.addFragment(PreviewFragment.newInstance(PreviewFragment.PREVIEW_VIDEO), "录像");
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
        //将TabLayout与ViewPager绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
