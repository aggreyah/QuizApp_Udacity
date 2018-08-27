package com.example.android.quizapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    static int possibleNumberOfPoints = 9;
    int pointsScoredSoFar = 0;
    float userScore;
    boolean userAnswersAll;
    String userName, userEmail;

    public void submitAnswers(View view){

        EditText userNameRaw = findViewById(R.id.entered_name);
        EditText userEmailRaw = findViewById(R.id.entered_email);
        String usersName = userNameRaw.getText().toString();
        String usersEmail = userEmailRaw.getText().toString();
        userName = usersName;
        userEmail = usersEmail;

        CheckBox checkBoxOne = findViewById(R.id.checkbox_one);
        CheckBox checkBoxTwo = findViewById(R.id.checkbox_two);
        CheckBox checkBoxThree = findViewById(R.id.checkbox_three);
        CheckBox checkBoxFour = findViewById(R.id.checkbox_four);
        CheckBox checkBoxFive = findViewById(R.id.checkbox_five);

        RadioGroup radioGroupOne = findViewById(R.id.radio_quiz_two);
        RadioGroup radioGroupTwo = findViewById(R.id.radio_quiz_three);
        RadioGroup radioGroupThree = findViewById(R.id.radio_quiz_four);

        RadioButton firstQuizTwo = findViewById(R.id.button_one_quiz2);
        RadioButton firstQuizThree = findViewById(R.id.button_one_quiz3);
        RadioButton secondQuizFour = findViewById(R.id.button_two_quiz4);

        EditText response = findViewById(R.id.entered_answer);
        String responseString = response.getText().toString();


        userAnswersAll = checkUserAttemptsAllQuestions(checkBoxOne,
                checkBoxTwo, checkBoxThree, checkBoxFour,
                checkBoxFive, radioGroupOne, radioGroupTwo, radioGroupThree, responseString);
        if (userAnswersAll){
            markUserResponses(checkBoxOne, checkBoxTwo, checkBoxThree, checkBoxFour, checkBoxFive,
                    firstQuizTwo, firstQuizThree, secondQuizFour, responseString);

            userScore = ((float) pointsScoredSoFar / possibleNumberOfPoints) * 100;
        } else{
            return;
        }

    }


    public boolean checkUserAttemptsAllQuestions(CheckBox checkOne, CheckBox checkTwo, CheckBox
            checkThree, CheckBox checkFour, CheckBox checkFive, RadioGroup groupOne, RadioGroup
            groupTwo, RadioGroup groupThree, String stringAnswer){

        int checked = 0;

        if (checkOne.isChecked()){
            checked += 1;
        }
        if (checkTwo.isChecked()){
            checked += 1;
        }
        if (checkThree.isChecked()){
            checked += 1;
        }
        if (checkFour.isChecked()){
            checked += 1;
        }
        if (checkFive.isChecked()){
            checked += 1;
        }

        if (checked == 0){
            Toast.makeText(this, getString(R.string.checkIfQuestionOneHasAResponse),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (groupOne.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, getString(R.string.checkIfQuestionTwoHasAResponse),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (groupTwo.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, getString(R.string.checkIfQuestionThreeHasAResponse),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (groupThree.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, getString(R.string.checkIfQuestionFourHasAResponse),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (stringAnswer.trim().equals("")){
            Toast.makeText(this, getString(R.string.checkIfQuestionFiveHasAResponse),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void markUserResponses(CheckBox check1, CheckBox check2, CheckBox check3, CheckBox
            check4, CheckBox check5, RadioButton radio1, RadioButton radio2, RadioButton
            radio3, String aString){

        pointsScoredSoFar = 0;

        // Now we mark the answers!
        if (check1.isChecked()){
            pointsScoredSoFar += 1;
        }
        if (check2.isChecked()){
            pointsScoredSoFar += 1;
        }
        if (check4.isChecked()){
            pointsScoredSoFar += 1;
        }
        if (!check3.isChecked()){
            pointsScoredSoFar += 1;
        }
        if (!check5.isChecked()){
            pointsScoredSoFar += 1;
        }
        if (radio1.isChecked()){
            pointsScoredSoFar += 1;
        }
        if (radio2.isChecked()){
            pointsScoredSoFar += 1;
        }
        if (radio3.isChecked()){
            pointsScoredSoFar += 1;
        }
        if (aString.trim().equals("Calculus")){
            pointsScoredSoFar += 1;
        }
        Toast.makeText(this, getString(R.string.mark_user_response, pointsScoredSoFar,
                possibleNumberOfPoints), Toast.LENGTH_SHORT).show();
        return;
    }

    public void computeScore(View view){
        // to ensure user submits results
        if (!userAnswersAll){
            Toast.makeText(this, getString(R.string.remind_user_to_answer_all), Toast.LENGTH_SHORT).show();
            return;
        }
        // to ensure email and name tabs not empty.
        if (userName.trim().equals("") || userEmail.trim().equals("")){
            Toast.makeText(this, getString(R.string.remind_user_to_enter_details),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        String formattedString = String.format("%.2f", userScore);
        Toast.makeText(this, getString(R.string.its_okay_to_mark, userName,
                formattedString, userEmail), Toast.LENGTH_SHORT).show();
        return;
    }

    public void emailResults(View view){
        if (!userAnswersAll){
            Toast.makeText(this, getString(R.string.email_button1), Toast.LENGTH_SHORT).show();
            return;
        }
        if (userName.trim().equals("") || userEmail.trim().equals("")){
            Toast.makeText(this, getString(R.string.email_button2),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent myEmailDesire = new Intent(Intent.ACTION_SENDTO);
        myEmailDesire.setData(Uri.parse("mailto:"));

        myEmailDesire.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_button4, userName));

        myEmailDesire.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_button3, userName, String.format("%.2f", userScore)));

        myEmailDesire.putExtra(Intent.EXTRA_EMAIL, new String[]{"aggreyah@hotmail.com", userEmail});

        if (myEmailDesire.resolveActivity(getPackageManager()) != null){
            startActivity(myEmailDesire);
        }
    }

}
