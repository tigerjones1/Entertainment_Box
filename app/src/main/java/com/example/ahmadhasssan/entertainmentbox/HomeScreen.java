/*
Section 4: Independent Research Task
For section 4 I have added in several different features, one of them is using pictures as buttons
on the HomeScreen and the ResultPage. Rather than using the normal look of the button I used a picture
for them which are in the drawable folder with the name "hb" and "sb". The pictures
I used were created by me. In order to do this I got help from the following website,
"http://stackoverflow.com/questions/4209582/how-to-add-image-for-button-in-android". I did
also still added the strings for the buttons to ensure that I fully know how to add and
use strings. Another thing that I did was change the background of the app which is in the drawable
folder with the name "bg.jpg", I obtained this picture from google images "http://www.iphone4wp.com/iphone-4-dark-backgrounds/".
This was fairly simple to do as I just replicated the idea for changing
the button to a picture but rather a picture for a background, as the code for both is basically the same.
I also added in a Toast message that appears when you do not get any results, this can be found on the
ResultPage.java. In order to add the Toast message I got help from the following website
"http://javatechig.com/android/android-toast-example" I then just made it so that if no results show up
then display the message "No Results Found". I also managed to change the logo of the app which I also
made myself. For this I got help from the following website
"http://stackoverflow.com/questions/17371470/changing-ic-launcher-png-in-android-studio". The image that
I have used on the HomeScreen called "pichs" was obtained from the following website
"http://movie.idevcreations.com/uploads//packages-0484761001339357801.jpeg".
The last feature that I added in was speech to text to allow searching for movies a little easier for the
user, also I added a Toast message with it to inform the user if speech to text is not available for their
device. In order to get speech to text to work I followed the following tutorial
"https://www.youtube.com/watch?v=nzkrRQgCEmE". These are all the things that I have added in for my
independent research task.

 */

package com.example.ahmadhasssan.entertainmentbox;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmadhasssan.entertainmentbox.R;

import java.util.ArrayList;
import java.util.Locale;

public class HomeScreen extends AppCompatActivity {

    private TextView resultText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        resultText = (TextView) findViewById(R.id.searchBox);
    }

    public void onButtonClick(View v){
        if(v.getId() == R.id.imageButton){

            promptSpeechInput();
        }
    }

    public void promptSpeechInput(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!");

        try {
            startActivityForResult(i, 100);
        }
        catch(ActivityNotFoundException a){

        }
        Toast.makeText(HomeScreen.this, "Sorry! Your device Doesnt support speech language!", Toast.LENGTH_LONG).show();
    }

    public void onActivityResult(int request_code, int result_code, Intent i){
        super.onActivityResult(request_code, result_code, i);

        switch (request_code){
            case 100: if(result_code == RESULT_OK && i != null){
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                resultText.setText(result.get(0));
            }
                break;
        }
    }

    public void ResultPage(View view){
        EditText titleEditText = (EditText) findViewById(R.id.searchBox);
        String movieTitle = titleEditText.getText().toString();

        Intent intent = new Intent(this, ResultPage.class);
        intent.putExtra("movieTitle", movieTitle);
        startActivity(intent);
    }
}