package com.sardinecorp.animalquizz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.list_of_questions)
    ListView mListOfQuestions;

    @BindView(R.id.submit_button)
    Button mSubmitButton;

    private QuestionAdapter mAdapter;
    private List<String> mQuestions;
    protected static List<Integer> mAnswers = new ArrayList<>(Collections.nCopies(8, 1));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // create a alert dialog with instruction
        final AlertDialog alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom))
                .setTitle("Instructions")
                .setMessage("You will be presented with several sentences." +
                        "Pick a number form 1 to 5 to show how much you agree with them.\n" +
                        "    = 1 - Not at all\n" +
                        "    = 5 - Oh my! Yes!!")
                .setPositiveButton("Let\'s go!", null)
                .create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                changeDialogButtonColor(alert);
            }
        });
        alert.show();

        // set an adapter for the listview
        mQuestions = Arrays.asList(getResources().getStringArray(R.array.questions));

        //mAdapter = new ArrayAdapter<>(this, R.layout.question_template, R.id.questionText, mQuestions);
        mAdapter = new QuestionAdapter(getLayoutInflater(), mQuestions, getApplicationContext());
        mListOfQuestions.setAdapter(mAdapter);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sumOfAns() == 8) {
                    final AlertDialog warning = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.AlertDialogCustom))
                            .setTitle("WARNING!")
                            .setMessage("Are you sure you want to put them all as 1? Or you just wanted to test the app?")
                            .setNegativeButton("Sorry!", null)
                            .setPositiveButton("I'm sure", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    goToNewActivity(sumOfAns());
                                    //Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                                }
                            })
                         .create();

                    warning.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            changeDialogButtonColor(warning);
                        }
                    });
                    warning.show();

                } else {
                    goToNewActivity(sumOfAns());
                }
            }
        });


    }

    private int sumOfAns() {
        Log.d("main", ""+mAnswers);
        int result = 0;
        for(Integer i: mAnswers) {
            result+=i;
        }
        return result;
    }

    private void goToNewActivity(final int totalAns) {
        // create a text field to parse the input
        final EditText input = new EditText(MainActivity.this);
        //input.setFocusable(true);
        // set the parameters for the input
        input.requestFocus();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        input.setTextColor(Color.WHITE);
        // open the keyboard automatically
        openKeyboard(input);
        final AlertDialog warning = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom))
                .setTitle("What is in your mind now?")
                .setView(input)
                .setPositiveButton("Just who am I?", null)
                .create();



        warning.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                changeDialogButtonColor(warning);
                Button button = warning.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().trim().length() > 0 && input.getText().toString().trim().length() < 20) {
                            Intent intent = new Intent(MainActivity.this, Result.class);
                            intent.putExtra("string", input.getText().toString());
                            intent.putExtra("result", totalAns);
                            closeKeyboard(view);
                            startActivity(intent);
                        } else if (input.getText().toString().trim().length() == 0) {
                            Toast.makeText(MainActivity.this, "Please Provide Something!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Animals don't like long words :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        warning.show();
    }

    private void openKeyboard(EditText edit) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //imm.showSoftInput(edit, InputMethodManager.SHOW_FORCED);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void changeDialogButtonColor(AlertDialog dialog) {
        Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        button.setTextColor(getResources().getColor(R.color.colorAccent));
        Button cancel = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancel.setTextColor(getResources().getColor(R.color.colorAccent));
    }
}
