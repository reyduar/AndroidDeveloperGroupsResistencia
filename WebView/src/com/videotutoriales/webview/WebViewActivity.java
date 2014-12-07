package com.videotutoriales.webview;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
        
        WebView wv = (WebView) findViewById(R.id.webview1);          
       // wv.loadUrl("file:///android_asset/Index.html");        
        
        /*
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String html = "<H1>Una simple página html</H1><body>" +
            "<p>Este es un ejemplo de código html creado en Android</p>"; 
        wv.loadDataWithBaseURL("", html, mimeType, encoding, "");
        */        
        
       
        wv.setWebViewClient(new Callback());
        WebSettings webSettings = wv.getSettings();//Para configuar el zoom de la web
        webSettings.setBuiltInZoomControls(true);//Para 
        //-- Para cargar una contenido web tenemos que cargar el metodo loadUrl
       // wv.loadUrl("http://www.illasaron.com/html/sites/default/files/imagecache/lead-image-full/0utKast-51483254.png");
                        
       wv.loadUrl("http://www.elpais.com");        
    }
    
    private class Callback extends WebViewClient {
    	@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return(false);
		}
	}	
}