package com.teaching.upbringing.modular.webview;

import android.Manifest;
import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.lypeer.fcpermission.FcPermissions;
import com.outsourcing.library.mvp.observer.NextObserver;
import com.outsourcing.library.utils.NotificationUtils;
import com.outsourcing.library.utils.StringUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.teaching.upbringing.R;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.PhoneUtil;
import com.teaching.upbringing.utils.ToastUtil;
import com.teaching.upbringing.widget.ConfigurableDialog;
import com.teaching.upbringing.widget.LoadingDialog;
import com.teaching.upbringing.widget.TipsDialog;
import com.teaching.upbringing.widget.share.SaveImageSeedModule;
import com.teaching.upbringing.widget.share.SeedEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author ChenHh
 * @time 2019/10/25 10:41
 * @des ${TODO}
 **/
public class WebViewActivity extends BaseMVPActivity<WebViewContract.Ipresenter> implements WebViewContract.IView {

    @BindView(R.id.webView_histroyImgCheck)
    WebView webView;

    @BindView(R.id.actionbar_title)
    TextView titleText;

    @BindView(R.id.actionbar_tab_layout)
    TabLayout actionBarTabLayout;

    @BindView(R.id.actionbar_confirm)
    TextView actionbarConfirm;//下载按钮

    @BindView(R.id.relativeLayout_HttpError)
    RelativeLayout relativeLayout_HttpError;

    @BindView(R.id.linearLayout_WebView)
    LinearLayout linearLayout_WebView;

    @BindView(R.id.web_progress)
    ProgressBar mProgressBar;// 加载网页显示进度条
    @BindView(R.id.actionbar_back)
    TextView actionbarBack;
    @BindView(R.id.custom_actionbar_title)
    RelativeLayout customActionbarTitle;
    @BindView(R.id.imageView_HttpError)
    ImageView imageViewHttpError;


    private boolean isTitleStrByLable = false;

    /**
     * 准备要进行下载的文件url，由于要先检查权限，所以把url缓存在变量中
     */
    private String downloadUrl;

