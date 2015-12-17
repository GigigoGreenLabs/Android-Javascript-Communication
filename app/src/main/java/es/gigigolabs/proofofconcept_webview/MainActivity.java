package es.gigigolabs.proofofconcept_webview;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import es.gigigolabs.interfaces.AndroidNativeInterface;

public class MainActivity extends AppCompatActivity implements AndroidNativeInterface {

    WebView webView;
    Button button;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListeners();

        setupProofOfConcept();

    }


    private void findViews(){
        webView = (WebView) findViewById(R.id.webview);
        button = (Button) findViewById( R.id.button);

    }

    private void setListeners() {
        button.setOnClickListener( btnClickListener);
    }

    View.OnClickListener btnClickListener = new View.OnClickListener(){

        @Override public void onClick(View view) {
            a2w_showMessage( "Mensaje android mostrado en un alert Web!");
        }
    };

    private void setupProofOfConcept() {
        webView.setClickable( true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        /**
         * <b> Support Classes For WebView </b>
         */
        WebClientClass webViewClient = new WebClientClass();
        webView.setWebViewClient(webViewClient);
        WebChromeClient webChromeClient = new WebChromeClient();
        webView.setWebChromeClient(webChromeClient);

        /**
         * Now Added Java Interface Class
         */
        webView.addJavascriptInterface( this, "Android");



        webView.loadUrl("file:///android_asset/page.html");
    }

    @Override @JavascriptInterface
    public void w2a_showMessage(String mssg) {
        String msg = mssg;


        AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();


        alert.setTitle("AlertDialog nativo!");
        alert.setMessage(msg);
        alert.setButton("OK", new DialogInterface.OnClickListener() {

            @Override public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();

    }

    @Override
    @JavascriptInterface
    public void a2w_showMessage(String msg) {
        final String webUrl = "javascript:diplayJavaMsg('"+msg+"')";
        // Add this to avoid android.view.windowmanager$badtokenexception unable to add window
        if(!MainActivity.this.isFinishing())
            // loadurl on UI main thread
            MainActivity.this.runOnUiThread(new Runnable() {

                @Override public void run() {
                    webView.loadUrl(webUrl);
                }
            });
    }

    public class WebClientClass extends WebViewClient {
        ProgressDialog pd = null;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Please wait");
            pd.setMessage("Page is loading..");
            pd.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pd.dismiss();
        }
    }

    public class WebChromeClass extends WebChromeClient {
    }

}
