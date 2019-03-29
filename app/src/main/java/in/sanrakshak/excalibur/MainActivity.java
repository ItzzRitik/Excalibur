package in.sanrakshak.excalibur;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        web=findViewById(R.id.web);
        WebSettings settings = web.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setAppCacheEnabled(false);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setUserAgentString("Mozilla/5.0 (Linux; Android "+Build.VERSION.RELEASE+"; "+Build.MODEL+" Build/"+Build.ID+") AppleWebKit/537.36 (KHTML, like Gecko) Chrome/ Mobile Safari/537.36");

        web.setWebChromeClient(new WebChromeClient());
        web.setScrollbarFadingEnabled(true);
        web.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url,String userAgent,String contentDisposition,String mimetype,long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }});
        web.setWebViewClient(new WebViewClient(){
            private Map<String, Boolean> loadedUrls = new HashMap<>();
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                Toast.makeText(MainActivity.this, "" + errorCode , Toast.LENGTH_LONG).show();
            }
            @Override public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){handler.proceed();}
            @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {}
            @Override public void onPageFinished(WebView view, String url){ }
            @Override public void onLoadResource(WebView view, String url) {}
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        //web.loadUrl("http://192.168.43.21:5000/dev/ttyACM0");
    }
}
