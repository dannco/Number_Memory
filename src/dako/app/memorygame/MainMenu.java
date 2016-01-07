package dako.app.memorygame;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;


public class MainMenu extends Activity {
    private static final int FONT_SIZE = 20;
    private static final int SHOW_SEQUENCE = 1;
    private static final int TEST_PLAYER = 2;
    private static final int INFO_PAGE = 3;
    private static final int DUR_OFFSET = 0;
    private static final int SEQ_OFFSET = 0;
    
    private static int len = 4;
    private static int dur = 4; 
    private static int[] seq;
    
    final static int bgColor =   0xFF000000;
    final static int textColor = 0xFFFCFD59;
    
    private Random rng = new Random();  
    
    static Score score;

    private NumberPicker seqLength;
    private NumberPicker durLength; 
    
    TextView scoreV;
    
    @SuppressLint("NewApi")
    public void onCreate(Bundle bndl) {
        super.onCreate(bndl);
        if (score == null) score = new Score();
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(10, 30, 10, 30);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(params);
        
        LinearLayout lengthSpan1 = new LinearLayout(this);
        lengthSpan1.setGravity(Gravity.CENTER_HORIZONTAL);
        lengthSpan1.setPadding(10, 10, 10, 10);     
        
        LinearLayout col1 = new LinearLayout(this);
        col1.setOrientation(LinearLayout.VERTICAL);
        col1.setPadding(0, 0, 25, 0);
        TextView a1 = new TextView(this);
        a1.setText("Digits:");
        col1.addView(a1);
        
        seqLength = new NumberPicker(this);     
        seqLength.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        seqLength.setMaxValue(18);
        seqLength.setMinValue(3);
        seqLength.setValue(len);                
        col1.addView(seqLength);
                
        LinearLayout col2 = new LinearLayout(this);
        col2.setOrientation(LinearLayout.VERTICAL);
        col2.setPadding(25,0,0,0);
        TextView a2 = new TextView(this);
        a2.setText("Interval in seconds");
        col2.addView(a2);
        
        durLength = new NumberPicker(this);
        durLength.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        durLength.setMinValue(1);
        durLength.setMaxValue(20);
        durLength.setValue(dur);
        String[] vals = new String[20];
        for (int i = 0; i < vals.length; i++) {
            vals[i] = ""+(i+1)/10.0;
        }
        durLength.setDisplayedValues(vals);
        col2.addView(durLength);
        
        lengthSpan1.addView(col1);
        lengthSpan1.addView(col2);
        ll.addView(lengthSpan1);
        
        LinearLayout ls1 = new LinearLayout(this);
        ls1.setPadding(10, 10, 10, 10);     
        TextView scoreText = new TextView(this);
        scoreText.setTextSize(FONT_SIZE);
        scoreText.setPadding(0, 50, 0, 0);
        scoreText.setText("Points: ");
        scoreV = new TextView(this);
        scoreV.setTextSize(FONT_SIZE);
        scoreV.setTextColor(Color.BLUE);
        ls1.addView(scoreText);
        ls1.addView(scoreV);
        ls1.setOnClickListener(new View.OnClickListener() {         
            @Override
            public void onClick(View v) {
                showScoreBreakdown();
            }
        });
        ll.addView(ls1);
        
        updateStats();
        
        Button startGame = new Button(this);
        startGame.setText("Start");
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
        
        RelativeLayout rl = new RelativeLayout(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        rl.addView(ll, lp);

        Button instructions = new Button(this);
        instructions.setText("Info");
        instructions.setOnClickListener(new View.OnClickListener() {            
            @Override
            public void onClick(View v) {
                showInfo();
            }
        });
        lp = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rl.addView(instructions,lp);

        lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rl.addView(startGame, lp);      
        setContentView(rl);
    }
    
    protected void showScoreBreakdown() {
        TextView view = new TextView(this);
        view.setPadding(20, 20, 20, 20);
        int r = score.getRounds();
        view.setText(
                 "You have earned "+score.getScore()
                +" points over "+r+(r==1?" round.":" rounds.")
                +"\n\nBest round: "+score.getBestRound());
        new AlertDialog.Builder(this)
        .setTitle("Score breakdown")
        .setView(view)
        .setNeutralButton("OK", null)
        .show();        
    }

    protected int[] getSequence(int length) {
        int[] s = new int[length];
        for (int i = 0; i < length; i++) {
            s[i] = Math.abs(rng.nextInt()%10);
        }
        return s;
    }
    
    @SuppressLint("NewApi")
    protected void newGame() {
        seq = getSequence(seqLength.getValue()+SEQ_OFFSET);
        dur = durLength.getValue();
        len = seqLength.getValue();

        Intent inte = new Intent(MainMenu.this, NumberSequence.class);
        inte.putExtra("Length",len+SEQ_OFFSET);
        inte.putExtra("Duration",(dur+DUR_OFFSET)*100);
        inte.putExtra("Sequence", seq);
        startActivityForResult(inte, SHOW_SEQUENCE);
    }
    protected void showInfo() {
        Intent inte = new Intent(MainMenu.this, InfoPage.class);
        startActivityForResult(inte, INFO_PAGE);
    }
    
    protected void onActivityResult(int req, int res, Intent data) {
        if (req == SHOW_SEQUENCE) {         
             if (res == RESULT_OK) {
                 Intent inte = new Intent(MainMenu.this, ReciteSequence.class);          
                 inte.putExtra("Sequence",seq);
                 inte.putExtra("dur",(dur+DUR_OFFSET)*100);
                 startActivityForResult(inte,TEST_PLAYER);
             }
        } else if (req == TEST_PLAYER) {
            if (res == RESULT_OK) {
                score.addPoints(len, (dur+DUR_OFFSET)*100,
                    data.getIntExtra("points", 0),
                    data.getIntExtra("rate",0));
            }
            updateStats();          
        }
    }

    protected void updateStats() {      
        scoreV.setText(""+score.getScore());
    }
    
    private class Score {
        private int rounds;
        private int score;
        private int highest;
        private String bestRound;
        
        public Score() {
            rounds = 0;
            score = 0;
            highest = 0;
            bestRound = "N/A";
        }
        
        public int getRounds() {
            return rounds;
        }
        public String getBestRound() {
            return bestRound;
        }
        
        public void addPoints(int len, int dur, int points, int rate) {
            Log.v("DUR", ""+dur);
            rounds++;
            score += points;
            if (points > highest) {
                highest = points;
                bestRound = points+" points earned for getting "
                +rate+"% correct on a "
                +(len+SEQ_OFFSET)+" number sequence with "
                +((double)dur/1000)+"s intervals.";
            }
        }

        public int getScore() {
            return score;
        }
    }
}
