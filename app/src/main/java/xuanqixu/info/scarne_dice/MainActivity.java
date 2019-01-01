package xuanqixu.info.scarne_dice;

import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //global variables
    private int userOverallScore;
    private int userTurnScore;
    private int computerOverallScore;
    private int computerTurnScore;
    //buttons
    private Button roll;
    private Button hold;
    private Button reset;

    private TextView scoreboard;
    private ImageView dicePicture;

    private static int diceFaces[] = {
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6
    };
    Handler handler = new Handler();
    int random;
    boolean computerRoll = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roll = findViewById(R.id.roll);
        hold = findViewById(R.id.hold);
        reset= findViewById(R.id.reset);

        scoreboard = findViewById(R.id.scoreboard);
        dicePicture= findViewById(R.id.dicepicture);

        roll.setOnClickListener(this);
        hold.setOnClickListener(this);
        reset.setOnClickListener(this);


    }
    private void rollDice(){
        handler=new Handler();
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                computerRoll=true;
                computerTurn();
                enableButtons();
            }
        };
        int random =(int) (Math.random()*6) +1;
        dicePicture.setImageResource(diceFaces[random-1]);
        if(random==1){
            userTurnScore=0;
            updateScoreboard();
            disableButtons();
            Toast.makeText(this,"You rolled 1. Computer turn",Toast.LENGTH_LONG).show();
            handler.postDelayed(r1,2000);
        }
        else {
            userTurnScore+=random;
            updateScoreboard();
        }

    }
    private void holdTurn(){
        userOverallScore+=userTurnScore;
        userTurnScore=0;
        updateScoreboard();
        disableButtons();
        if(userOverallScore>=100){
            scoreboard.setText("You Win!");
            return;
        }
        computerRoll=true;
        computerTurn();
        enableButtons();
    }
    private void resetGame(){
        dicePicture.setImageResource(diceFaces[0]);
        userTurnScore=0;
        userOverallScore=0;
        computerOverallScore=0;
        computerTurnScore=0;
        scoreboard.setText("New Game");
        enableButtons();
    }

    private void computerTurn(){
            if(computerRoll=false){return;}
            if(computerTurnScore>20){
                computerOverallScore+=computerTurnScore;
                computerTurnScore=0;
                updateScoreboard();
                Toast.makeText(this,"Computer finished roll. Your turn",Toast.LENGTH_LONG).show();
                return;
            }

            random =(int) (Math.random()*6) +1;
            dicePicture.setImageResource(diceFaces[random-1]);


            if(random==1){
                computerTurnScore=0;
                updateScoreboard();
                enableButtons();
                Toast.makeText(this, "Computer rolled 1. Your turn", Toast.LENGTH_SHORT).show();
                computerRoll=false;
                return;
            }
            else{
                Runnable r1 = new Runnable() {
                    @Override
                    public void run() {
                        computerTurn();
                    }
                };
                Log.d("Score", "CPU rolled " + random);
                computerTurnScore+=random;
               updateScoreboard();
               handler.postDelayed(r1,2000);
            }




        if(computerOverallScore>=100){
            scoreboard.setText("Computer Wins");
            disableButtons();
            computerRoll=false;
            return;
        }

    }
    public void computerRoll(){

    }
    private void updateScoreboard(){
        String s ="";
        s+= "Your score:"+userOverallScore+" Your turn score:"+userTurnScore+"\n"+
                "Computer score:"+computerOverallScore+" Computer turn score:"+computerTurnScore;
        scoreboard.setText(s);
    }

    private void disableButtons() {
        roll.setEnabled(false);
        hold.setEnabled(false);
    }

    private void enableButtons() {
        roll.setEnabled(true);
        hold.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.roll:
                rollDice();
                break;
            case R.id.hold:
                holdTurn();
                break;
            case R.id.reset:
                resetGame();
                break;
        }
    }
}