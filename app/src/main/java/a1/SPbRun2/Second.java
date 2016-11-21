package a1.SPbRun2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Second extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        Button myBtn = (Button) findViewById(R.id.toMapBtn);

    }

    public void toMap(View view){
        Intent intent = new Intent(this,Map.class);
        startActivity(intent);

    }

}
