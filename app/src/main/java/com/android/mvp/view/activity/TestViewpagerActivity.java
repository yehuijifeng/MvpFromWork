package com.android.mvp.view.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mvp.R;
import com.android.mvp.presenter.BasePresenter;
import com.android.mvp.view.activity.base.BaseViewPagerActivity;
import com.android.mvp.view.baseview.MyTitleView;
import com.android.mvp.view.fragment.TestFragmentOne;
import com.android.mvp.view.fragment.TestListFragment;

/**
 * Created by Luhao on 2016/7/21.
 */
public class TestViewpagerActivity extends BaseViewPagerActivity {

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_test_viewpager;
    }

    @Override
    protected String setTitleText() {
        return getResources().getString(R.string.test_viewpager);
    }

    @Override
    protected void initView() {
        super.initView();
        mTitleView.setTitleMode(MyTitleView.TitleMode.OPTIONS);
        mTitleView.setImageButtonOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLongToast("dddddddddddd");
            }
        });
        mViewList.add(new TestFragmentOne());
        mViewList.add(new TestListFragment());
        mViewList.add(new TestFragmentOne());
        mViewList.add(new TestFragmentOne());
        setPageNumber(0);
    }

    @Override
    protected View setTabView(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.base_viewpager_tab_item, container, false);
        TextView tabText = (TextView) view.findViewById(R.id.viewpager_tab_text);
        switch (position) {
            case 0:
                tabText.setText("遮罩框");
                break;
            case 1:
                tabText.setText("列表");
                break;
            case 2:
                tabText.setText("表格");
                break;
            case 3:
                tabText.setText("瀑布流");
                break;
        }
        return view;
    }

}
