package com.google.engedu.ghost;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();

    private Button challengeBtn;
    private Button restartBtn;
    private TextView text;
    private String wordFrag;
    private TextView label;
    private SimpleDictionary simpleDictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        onStart(null);

        challengeBtn = (Button) findViewById(R.id.challenge);
        restartBtn = (Button) findViewById(R.id.restart);

        challengeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordFrag = text.getText().toString();

                if(wordFrag.length() >= 4 && simpleDictionary.isWord(wordFrag)) {
                    label.setText("USER WINS");
                } else if(simpleDictionary.getAnyWordStartingWith(wordFrag) != null) {
                    if(simpleDictionary.isWord(simpleDictionary.getAnyWordStartingWith(wordFrag)))
                        label.setText("COMPUTER WINS. Possible word is " + simpleDictionary.getAnyWordStartingWith(wordFrag));
                } else {
                    label.setText("USER WINS");
                }
            }
        });

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordFrag = "";
                onStart(null);
            }
        });

        try {
            InputStream input = getAssets().open("words.txt");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);

            simpleDictionary = new SimpleDictionary(input);
            input.close();
            Log.i("text", new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if(keyCode >= event.KEYCODE_A && keyCode <= event.KEYCODE_Z) {
            String str = Character.toString((char) event.getUnicodeChar());
            wordFrag += str;
            text.setText(wordFrag);

            return super.onKeyUp(keyCode, event);
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again

        userTurn = false;

        if(wordFrag.length() >= 4) {
            label.setText(wordFrag + " is a valid word. COMPUTER WINS!!");
        } else {
            String newWord = dictionary.getAnyWordStartingWith(wordFrag);

            if(newWord == null) {
                label.setText(wordFrag + " is not a valid word prefix. COMPUTER WINS");

            } else {
                wordFrag = newWord.substring(0, wordFrag.length() + 1);
                text.setText(wordFrag);

                userTurn = true;
                label.setText(USER_TURN);
            }
        }

        userTurn = true;
        label.setText(USER_TURN);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }
}