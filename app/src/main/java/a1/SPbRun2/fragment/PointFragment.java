package a1.SPbRun2.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import a1.SPbRun2.R;
import a1.SPbRun2.dto.QuestionDTO;
import a1.SPbRun2.util.Constants;
import a1.SPbRun2.util.HeaderRequestInterceptor;


public class PointFragment extends Fragment {

    TextView questionText;
    QuestionRequestTask questionRequestTask;
    private static String header;
    public PointFragment() {

    }


    public static PointFragment newInstance(Context context) {
        PointFragment fragment = new PointFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        header = args.getString("header");
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_point, container, false);
        questionText = (TextView) view.findViewById(R.id.point);
        questionRequestTask.execute();
        return view;
    }


    private class QuestionRequestTask extends AsyncTask<Void, Void, QuestionDTO> {
        @Override
        protected QuestionDTO doInBackground(Void... params) {
            try {
                List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
                interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
                interceptors.add(new HeaderRequestInterceptor("Authorization", header));
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                QuestionDTO point = restTemplate.getForObject(Constants.URL.GET_QUESTION, QuestionDTO.class);
                return point;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(QuestionDTO point) {
            questionText.setText(point.getQuestionText());
        }

    }


}

