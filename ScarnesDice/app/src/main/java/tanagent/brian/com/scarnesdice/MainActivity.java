package tanagent.brian.com.scarnesdice;

import android.media.Image;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[] FACE_OF_DICE = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
                                R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    private int user_overallScore;
    private int user_turnScore;
    private int comp_overallScore;
    private int comp_turnScore;

    private ImageView diceImg;
    private Button rollBtn;
    private Button holdBtn;
    private Button resetBtn;

    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollBtn = (Button) findViewById(R.id.btn_roll);
        holdBtn = (Button) findViewById(R.id.btn_hold);
        resetBtn = (Button) findViewById(R.id.btn_reset);
        diceImg = (ImageView) findViewById(R.id.img_dice);

        rollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dieRoll = FACE_OF_DICE[rand.nextInt(5)];
                diceImg.setImageDrawable(ResourcesCompat.getDrawable(getResources(), dieRoll, null));

                if(dieRoll != FACE_OF_DICE[0])
                    user_overallScore += dieRoll;
                else
                    user_overallScore = 0;
            }
        });
    }


}
