package a1.SPbRun2.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import a1.SPbRun2.R;
import a1.SPbRun2.dto.QuestDTO;

public class QuestListAdapter extends RecyclerView.Adapter<QuestListAdapter.QuestViewHolder>{

    private List<QuestDTO> questList;

    public QuestListAdapter(List<QuestDTO> questList) {
        this.questList = questList;
    }

    @Override
    public int getItemCount() {
        return questList.size();
    }

    @Override
    public QuestViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quest_choice_card, viewGroup, false);
        QuestViewHolder questViewHolder = new QuestViewHolder(v);
        return questViewHolder;
    }

    @Override
    public void onBindViewHolder(QuestViewHolder questViewHolder, int position) {
        QuestDTO item = questList.get(position);
        questViewHolder.questName.setText(item.getName());
        questViewHolder.questDescription.setText(item.getDescription());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setData(List<QuestDTO> questList) {
        this.questList = questList;
    }

    public static class QuestViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView questName;
        TextView questDescription;
//        ImageView questPicture;

        QuestViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
            questName = (TextView)itemView.findViewById(R.id.quest_name);
            questDescription = (TextView)itemView.findViewById(R.id.quest_description);
//            questPicture = (ImageView)itemView.findViewById(R.id.quest_picture);
        }
    }
}