package com.yuriyk_israelb.ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import static java.lang.String.format;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView[][] txvBoard;
    private Button btnNewGame;
    private TextView txvTime, txvMove;
    private GameBoard board;
    private MediaPlayer mediaPlayer;
    private int counter = 0, sec = 0, min = 0;
    private boolean isGameOver = false, onPause = false;
    SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mediaPlayer = MediaPlayer.create(this, R.raw.song);
        board = new GameBoard();
        txvBoard = new TextView[board.BOARD_SIZE][board.BOARD_SIZE];
        sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if(sp.getBoolean("isMusicOn", false))
            mediaPlayer.start();
        //set number of moves in shuffle according choosed level
        board.setNumOfShuffIteration(sp.getInt("level", 0));
        board.shuffle(board.BOARD_SIZE-1,board.BOARD_SIZE-1);


        btnNewGame = findViewById(R.id.btnNewGameID);
        txvMove = findViewById(R.id.txvMoveID);
        txvTime = findViewById(R.id.txvTimeID);
        txvBoard[0][0] = findViewById(R.id.txv1ID);
        txvBoard[0][1] = findViewById(R.id.txv2ID);
        txvBoard[0][2] = findViewById(R.id.txv3ID);
        txvBoard[0][3] = findViewById(R.id.txv4ID);
        txvBoard[1][0] = findViewById(R.id.txv5ID);
        txvBoard[1][1] = findViewById(R.id.txv6ID);
        txvBoard[1][2] = findViewById(R.id.txv7ID);
        txvBoard[1][3] = findViewById(R.id.txv8ID);
        txvBoard[2][0] = findViewById(R.id.txv9ID);
        txvBoard[2][1] = findViewById(R.id.txv10ID);
        txvBoard[2][2] = findViewById(R.id.txv11ID);
        txvBoard[2][3] = findViewById(R.id.txv12ID);
        txvBoard[3][0] = findViewById(R.id.txv13ID);
        txvBoard[3][1] = findViewById(R.id.txv14ID);
        txvBoard[3][2] = findViewById(R.id.txv15ID);
        txvBoard[3][3] = findViewById(R.id.txv16ID);

        for (int i = 0; i < board.BOARD_SIZE; i++) {
            for (int j = 0; j < board.BOARD_SIZE; j++) {
                String indx = Integer.toString(i) + Integer.toString(j); // convert cell index to String
                txvBoard[i][j].setOnClickListener(this);
                txvBoard[i][j].setTag(indx); // save the String cell index in the tag
            }
        }
        btnNewGame.setOnClickListener(this);
        txvMove.setOnClickListener(this);


        setTxvBoard();
        time();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPause = true;
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mediaPlayer.isPlaying() && sp.getBoolean("isMusicOn", false))
            mediaPlayer.start();
        onPause = false;

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnNewGameID){
            sec = 0;
            min = 0;
            counter = 0;
            if(isGameOver){
                time();
                setClick(true);
            }
            board.shuffle(board.findSixteen()[0], board.findSixteen()[1]);
            setTxvBoard();
        }
        else{
            if(v.isClickable()){
                String indx = (String) v.getTag();
                int i = Character.getNumericValue(indx.charAt(0)), j = Character.getNumericValue(indx.charAt(1)); // put index in i and j from tag
                int i1 = board.findSixteen()[0], j1 = board.findSixteen()[1];
                // check if the move is legal
                if((i == i1 && Math.abs(j - j1) == 1) || (j == j1 && Math.abs(i - i1) == 1)) {
                    board.swap(i1, j1, i, j);
                    counter++;
                    setTxvBoard();
                }
            }
        }
    }

    /**
     *  this function create new thread and launch timer
     *  and display it by using runOnUiThread function.
     */
    private void time() {
        new Thread(new Runnable()
        {
            @Override
            public void run() {
                while (true) {
                    if(isGameOver)
                        return;
                    else if(onPause)
                        continue;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final String time = format("Time: %02d:%02d", min, sec);
                            txvTime.setText(time);
                            sec++;
                            if (sec == 60) {
                                sec = 0;
                                min++;
                            }
                            if(min == 60)
                                min = 0;
                        }
                    });
                    SystemClock.sleep(1000);    // sleep for 1000ms = 1sec
                }
            }
        }).start();
    }

    /**
     *  this function check if the game is over, if so it display toast with massage.
     *  also it checked if there are changes in the game board or in amount of move and update display.
     */
    public void setTxvBoard() {
        String count = format("Move: %04d", counter);
        txvMove.setText(count);
        isGameOver = board.isWin(board.getBoard());

        if(isGameOver){
            setClick(false);
            Toast.makeText(this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
        }
        for (int i = 0; i < board.BOARD_SIZE; i++) {
            for (int j = 0; j < board.BOARD_SIZE; j++) {
                if (board.getBoard()[i][j] == 16) {
                    txvBoard[i][j].setText("");
                    txvBoard[i][j].setBackgroundColor(Color.rgb(201, 167, 118));
                } else {
                    String num = Integer.toString(board.getBoard()[i][j]);
                    txvBoard[i][j].setText(num);
                    txvBoard[i][j].setBackgroundColor(Color.rgb(121, 85, 72));
                }
            }

        }
    }

    /**
     *  this function have boolean argument
     *  if the argument is true - it make all cell in board clickable
     *  if the argument is false - it block all cells.
     */
    public void setClick(boolean isAClicakble){
        for (int i = 0; i < board.BOARD_SIZE; i++) {
            for (int j = 0; j < board.BOARD_SIZE; j++) {
                txvBoard[i][j].setClickable(isAClicakble);
            }
        }
    }


}
