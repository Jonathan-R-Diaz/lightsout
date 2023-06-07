//CONTROLLER
package com.zybooks.lightsout;

import static com.zybooks.lightsout.LightsOutGame.GRID_SIZE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private LightsOutGame mGame;
    private GridLayout mLightGrid;
    private int mLightOnColor;
    private int mLightOffColor;
    //private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLightGrid = findViewById(R.id.light_grid);

        // Add the same click handler to all grid buttons
        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);
            gridButton.setOnClickListener(this::onLightButtonClick);
            if (buttonIndex == 0)
                gridButton.setOnLongClickListener(this::onLongButtonClick);
        }

        mLightOnColor = ContextCompat.getColor(this, R.color.yellow);
        mLightOffColor = ContextCompat.getColor(this, R.color.black);

        mGame = new LightsOutGame();
        startGame();
    }

    private void startGame() {
        mGame.newGame();
        setButtonColors();
    }

    private void onLightButtonClick(View view) {

        // Find the button's row and col
        int buttonIndex = mLightGrid.indexOfChild(view);
        int row = buttonIndex / GRID_SIZE;
        int col = buttonIndex % GRID_SIZE;

        mGame.selectLight(row, col);
        setButtonColors();

        // Congratulate the user if the game is over
        if (mGame.isGameOver()) {
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
        }
    }


    public boolean onLongButtonClick(View view){
        mGame.blackout();
        setButtonColors();
        Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
        return true;
    }

    private void setButtonColors() {

        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);

            // Find the button's row and col
            int row = buttonIndex / GRID_SIZE;
            int col = buttonIndex % GRID_SIZE;

            if (mGame.isLightOn(row, col)) {
                gridButton.setText(getText(R.string.on));
                gridButton.setTextColor(getColor(R.color.black));
                gridButton.setBackgroundColor(mLightOnColor);
            } else {
                gridButton.setText(getText(R.string.off));
                gridButton.setTextColor(getColor(R.color.yellow));
                gridButton.setBackgroundColor(mLightOffColor);
            }
        }
    }

    public void onNewGameClick(View view) {
        startGame();
    }
}