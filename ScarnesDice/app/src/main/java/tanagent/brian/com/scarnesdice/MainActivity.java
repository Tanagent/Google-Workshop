package tanagent.brian.com.scarnesdice;

import android.media.Image;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Integer[] FACE_OF_DICE = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
                                R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    private int user_overallScore = 0;
    private int user_turnScore = 0;
    private int comp_overallScore = 0;
    private int comp_turnScore = 0;

    private ImageView diceImg;
    private TextView userScore;
    private TextView compScore;
    private TextView userOverallScore;
    private TextView compOverallScore;
    private Button rollBtn;
    private Button holdBtn;
    private Button resetBtn;

    private Random rand = new Random();
    Handler timerHandler = new Handler();
    Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollBtn = (Button) findViewById(R.id.btn_roll);
        holdBtn = (Button) findViewById(R.id.btn_hold);
        resetBtn = (Button) findViewById(R.id.btn_reset);
        diceImg = (ImageView) findViewById(R.id.img_dice);
        userScore = (TextView) findViewById(R.id.user_score);
        compScore = (TextView) findViewById(R.id.comp_score);
        userOverallScore = (TextView) findViewById(R.id.user_overall_score);
        compOverallScore = (TextView) findViewById(R.id.comp_overall_score);

        userTurn();
    }

    public void userTurn(){

        rollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dieRoll = rollDie();

                Log.i("User Die Roll", String.valueOf(Arrays.asList(FACE_OF_DICE).indexOf(dieRoll) + 1));

                if(dieRoll != FACE_OF_DICE[0]){
                    user_turnScore += Arrays.asList(FACE_OF_DICE).indexOf(dieRoll) + 1;
                } else {
                    user_turnScore = 0;
                    computerTurn();
                }

                userScore.setText(user_turnScore + " ");
            }
        });

        holdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_overallScore += user_turnScore;
                user_turnScore = 0;
                userOverallScore.setText(user_overallScore + " ");
                Log.i("User Overall Score", String.valueOf(user_overallScore));
                computerTurn();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_overallScore = 0;
                user_turnScore = 0;
                comp_overallScore = 0;
                comp_turnScore = 0;

                userScore.setText(0 + " ");
                compScore.setText(0 + " ");
                compOverallScore.setText(0 + " ");
                userOverallScore.setText(0 + " ");
            }
        });
    }

    private long startTime = 0;

    public void computerTurn(){

        comp_turnScore = 0;
        
        timerRunnable = new Runnable() {

            int count = 0;

            @Override
            public void run() {
                rollBtn.setEnabled(false);
                holdBtn.setEnabled(false);

                count++;

                int dieRoll = rollDie();
                Log.i("Comp Die Roll", String.valueOf(Arrays.asList(FACE_OF_DICE).indexOf(dieRoll) + 1));

                if(count < 20){
                    if(dieRoll != FACE_OF_DICE[0]){
                        comp_turnScore += Arrays.asList(FACE_OF_DICE).indexOf(dieRoll) + 1;
                    } else {
                        comp_turnScore = 0;
                        compScore.setText(comp_turnScore + " ");
                        rollBtn.setEnabled(true);
                        holdBtn.setEnabled(true);
                        return;
                    }

                    compScore.setText(comp_turnScore + " ");
                }

                comp_overallScore += comp_turnScore;

                Log.i("Comp Overall Score", String.valueOf(comp_overallScore));

                rollBtn.setEnabled(true);
                holdBtn.setEnabled(true);

                timerHandler.postDelayed(this, 500);

                return;
            }
        };

        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public int rollDie(){
        int dieRoll = FACE_OF_DICE[rand.nextInt(6)];
        diceImg.setImageDrawable(ResourcesCompat.getDrawable(getResources(), dieRoll, null));
        return dieRoll;
    }

}
