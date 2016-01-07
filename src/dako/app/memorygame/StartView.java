package dako.app.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class StartView extends Activity{
    
    static final AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
    
    protected void onCreate(Bundle bndl) {
        super.onCreate(bndl);
        fadeIn.setDuration(1200);
        RelativeLayout rl = new RelativeLayout(this);
        rl.setBackgroundColor(Color.BLACK);
        TextView iv1 = new TextView(this);
        iv1.setGravity(Gravity.CENTER);
        iv1.setText("TAP SCREEN TO BEGIN");
        iv1.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        ImageView iv2 = new ImageView(this);
        rl.addView(iv2);
        rl.addView(iv1,lp);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        setContentView(rl);
    }
    
    private void startGame() {
        Intent inte = new Intent(StartView.this, MainMenu.class);
        startActivityForResult(inte,1);
    }
    protected void onActivityResult(int req, int res, Intent data) {
        finish();
    }
}
