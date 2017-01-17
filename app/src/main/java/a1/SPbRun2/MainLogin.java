package a1.SPbRun2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;


public class MainLogin extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button loginBtn;
    private Button registerBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);

        login = (EditText) findViewById(R.id.edit_user);
        password = (EditText) findViewById(R.id.edit_password);
        loginBtn = (Button) findViewById(R.id.button_login);
        registerBtn = (Button) findViewById(R.id.registerBtn);


    }




}