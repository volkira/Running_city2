package a1.SPbRun2.loader;

/**
 * Created by FreeWind on 02.02.2017.
 */

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import a1.SPbRun2.dto.QuestDTO;
import a1.SPbRun2.util.Constants;
import a1.SPbRun2.util.HeaderRequestInterceptor;

public class QuestLoader extends AsyncTaskLoader<ArrayList<QuestDTO>>{
    private ArrayList<QuestDTO> questList;
    public String header;



    public QuestLoader(Context context, ArrayList<QuestDTO> questList) {
        super(context);
        this.questList=questList;

    }

    @Override
    public ArrayList<QuestDTO> loadInBackground() {

        try {
            List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
            interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
            interceptors.add(new HeaderRequestInterceptor("Authorization", header));
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setInterceptors(interceptors);
            ResponseEntity<QuestDTO[]> responseEntity = restTemplate.getForEntity(Constants.URL.GET_QUEST_LIST, QuestDTO[].class);
            QuestDTO[] quests = responseEntity.getBody();
            MediaType contentType = responseEntity.getHeaders().getContentType();
            HttpStatus statusCode = responseEntity.getStatusCode();
            return new ArrayList<QuestDTO>(Arrays.asList(quests));


        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public void deliverResult(ArrayList<QuestDTO> result) {
        if (this.isReset()) {
            // The Loader has been reset; ignore the result and invalidate the data.
            result=null;
            return;
        }

        questList = result;

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
        if (questList != null) {
            // Deliver any previously loaded data immediately.
            deliverResult(questList);
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
    public void onCanceled(ArrayList<QuestDTO> result) {
        // Attempt to cancel the current asynchronous load.
        super.onCanceled(result);

    }



}



