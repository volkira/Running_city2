package a1.SPbRun2;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import a1.SPbRun2.dto.UserDTO;

/**
 * Created by FreeWind on 19.01.2017.
 */

public class Register extends AbstractAsyncActivity {
    private EditText userName;
    private EditText userEmail;
    private String selectedLanguage;
    private String password;
    private Date birthdate;
    private EditText userPassword;
    private EditText confPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = (EditText) findViewById(R.id.edit_user);
        userEmail = (EditText) findViewById(R.id.edit_email);
        userPassword = (EditText) findViewById(R.id.edit_password);
        confPassword = (EditText) findViewById(R.id.edit_password_confirm);
        EditText userBirthdate = (EditText) findViewById(R.id.edit_birthdate);
        String dateString = userBirthdate.getText().toString();
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            birthdate = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Spinner spinner = (Spinner) findViewById(R.id.language_choice);
        selectedLanguage = spinner.getSelectedItem().toString();




        // Initiate the request to the protected service
        final Button submitButton = (Button) findViewById(R.id.button_register);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new RegisterTask().execute();
            }
        });




    }

    // ***************************************
    // Private methods
    // ***************************************
    private void showResult(String result) {
        if (result != null) {
            // display a notification to the user with the response message
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "I got null, something happened!", Toast.LENGTH_LONG).show();
        }
    }

    // ***************************************
    // Private classes
    // ***************************************
    private class RegisterTask extends AsyncTask<MediaType, Void, String> {

        private UserDTO user;

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();

            // build the message object


            user = new UserDTO();
            user.setName(userName.getText().toString());
            try {
                user.setEmail(userEmail.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (userPassword != null && userPassword.getText().toString().equals(confPassword.getText().toString())){
                password = userPassword.getText().toString();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_LONG).show();
            }
            user.setPassword(password);
            user.setBirthDate(birthdate);
            user.setLanguage(selectedLanguage);



        }

        @Override
        protected String doInBackground(MediaType... params) {

            MediaType mediaType = MediaType.APPLICATION_JSON;

            // The URL for making the POST request
       HttpHeaders requestHeaders = new HttpHeaders();

                // Sending a JSON or XML object i.e. "application/json" or "application/xml"
            requestHeaders.setContentType(mediaType);

                // Populate the Message object to serialize and headers in an
                // HttpEntity object to use for the request
            HttpEntity<UserDTO> requestEntity = new HttpEntity<UserDTO>(user, requestHeaders);

                // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                // Make the network request, posting the message, expecting a String in response from the server
            ResponseEntity<String> response = restTemplate.exchange(Constants.URL.POST_NEW_PROFILE, HttpMethod.POST, requestEntity,
                        String.class);

                // Return the response body to display to the user
            return response.getBody();

        }

        @Override
        protected void onPostExecute(String result) {
            dismissProgressDialog();
            showResult(result);
        }

    }
}

