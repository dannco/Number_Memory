package dako.app.memorygame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class InfoPage extends Activity {
    final static int fontSize = 20;
    
    final String info1 = "This application is intended to test your short-term memory"+
        " by having you recall a sequence of random digits displayed one by one over over"+
        " a short period of time.";
    final String info2 = "During game setup you can specify the length of the sequence,"+
        " and the duration that each digit will be shown.";
    final String info3 = "When the game starts, a randomly generated sequence of digits of"+
        " the specified length and duration will be shown to you. You will then be asked to"+
        " recall the sequence. You earn points based on your speed and amount of correct"+
        " digits in the sequence.";
        
    
    
    protected void onCreate(Bundle bndl) {
        super.onCreate(bndl);
        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        TextView tv1 = new TextView(this);
        tv1.setPadding(25, 20, 25, 20);
        tv1.setTextSize(fontSize);
        tv1.setText(info1+"\n"+info2+"\n"+info3);
        ll.addView(tv1);
        sv.addView(ll);
        setContentView(sv);
    }
}
