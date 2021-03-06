package com.android.mvp.view.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mvp.R;
import com.android.mvp.presenter.base.BasePresenter;
import com.android.mvp.view.activity.base.BaseViewPagerActivity;
import com.android.mvp.view.baseview.MyTitleView;
import com.android.mvp.view.fragment.TestFragmentDialog;
import com.android.mvp.view.fragment.TestGridFragment;
import com.android.mvp.view.fragment.TestListFragment;
import com.android.mvp.view.fragment.TestSettingsFragment;

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
        return R.layout.test_viewpager;
    }

    @Override
    protected String setTitleText() {
        return getResources().getString(R.string.app_name);
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
        mViewList.add(new TestFragmentDialog());
        mViewList.add(new TestListFragment());
        mViewList.add(new TestGridFragment());
        mViewList.add(new TestSettingsFragment());
        setPageNumber(0);
    }

    @Override
    protected View setTabView(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.base_viewpager_tab_item, container, false);
        TextView tabText = (TextView) view.findViewById(R.id.viewpager_tab_text);
        switch (position) {
            case 0:
                tabText.setText(getResources().getString(R.string.test_dialog));
                break;
            case 1:
                tabText.setText(getResources().getString(R.string.test_list));
                break;
            case 2:
                tabText.setText(getResources().getString(R.string.test_grid));
                break;
            case 3:
                tabText.setText(getResources().getString(R.string.test_settings));
                break;
        }
        return view;
    }

}
