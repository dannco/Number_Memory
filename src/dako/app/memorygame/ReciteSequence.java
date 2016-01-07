package dako.app.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;


public class ReciteSequence extends Activity {
    final static int FONT_SIZE = 20;
    protected AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f );
    
    
    private boolean ok;
    private TextView tv;
    private TextView answer;
    private EditText et;
    private int[] seq;
    private Button butt;
    private int correct;
    private int dur;
    
    
    protected void onCreate(Bundle bndl) {
        super.onCreate(bndl);
        correct = 0;
        seq = getIntent().getIntArrayExtra("Sequence");
        dur = getIntent().getIntExtra("dur",500);
        fadeIn.setDuration(1000);
        fadeIn.setFillAfter(true);
        
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(10, 10, 10, 10);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(params);
        
        tv = new TextView(this);
        tv.setText("Enter the sequence.");
        tv.setGravity(Gravity.CENTER);
        ll.addView(tv);
        
        
        et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        et.setTextSize(FONT_SIZE);
        et.setGravity(Gravity.CENTER);
        
        ll.addView(et);
        
        answer = new TextView(this);
        answer.setGravity(Gravity.CENTER);
        answer.setPadding(0, 10, 0, 0);
        answer.setTextSize(FONT_SIZE);
        String placeholder = "";
        
        for (int i = 0; i < seq.length; i++) {
            placeholder += "? ";
        }
        answer.setText(placeholder);
        ll.addView(answer);
        
        butt = new Button(this);
        butt.setText("OK");
        butt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (et.getText().length() == seq.length) {
                    if (checkInput()) {
                        ok = true;
                    } else {
                        ok = false;
                    }
                    resetButton();
                }
            }
        });
        
        RelativeLayout rl = new RelativeLayout(this);
        rl.addView(ll);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);     
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);

        rl.addView(butt, lp);
        setContentView(rl);
    }
    
    private void resetButton() {
        butt.setText("Back");
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ok) {   
                    Intent inte = new Intent();
                    inte.putExtra("points", calcScore());
                    inte.putExtra("rate",getRate());
                    setResult(RESULT_OK,inte);
                }
                finish();
            }
        }); 
    }
    
    protected int getRate() {
        double r = ((double)correct)/seq.length;
        int rate = (int)(r*100);
         
        return rate;
    }

    protected int calcScore() {
        double score = seq.length*correct/((double)dur/1000);
        Log.v("SCORE",""+score);
        return (int)score;
    }

    private boolean checkInput() {
        String input = ""+et.getText();
        String output ="";
        for (int i = 0; i < seq.length; i++) {
            if ('0'+seq[i] == input.charAt(i)) {
                correct++;
                output += "<font color=\"green\">"
                        +seq[i]
                        + "</font>";
            } else {
                output += "<font color=\"red\">"
                        +seq[i]
                        + "</font>";
            }
            output +=" ";
        }
        answer.setText(Html.fromHtml(output));
        answer.startAnimation(fadeIn);
        answer.setVisibility(View.VISIBLE);
        return true;
    }
}
