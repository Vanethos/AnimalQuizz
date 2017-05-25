package com.sardinecorp.animalquizz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Result extends AppCompatActivity {

    private String mAnimalText;
    private int mAnsSum;
    private int numberOfAnimals = 10;
    private int numberOfQuestions = 8;
    private int numberOfChoices = 5;

    @BindView(R.id.animal_image)
    ImageView mResultImage;

    @BindView(R.id.result_text_title)
    TextView mResultText;

    @BindView(R.id.speech_bubble)
    TextView mSpeechBubble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);


        // receive the result from the intent
        Intent intent = getIntent();



        mAnimalText = intent.getStringExtra("string");
        mAnsSum = intent.getIntExtra("result", 8);

        setAnimalAndText(mAnsSum);

    }


    private void setAnimalAndText(int ans) {
        String animalString = "";
        int animalDrawable = -1;
        // (number of questions * number of answers) / number of animals * branch
        if (ans == numberOfQuestions) {
            animalString = "Sloth";
            animalDrawable = R.drawable.sloth;
        } else if (ans <= (numberOfQuestions * numberOfChoices)/numberOfAnimals * 2){
            animalString = "Slug";
            animalDrawable = R.drawable.slug;
        } else if (ans <= (numberOfQuestions * numberOfChoices)/numberOfAnimals* 3) {
            animalString = "Monkey";
            animalDrawable = R.drawable.monkey;
        } else if (ans <= (numberOfQuestions * numberOfChoices)/numberOfAnimals* 4) {
            animalString = "Tiger";
            animalDrawable = R.drawable.tiger;
        } else if (ans <= (numberOfQuestions * numberOfChoices)/numberOfAnimals* 5) {
            animalString = "dolphin";
            animalDrawable = R.drawable.dolphin;
        } else if (ans <= (numberOfQuestions * numberOfChoices)/numberOfAnimals* 6) {
            animalString = "Elephan";
            animalDrawable = R.drawable.elephant;
        } else if (ans <= (numberOfQuestions * numberOfChoices)/numberOfAnimals* 7) {
            animalString = "Red Panda";
            animalDrawable = R.drawable.redpanda;
        } else  if (ans <= (numberOfQuestions * numberOfChoices)/numberOfAnimals* 8) {
            animalString = "Fox";
            animalDrawable = R.drawable.fox;
        } else  if (ans <= (numberOfQuestions * numberOfChoices)/numberOfAnimals* 9) {
            animalString = "Iberian Lynx";
            animalDrawable = R.drawable.lynx;
        } else {
            animalString = "Duck";
            animalDrawable = R.drawable.duck;
        }

        Log.d("main", "Animal String: "+ (numberOfQuestions * numberOfChoices)/numberOfAnimals* 2);
        mResultImage.setImageDrawable(getResources().getDrawable(animalDrawable));
        mResultText.setText(animalString);
        mSpeechBubble.setText(mAnimalText);

        Toast.makeText(this, "You are a "+animalString, Toast.LENGTH_SHORT).show();
    }

}
