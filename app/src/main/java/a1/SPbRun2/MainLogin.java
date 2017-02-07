package a1.SPbRun2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import a1.SPbRun2.dto.UserDTO;
import a1.SPbRun2.util.Constants;
import a1.SPbRun2.util.SecurePreferences;


public class MainLogin extends AbstractAsyncActivity  {

    private String username;
    private String password;
    private String header;

    protected static final String TAG = MainLogin.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);

        // Initiate the request to the protected service
        final Button submitButton = (Button) findViewById(R.id.button_login);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new FetchSecuredResourceTask().execute();
            }
        });
        final Button registerButton = (Button) findViewById(R.id.registerBtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }


    private void displayResponse(UserDTO response) {
        if (response.getEmail() != null){
            Toast.makeText(this, getApplicationContext().getString(R.string.welcome_message)+ " " + response.getName() + "!", Toast.LENGTH_LONG).show();
            SecurePreferences preferences = new SecurePreferences(this, "my-preferences", "998811223377446655", true);
            preferences.put("header", header);
            Intent intent = new Intent(this.getApplicationContext(), QuestChoice.class);
            startActivity(intent);}
        else{
            Toast.makeText(this, getApplicationContext().getString(R.string.auth_failure), Toast.LENGTH_LONG).show();}
    }



    // ***************************************
    // Private classes
    // ***************************************
    private class FetchSecuredResourceTask extends AsyncTask<Void, Void, UserDTO> {


        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();

            // build the message object
            EditText editText = (EditText) findViewById(R.id.edit_user);
            username = editText.getText().toString();

            editText = (EditText) findViewById(R.id.edit_password);
            password = editText.getText().toString();
        }

        @Override
        protected UserDTO doInBackground(Void... params) {


            // Populate the HTTP Basic Authentitcation header with the username and password
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader);
            header = requestHeaders.getAuthorization();
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            try {
                // Make the network request
                Log.d(TAG, Constants.URL.GET_PROFILE);
                ResponseEntity<UserDTO> response = restTemplate.exchange(Constants.URL.GET_PROFILE, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), UserDTO.class);
                return response.getBody();
            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new UserDTO();
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new UserDTO();
            }
        }

        @Override
        protected void onPostExecute(UserDTO result) {
            dismissProgressDialog();
            displayResponse(result);}

        }

    }



