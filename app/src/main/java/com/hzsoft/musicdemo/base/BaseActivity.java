package com.hzsoft.musicdemo.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hzsoft.musicdemo.R;
import com.hzsoft.musicdemo.view.LoadingInitView;
import com.hzsoft.musicdemo.view.NoDataView;

/**
 * Describe:
 * <p></p>
 *
 * @author zhouhuan
 * @Date 2020/11/20
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static String TAG = BaseActivity.class.getSimpleName();
    public Context mContext;

    protected TextView mTxtTitle;
    protected TextView tvToolbarRight;
    protected ImageView ivToolbarRight;
    protected Toolbar mToolbar;

    protected NoDataView mNoDataView;
    protected LoadingInitView mLoadingInitView;

    private ViewStub mViewStubToolbar;
    private ViewStub mViewStubContent;
    private ViewStub mViewStubInitLoading;
    private ViewStub mViewStubNoData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_root);
        initCommonView();
        initView();
        initListener();
        initData();
    }

    protected void initCommonView() {
        mContext = this;

        mViewStubToolbar = findViewById(R.id.view_stub_toolbar);
        mViewStubContent = findViewById(R.id.view_stub_content);
        mViewStubInitLoading = findViewById(R.id.view_stub_init_loading);
        mViewStubNoData = findViewById(R.id.view_stub_nodata);

        if (enableToolbar()) {
            mViewStubToolbar.setLayoutResource(onBindToolbarLayout());
            View view = mViewStubToolbar.inflate();
            initToolbar(view);
        }
        mViewStubContent.setLayoutResource(onBindLayout());
        mViewStubContent.inflate();
    }

    protected void initToolbar(View view) {
        mToolbar = view.findViewById(R.id.toolbar_root);
        mTxtTitle = view.findViewById(R.id.toolbar_title);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            //??????????????????
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (enableToolBarLeft()) {
                //????????????????????????NavigationIcon.????????????
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                //??????NavigationIcon???icon.?????????Drawable ,????????????ResId
                mToolbar.setNavigationIcon(getToolBarLeftIcon());
                mToolbar.setNavigationOnClickListener(v -> onBackPressed());
            }
            //???????????????????????????????????????????????????????????????
            if (tvToolbarRight != null && !TextUtils.isEmpty(getToolBarRightTxt())) {
                tvToolbarRight.setText(getToolBarRightTxt());
                tvToolbarRight.setVisibility(View.VISIBLE);
                tvToolbarRight.setOnClickListener(getToolBarRightTxtClick());
            }
            //?????????????????????????????? ??????0?????????????????????
            if (ivToolbarRight != null && getToolBarRightImg() != 0) {
                ivToolbarRight.setImageResource(getToolBarRightImg());
                ivToolbarRight.setVisibility(View.VISIBLE);
                ivToolbarRight.setOnClickListener(getToolBarRightImgClick());
            }

        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (mTxtTitle != null && !TextUtils.isEmpty(title)) {
            mTxtTitle.setText(title);
        }
        //????????????????????????title
        String tootBarTitle = getTootBarTitle();
        if (mTxtTitle != null && !TextUtils.isEmpty(tootBarTitle)) {
            mTxtTitle.setText(tootBarTitle);
        }
    }

    public String getTootBarTitle() {
        return "";
    }

    /**
     * ???????????????????????????????????????Drawable ,????????????ResId
     * ???????????? enableToolBarLeft ????????? true ????????????
     *
     * @return
     */
    public int getToolBarLeftIcon() {
        return R.drawable.ic_white_black_45dp;
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public boolean enableToolBarLeft() {
        return false;
    }

    /**
     * ??????????????????????????????
     *
     * @return
     */
    public String getToolBarRightTxt() {
        return "";
    }

    /**
     * ???????????????????????? Icon
     *
     * @return int resId ??????
     */
    public int getToolBarRightImg() {
        return 0;
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    public View.OnClickListener getToolBarRightTxtClick() {
        return null;
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    public View.OnClickListener getToolBarRightImgClick() {
        return null;
    }


    /**
     * ?????????????????????
     *
     * @return
     */
    public boolean enableToolbar() {
        return true;
    }


    /**
     * ???????????????
     *
     * @return
     */
    public int onBindToolbarLayout() {
        return R.layout.common_toolbar;
    }

    /**
     * ????????????
     *
     * @return
     */
    public abstract int onBindLayout();

    /**
     * ???????????????????????????????????????
     */
    public abstract void initView();

    /**
     * ???????????????
     */
    public abstract void initData();

    /**
     * ???????????????
     */
    public void initListener() {
    }

    public void finishActivity() {
        finish();
    }

    /**
     * toast
     *
     * @param msg msg
     */
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
    }


    /**
     * ??????????????????
     */
    public void showNoDataView() {
        showNoDataView(true);

    }

    /**
     * ???????????????????????????????????????
     */
    public void showNoDataView(int resid) {
        showNoDataView(true, resid);
    }

    /**
     * ??????????????????
     */
    public void hideNoDataView() {
        showNoDataView(false);
    }


    /**
     * ????????????
     */
    public void showInitLoadView() {
        showInitLoadView(true);
    }

    /**
     * ????????????
     */
    public void hideInitLoadView() {
        showInitLoadView(false);
    }

    /**
     * ????????????????????????
     *
     * @param show
     */
    public void showNoDataView(boolean show) {
        if (mNoDataView == null) {
            View view = mViewStubNoData.inflate();
            mNoDataView = view.findViewById(R.id.view_no_data);
        }
        mNoDataView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showNoDataView(Boolean show, int resid) {
        showNoDataView(show);
        if (show) {
            mNoDataView.setNoDataView(resid);
        }
    }


    /**
     * ??????????????????????????????
     *
     * @param show
     */
    public void showInitLoadView(boolean show) {
        if (mLoadingInitView == null) {
            View view = mViewStubInitLoading.inflate();
            mLoadingInitView = view.findViewById(R.id.view_init_loading);
        }
        mLoadingInitView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoadingInitView.loading(show);
    }

}
