package a1.SPbRun2.fragment;

/**
 * Created by FreeWind on 06.02.2017.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

public class AbstractFragment extends Fragment {

    private String title;
    protected Context context;
    protected View view;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
