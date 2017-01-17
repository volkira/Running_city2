package a1.SPbRun2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import a1.SPbRun2.R;
import a1.SPbRun2.adapter.QuestionAdapter;
import a1.SPbRun2.dto.QuestionDTO;


public class PointFragment extends Fragment {

    private List<QuestionDTO> data;
    private Context context;

    private QuestionAdapter adapter;

    public PointFragment() {

    }


    public static PointFragment newInstance(Context context) {
        PointFragment fragment = new PointFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setContext(context);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycleView);
        rv.setLayoutManager(new LinearLayoutManager(context));

        adapter = new QuestionAdapter(data);
        rv.setAdapter(adapter);

        return view;
    }

    public void refreshList(List<QuestionDTO> data) {
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setData(List<QuestionDTO> data) {
        this.data = data;
    }
}

