package a1.SPbRun2.loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import a1.SPbRun2.dto.AnswerDTO;
import a1.SPbRun2.util.Constants;
import a1.SPbRun2.util.HeaderRequestInterceptor;

/**
 * Created by FreeWind on 04.02.2017.
 */

public class AnswerLoader extends AsyncTaskLoader<Boolean> {
    private AnswerDTO answer;
    public String header;
    private Boolean result;



    public AnswerLoader(Context context, AnswerDTO answer) {
        super(context);
        this.answer=answer;

    }

    @Override
    public Boolean loadInBackground() {

        try {
            List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
            interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
            interceptors.add(new HeaderRequestInterceptor("Authorization", header));
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setInterceptors(interceptors);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Constants.URL.POST_ANSWER);
            HttpEntity<AnswerDTO> requestEntity = new HttpEntity<AnswerDTO>(answer);
            ResponseEntity<Boolean> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, requestEntity, Boolean.class);
            return response.getBody();


        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public void deliverResult(Boolean result) {
        if (this.isReset()) {
            // The Loader has been reset; ignore the result and invalidate the data.
            result=null;
            return;
        }

        this.result = result;


        if (this.isStarted()) {
            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult(result);
        }

    }


    /*********************************************************/
    /** (3) Implement the Loader’s state-dependent behavior **/
    /*********************************************************/

    @Override
    protected void onStartLoading() {


        if (result != null) {
            // Deliver any previously loaded data immediately.
            deliverResult(result);
        }
    }

    @Override
    protected void onStopLoading() {
        // The Loader is in a stopped state, so we should attempt to cancel the
        // current load (if there is one).
        cancelLoad();

        // Note that we leave the observer as is. Loaders in a stopped state
        // should still monitor the data source for changes so that the Loader
        // will know to force a new load if it is ever started again.
    }

    @Override
    protected void onReset() {
        // Ensure the loader has been stopped.
        onStopLoading();

    }

    @Override
    public void onCanceled(Boolean result) {
        // Attempt to cancel the current asynchronous load.
        super.onCanceled(result);

    }

}