    /**
     * 下载文件的动作，长按保存图片或下载文件
     */
    @DownActionType
    private int downActionType;
    private int mType;
//    private DecouplingShareDialog mShareDialog;
//    private DecouplingMoreShareDialog mMoreShareDialog;
    private ViewStub mViewStub;
    private String mTitle;
    private String mUrl;
    private Parcelable mEntity;
    private Bitmap mBitmap;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            DownActionType.DownImg,
            DownActionType.DownFile
    })
    private @interface DownActionType {
        int DownImg = 0x1;
        int DownFile = 0x2;
    }

    /**
     * 等待框，下载文件时显示
     */
    private LoadingDialog loaddingDialog;

    @Override
    protected Integer getContentId() {
        return R.layout.activity_webview;
    }

    @Override
    protected WebViewContract.Ipresenter initPresenter() {
        return new WebViewPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {//截屏兼容
            WebView.enableSlowWholeDocumentDraw();
        }
        super.onCreate(savedInstanceState);
        hideHeadBar();
        initListener();
    }

    @Override
    protected void init() {
        initParame(webView);
        getPresenter().loadPage(getIntent());
    }

    protected void initListener() {
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //忽略证书错误，继续执行网络访问
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.e("访问异常404", "3");
                view.stopLoading();
                linearLayout_WebView.setVisibility(View.GONE);
                relativeLayout_HttpError.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.GONE);
                }
                super.onPageFinished(view, url);
                getPresenter().onLoadFinish();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("------------>", "shouldOverrideUrlLoading");
                if (url.startsWith("tel:")) {
                    String mobile = url.substring(url.lastIndexOf("/") + 1);
                    PhoneUtil.call(WebViewActivity.this, mobile);
                    return true;
                }
                view.loadUrl(url);
                hideBtns();
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (isTitleStrByLable) {
                    titleText.setText(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mProgressBar != null) {
                    Log.e("加载网页进度", newProgress + "");
                    mProgressBar.setProgress(newProgress);
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                if (newProgress == 0) {
                    hideBtns();
                }
                if (newProgress == 100) {
                    boolean contains = false;
                    if (webView!=null&&!StringUtils.isBlank(webView.getUrl())){
                        contains = webView.getUrl().contains("share_poster.aspx");//分享有礼需要显示底部分享按钮
                    }
                    if (contains) {
                        showBtns();
                    } else {
                        hideBtns();
                    }
                }
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
            }

            @Override
            public View getVideoLoadingProgressView() {
                return null;
            }

        });
    }

    @Override
    public void initVisiable() {
        if (webView == null) {
            return;
        }
        if (webView.canGoBack()) {
            actionbarConfirm.setVisibility(View.VISIBLE);
        } else {
            actionbarConfirm.setVisibility(View.GONE);
        }
    }

    private static Bitmap captureWebView(WebView webView) {
        //        webView.setDrawingCacheEnabled(true);
        //        webView.buildDrawingCache();
        Picture snapShot = webView.capturePicture();
        //        Bitmap bitmap = webView.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(snapShot.getWidth(),
                snapShot.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        snapShot.draw(canvas);
        return bitmap;
    }

    @Override
    public void setTitle(String title) {
        titleText.setText(title);
    }

    @Override
    public void setActionBarRight(String txt, int iconRes, int visible) {

    }

    @Override
    public void loadUrl(String url, Account account) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("?token=");
        //        sb.append(UserInfo.getToken());
        //        Log.e("请求获取的url",sb.toString());
        webView.loadUrl(url);
    }

    @Override
    public void showErrorPage() {
        linearLayout_WebView.setVisibility(View.GONE);
        relativeLayout_HttpError.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadCommonUrl(String url) {
        Log.e("加载的url  ", url + "");
        webView.loadUrl(url);
    }

    @Override
    public void setZoomEnable(boolean isEnable) {
        WebSettings settings = webView.getSettings();
        settings.setDisplayZoomControls(isEnable);
        settings.setSupportZoom(isEnable);
        settings.setBuiltInZoomControls(isEnable);
        webView.setHorizontalScrollBarEnabled(isEnable);
        webView.setVerticalScrollBarEnabled(isEnable);
    }

    @Override
    public void setTitleBarRightBtn(boolean isShow, String text, int imgId) {
        if (isShow) {
            actionbarConfirm.setVisibility(View.VISIBLE);
            if (imgId != -1) {
                Drawable drawable = getResources().getDrawable(imgId);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                actionbarConfirm.setCompoundDrawables(null, null, drawable, null);
            } else {
                actionbarConfirm.setCompoundDrawables(null, null, null, null);
            }
            actionbarConfirm.setText(text);
        } else {
            actionbarConfirm.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPupowindow() {
        View view = getLayoutInflater().inflate(R.layout.popup_web_report, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchable(true);
        TextView tvPop = (TextView) view.findViewById(R.id.tv_pop_web_report);
        tvPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.clearHistory();
                webView.clearCache(true);
                getPresenter().actionBarConfigPopupProcess();
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(actionbarConfirm, -100, 0);
    }

    @Override
    public void initTabLayout(String... title) {
        int length = title.length;
        for (int i = 0; i < length; i++) {
            actionBarTabLayout.addTab(actionBarTabLayout.newTab().setText(title[i]));
        }
        actionBarTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                webView.clearHistory();
                webView.clearCache(true);
                getPresenter().onTabSelectorProcess(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void initActionBarTitle(int type) {
        switch (type) {
            case 0://举个例子，之后用静态变量代替
                titleText.setVisibility(View.GONE);
                actionBarTabLayout.setVisibility(View.VISIBLE);
                break;
            default:
                titleText.setVisibility(View.VISIBLE);
                actionBarTabLayout.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void saveEdtReportForWeb() {
        String save = "javascript:save()";
        webView.loadUrl(save);
    }

    @Override
    public void setTitleByLable(boolean isByNet) {
        isTitleStrByLable = isByNet;
    }

    @Override
    public void setOk() {
        setResult(RESULT_OK);
    }

    @Override
    public void setType(int type) {
        mType = type;
    }

    @Override
    public void showBtns() {
        mViewStub = findViewById(R.id.view_stub);
        boolean isInit = false;
        if (mViewStub != null) {
            mViewStub.inflate();
        } else {
            isInit = true;
        }
        View group = findViewById(R.id.ll_btns);
        if (isInit) {
            if (group != null) {
                group.setVisibility(View.VISIBLE);
            }
            return;
        }
        View tvLeft = group.findViewById(R.id.tv_left);
        View tvRight = group.findViewById(R.id.tv_right);
        //分享功能，后续添加
        //保存图片
        SaveImageSeedModule imageSeedModule = new SaveImageSeedModule();
        tvLeft.setOnClickListener(v -> {
            boolean b = imageSeedModule.work(WebViewActivity.this, new SeedEntity(captureWebView(webView), null, null, null));
            if (b) {
                getPresenter().applyShare();
            }
        });

    }

    private void hideBtns() {
        View group = findViewById(R.id.ll_btns);
        if (group != null) {
            group.setVisibility(View.GONE);
        }
    }

    @Override
    public void openHardware() {
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    @OnClick({R.id.actionbar_back, R.id.custom_actionbar_title, R.id.actionbar_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.actionbar_back:
                onBackPressed();
                break;
            case R.id.custom_actionbar_title:
                break;
            case R.id.actionbar_confirm:
                break;
        }
    }

    /**
     * 初始化控件参数
     */
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface", "JavascriptInterface"})
    private void initParame(final WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);//支持js
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setBuiltInZoomControls(true);//支持出现缩放工具
        settings.setSupportZoom(true);//支持缩放
        settings.setDisplayZoomControls(false);//是否显示缩放控件
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 排版适应屏幕
        settings.setUseWideViewPort(true);// 可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "MyJavaScriptInterface");
        webView.addJavascriptInterface(new AndroidInterface(this), "cardealer");
        settings.setAllowFileAccess(true);  //设置可以访问文件
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        settings.supportMultipleWindows();  //多窗口

        webView.setOnLongClickListener(v -> {
            final WebView.HitTestResult hitTestResult = webView.getHitTestResult();
            if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                    hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {

                new ConfigurableDialog(WebViewActivity.this)
                        .setTextModel(ConfigurableDialog.ONE_TXT)
                        .setTextFirst("保存图片")
                        .setBtnModel(ConfigurableDialog.ONE_BTN)
                        .setBtnName(new String[]{"取消，保存"})
                        .setBtnListener((ConfigurableDialog, view) -> {
                            ConfigurableDialog.dismiss();
                            saveImage(hitTestResult);
                        })
                        .setBtnListener((ConfigurableDialog, view) -> ConfigurableDialog.dismiss())
                        .show();
            }
            return false;
        });

        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            downloadUrl = url;
            downActionType = DownActionType.DownFile;
            // 下载文件，先检查权限
            FcPermissions.requestPermissions(WebViewActivity.this,
                    "系统需要访问网络权限，否则将无法下载文件。",
                    300, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        });
    }

    private void saveImage(WebView.HitTestResult hitTestResult) {
        downloadUrl = hitTestResult.getExtra();
        downActionType = DownActionType.DownImg;
        // 保存图片，先检查权限
        FcPermissions.requestPermissions(WebViewActivity.this,
                "系统需要访问网络权限，否则将无法下载文件。",
                300, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void saveImage(String url) {
        downloadUrl = url;
        downActionType = DownActionType.DownImg;
        // 保存图片，先检查权限
        FcPermissions.requestPermissions(WebViewActivity.this,
                "系统需要访问网络权限，否则将无法下载文件。",
                300, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public class MyJavaScriptInterface {
        /**
         * 被js调用的方法必须要加上此注解。
         */
        @JavascriptInterface
        public String processJson(String json) {
            Log.e("webView_json", json.toString());
            try {
                JSONObject jsonObject = new JSONObject(json);
                String jsoncode = jsonObject.getString("jsoncode");
                String data = jsonObject.getString("data");
                if (jsoncode.equals("1") && data.equals("goback")) {
                    WebViewActivity.this.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "OK";
        }
    }

    public class AndroidInterface {
        private FragmentActivity mContext;

        public AndroidInterface(FragmentActivity context) {
            mContext = context;
        }

        /**
         * 被js调用的方法必须要加上此注解。
         */
        /*@JavascriptInterface
        public void recordEvent(String eventStr) {
            statistics(eventStr);
        }*/

        @JavascriptInterface
        public void call(String phone) {
            new RxPermissions(mContext)
                    .request(Manifest.permission.CALL_PHONE)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new NextObserver<Boolean>() {
                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (aBoolean) {
                                PhoneUtil.call(mContext, phone);
                            } else {
                                TipsDialog dialog = new TipsDialog(mContext, mContext.getSupportFragmentManager());
                                dialog.setTips("提示", "拨打电话需要设置相关权限");
                                dialog.setLeftBtnText("取消");
                                dialog.setCancelable(true);
                                dialog.setRightBtnText("去设置");
                                dialog.setOnLeftConfirmListener(v -> dialog.dismiss());
                                dialog.setOnRightConfirmListener(v -> {
                                    NotificationUtils.toSetting(mContext);
                                    dialog.dismiss();
                                });
                                dialog.show();
                            }
                        }
                    });
        }

        @JavascriptInterface
        public void saveImage(String url) {
            WebViewActivity.this.saveImage(url);
        }
    }

    public static void goInto(Context activity, int type, String... url) {
        if (url.length < 1 || StringUtils.isEmpty(url[0])) {
            ToastUtil.showFault("数据异常，请稍后再试！");
            return;
        }
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("webtype", type);
        intent.putExtra("url", url[0]);
        if (url.length > 1) {
            intent.putExtra("url2", url[1]);
        }
        activity.startActivity(intent);
    }
}
