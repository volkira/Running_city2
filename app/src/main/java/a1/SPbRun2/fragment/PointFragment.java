package a1.SPbRun2.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import a1.SPbRun2.R;
import a1.SPbRun2.dto.AnswerDTO;
import a1.SPbRun2.dto.HintDTO;
import a1.SPbRun2.dto.QuestionDTO;
import a1.SPbRun2.loader.AnswerLoader;
import a1.SPbRun2.loader.HintLoader;
import a1.SPbRun2.loader.QuestionLoader;
import a1.SPbRun2.util.SecurePreferences;

import static a1.SPbRun2.util.Constants.URL.ANSWER_LOADER_ID;
import static a1.SPbRun2.util.Constants.URL.HINT_LOADER_ID;
import static a1.SPbRun2.util.Constants.URL.QUESTION_LOADER_ID;


public class PointFragment extends AbstractFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    TextView questionText;
    TextView hint1Text;
    TextView hint2Text;
    TextView hint3Text;
    String hintText;

    EditText answerText;
    ImageButton answerButton;
    ImageButton hint1Button;
    ImageButton hint2Button;
    ImageButton hint3Button;

    Loader<QuestionDTO> questionLoader;
    Loader<HintDTO> hintLoader;
    Loader<Boolean> answerLoader;

    private HintDTO hint;
    private QuestionDTO question;
    private AnswerDTO answer;

//    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    LatLng latLng;

    String header;
    String questId;
    String questNumberOfQuestions;

    static String title;

    public PointFragment() {
    }


    public static PointFragment newInstance(Context context) {
        PointFragment fragment = new PointFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        buildGoogleApiClient();

    }


    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(getContext(), "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(getContext(), "onConnected", Toast.LENGTH_SHORT).show();


        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
        else {
            Toast.makeText(getContext(), "Нет местоположения", Toast.LENGTH_LONG).show();
        }

//
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(5000); //5 seconds
//        mLocationRequest.setFastestInterval(3000); //3 seconds
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getContext(),"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getContext(),"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point, container, false);
        mGoogleApiClient.connect();

        SecurePreferences preferences = new SecurePreferences(getActivity().getApplicationContext(), "my-preferences", "998811223377446655", true);
        header = preferences.getString("header");
        questId = preferences.getString("questId");
        questNumberOfQuestions = preferences.getString("questNumberOfQuestions");

        answerText = (EditText) view.findViewById(R.id.answer);
        answerButton = (ImageButton) view.findViewById(R.id.answerButton);
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerLoader = getActivity().getSupportLoaderManager().initLoader(ANSWER_LOADER_ID, null, answerResultLoaderListener);
            }
        });

        questionText = (TextView) view.findViewById(R.id.point);

        hint1Text = (TextView) view.findViewById(R.id.Hint1);
        hint2Text = (TextView) view.findViewById(R.id.Hint2);
        hint3Text = (TextView) view.findViewById(R.id.Hint3);

        hint1Button = (ImageButton) view.findViewById(R.id.hint1Btn);
        hint1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintLoader = getActivity().getSupportLoaderManager().initLoader(HINT_LOADER_ID, null, hintResultLoaderListener);
                hint1Text.setText(hintText);
            }
        });
        hint2Button = (ImageButton) view.findViewById(R.id.hint2Btn);
        hint2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportLoaderManager().restartLoader(HINT_LOADER_ID, null, hintResultLoaderListener);
                hint2Text.setText(hintText);
            }
        });
        hint3Button = (ImageButton) view.findViewById(R.id.hint3Btn);
        hint3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportLoaderManager().restartLoader(HINT_LOADER_ID, null, hintResultLoaderListener);
                hint3Text.setText(hintText);
            }
        });

        question = new QuestionDTO();
        hint = new HintDTO();
        answer = new AnswerDTO();


        answer.setAnswer(answerText.getText().toString());
        answer.setQuestId(Integer.parseInt(questId));
//        answer.setLatitude(latLng.latitude);
//        answer.setLongitude(latLng.longitude);

        questionLoader = getActivity().getSupportLoaderManager().initLoader(QUESTION_LOADER_ID, null, questionResultLoaderListener);



        //
        return view;
    }

//    @Override
//    public void setTitle(String title) {
//        super.setTitle(context.getString(R.string.nav_menu_point) + questId + " из " + questNumberOfQuestions);
//    }
//
//    @Override
//    public String getTitle() {
//        return title;
//    }

    public void OnClick(View v){
     switch (v.getId()){
         case R.id.Hint1:
             hintLoader = getActivity().getSupportLoaderManager().initLoader(HINT_LOADER_ID, null, hintResultLoaderListener);
             hint1Text.setText(hintText);
             break;
         case R.id.Hint2:
             hintLoader = getActivity().getSupportLoaderManager().restartLoader(HINT_LOADER_ID, null, hintResultLoaderListener);
             hint2Text.setText(hintText);
             break;
         case R.id.Hint3:
             hintLoader = getActivity().getSupportLoaderManager().restartLoader(HINT_LOADER_ID, null, hintResultLoaderListener);
             hint3Text.setText(hintText);
             break;



        }
    }



    private LoaderManager.LoaderCallbacks<QuestionDTO> questionResultLoaderListener
            = new LoaderManager.LoaderCallbacks<QuestionDTO>() {

        @Override
        public Loader<QuestionDTO> onCreateLoader(int arg0, Bundle arg1) {
            QuestionLoader questionLoader = new QuestionLoader(getContext(), question);
            questionLoader.header = header;
            questionLoader.questId = questId;
            questionLoader.forceLoad();
            return questionLoader;
        }

        @Override
        public void onLoadFinished(Loader<QuestionDTO> arg0, QuestionDTO arg1) {
            questionText.setText(arg1.getQuestionText());
       }

        @Override
        public void onLoaderReset(Loader<QuestionDTO> arg0) {
            questionText.setText(null);
        }

   };

    private LoaderManager.LoaderCallbacks<HintDTO> hintResultLoaderListener
            = new LoaderManager.LoaderCallbacks<HintDTO>() {

        @Override
        public Loader<HintDTO> onCreateLoader(int arg0, Bundle arg1) {
            HintLoader hintLoader = new HintLoader(getContext(), hint);
            hintLoader.header = header;
            hintLoader.questId = questId;
            hintLoader.forceLoad();
            return hintLoader;
        }

        @Override
        public void onLoadFinished(Loader<HintDTO> arg0, HintDTO arg1) {
            hintText = arg1.getText();

        }

        @Override
        public void onLoaderReset(Loader<HintDTO> arg0) {

        }

    };

    private LoaderManager.LoaderCallbacks<Boolean> answerResultLoaderListener
            = new LoaderManager.LoaderCallbacks<Boolean>() {

        @Override
        public Loader<Boolean> onCreateLoader(int arg0, Bundle arg1) {
            AnswerLoader answerLoader = new AnswerLoader(getContext(), answer);
            answerLoader.header = header;
            answerLoader.forceLoad();
            return answerLoader;
        }

        @Override
        public void onLoadFinished(Loader<Boolean> arg0, Boolean arg1) {
            Toast.makeText(context, "arg1", Toast.LENGTH_SHORT).show();

//            if (arg1) {
//                getActivity().getSupportFragmentManager().beginTransaction().remove(getParentFragment()).commit();
//                newInstance(getContext());
//
//            }
//            else
//            {answerText.setText("");
//                Toast.makeText(getContext(), "Ответ неверный. Попробуйте еще раз", Toast.LENGTH_SHORT).show();;}

        }

        @Override
        public void onLoaderReset(Loader<Boolean> arg0) {

        }

    };

}


