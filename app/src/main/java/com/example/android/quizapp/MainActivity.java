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
    // storing possible number of points
    static int possibleNumberOfPoints = 9;
    //store current score of user.
    int pointsScoredSoFar = 0;
    //store total score as a percent of possible number of points
    float userScore;
    //check if user has answered all questions
    boolean userAnswersAll;
    //store user name and email globally so as to access from any function.
    String userName, userEmail;

    /**
     *  @param view the submit button view.
     *  defines what happens when user hits the submit button.
     */
    public void submitAnswers(View view){

        //create editText view objects to extract text and assign to string variables userName and userEmail.
        EditText userNameRaw = findViewById(R.id.entered_name);
        EditText userEmailRaw = findViewById(R.id.entered_email);
        String usersName = userNameRaw.getText().toString();
        String usersEmail = userEmailRaw.getText().toString();
        userName = usersName;
        userEmail = usersEmail;

        //Create variables for all the checkBoxes to use to check if user has selected at least one.
        CheckBox checkBoxOne = findViewById(R.id.checkbox_one);
        CheckBox checkBoxTwo = findViewById(R.id.checkbox_two);
        CheckBox checkBoxThree = findViewById(R.id.checkbox_three);
        CheckBox checkBoxFour = findViewById(R.id.checkbox_four);
        CheckBox checkBoxFive = findViewById(R.id.checkbox_five);

        //Create variables for all the radiogroups which we will check if user has selected any
        RadioGroup radioGroupOne = findViewById(R.id.radio_quiz_two);
        RadioGroup radioGroupTwo = findViewById(R.id.radio_quiz_three);
        RadioGroup radioGroupThree = findViewById(R.id.radio_quiz_four);

        //Create variables for all the radio buttons that have right answers.
        RadioButton firstQuizTwo = findViewById(R.id.button_one_quiz2);
        RadioButton firstQuizThree = findViewById(R.id.button_one_quiz3);
        RadioButton secondQuizFour = findViewById(R.id.button_two_quiz4);

        //Read the text entered by user
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

    /**
     * @params checkOne, checkTwo, checkThree, checkFour, checkFive, groupOne, groupTwo, groupThree,
     * stringAnswer all checkboxes, all the radiogroups and the last Edit text response
     * @returns true if user has responded to all quiz questions and false otherwise.
     */
    public boolean checkUserAttemptsAllQuestions(CheckBox checkOne, CheckBox checkTwo, CheckBox
            checkThree, CheckBox checkFour, CheckBox checkFive, RadioGroup groupOne, RadioGroup
            groupTwo, RadioGroup groupThree, String stringAnswer){

        /**Using a variable checked initialized to zero to run through check boxes and
         * incerement it by one whenever a checkbox is checked.
         */
        //a variable to keep track of how many check boxes are checked.
        int checked = 0;

        //use if conditions to check if any of the checkboxes is checked.
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
        //After running through all checkboxes and checked is still zero then we know no response on quiz one.

        if (checked == 0){
            Toast.makeText(this, getString(R.string.checkIfQuestionOneHasAResponse),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        //Now we check if the radioGroup for Quiz two has a response.
        if (groupOne.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, getString(R.string.checkIfQuestionTwoHasAResponse),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        //Now we check if the radioGroup for Quiz three has a response.
        if (groupTwo.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, getString(R.string.checkIfQuestionThreeHasAResponse),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        //Now we check if the radioGroup for Quiz four has a response.
        if (groupThree.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, getString(R.string.checkIfQuestionFourHasAResponse),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        //Now we check if the edit view for Quiz five has a response.
        if (stringAnswer.trim().equals("")){
            Toast.makeText(this, getString(R.string.checkIfQuestionFiveHasAResponse),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * @params all checkboxes, all the radiobuttons and the last Edit text response
     * @returns void, but notifies the user of their current score.
     */
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
        //We want to reward user with a point if they leave this unchecked and deny them otherwise.
        if (!check3.isChecked()){
            pointsScoredSoFar += 1;
        }
        //We want to reward user with a point if they leave this unchecked and deny them otherwise.
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

    /**
     * @params view (the your score button)on click of the how i did button.
     *
     */
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
        // if all checks show a score preview.
        String formattedString = String.format("%.2f", userScore);
        Toast.makeText(this, getString(R.string.its_okay_to_mark, userName,
                formattedString, userEmail), Toast.LENGTH_SHORT).show();
        return;
    }

    /**
     * @params view on click of the email button.
     *
     */
    public void emailResults(View view){
        // just to be sure the user has submitted results
        if (!userAnswersAll){
            Toast.makeText(this, getString(R.string.email_button1), Toast.LENGTH_SHORT).show();
            return;
        }
        //to ensure email and name fields are not empty too
        if (userName.trim().equals("") || userEmail.trim().equals("")){
            Toast.makeText(this, getString(R.string.email_button2),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        //if all checks ready the email message and send.
        Intent myEmailDesire = new Intent(Intent.ACTION_SENDTO);
        myEmailDesire.setData(Uri.parse("mailto:"));

        //Customize the subject field.
        myEmailDesire.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_button4, userName));

        //Automatically populate email body with customise text.(myText)
        myEmailDesire.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_button3, userName, String.format("%.2f", userScore)));

        //Automatically populate the to box for which addresses the email is sent to.
        myEmailDesire.putExtra(Intent.EXTRA_EMAIL, new String[]{"aggreyah@hotmail.com", userEmail});

        if (myEmailDesire.resolveActivity(getPackageManager()) != null){
            startActivity(myEmailDesire);
        }
    }

}
