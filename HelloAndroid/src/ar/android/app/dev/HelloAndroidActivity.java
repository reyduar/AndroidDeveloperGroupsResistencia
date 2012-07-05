package ar.android.app.dev;
/**
 * @author Ariel Duarte
 * Date: 17/06/2012
 */
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HelloAndroidActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
     /**
      * Create labels with java using TextView android.widget
      */
//      TextView tvAbout = new TextView(this); 
//      tvAbout.setText("Developer: Ariel Duarte \nemail: iariel.javauser@gmail.com \nAndroid Development ©2012");  
//      setContentView(tvAbout);
      
    }
}