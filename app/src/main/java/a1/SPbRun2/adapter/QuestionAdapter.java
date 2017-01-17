package a1.SPbRun2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import a1.SPbRun2.R;
import a1.SPbRun2.dto.QuestionDTO;

;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<QuestionDTO> data;

    public QuestionAdapter(List<QuestionDTO> data) {
        this.data = data;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        QuestionDTO item = data.get(position);
        holder.questionText.setText(item.getQuestionText());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<QuestionDTO> data) {
        this.data = data;
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionText;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            questionText = (TextView) itemView.findViewById(R.id.point);
        }
    }

}