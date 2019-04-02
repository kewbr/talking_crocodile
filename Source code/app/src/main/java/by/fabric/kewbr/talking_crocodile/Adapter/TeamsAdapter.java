package by.fabric.kewbr.talking_crocodile.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.fabric.kewbr.talking_crocodile.Model.TeamNamesModel;
import by.fabric.kewbr.talking_crocodile.R;

import by.fabric.kewbr.talking_crocodile.Model.WordStatusModel;

public class TeamsAdapter extends
        RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>  {

    private int countOfTeamsItems;
    private List<TeamNamesModel> teams;

    public TeamsAdapter (List<TeamNamesModel> teams, Context context) {

        this.countOfTeamsItems = teams.size();
        this.teams = new ArrayList<>(teams);
    }

    @NonNull
    @Override
    public TeamsAdapter.TeamsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int idLayoutWordCheckCell = R.layout.teams_add_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(idLayoutWordCheckCell, viewGroup, false);
        return new TeamsAdapter.TeamsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamsAdapter.TeamsViewHolder TeamsViewHolder, int i) {
        //MARK – Here update element of recyclerView
        TeamsViewHolder.bind(this.teams.get(i).getTeamName());
    }

    public void addItem(TeamNamesModel team) {
        teams.add(team);
        countOfTeamsItems++;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return countOfTeamsItems;
    }

    class TeamsViewHolder extends RecyclerView.ViewHolder {

        TextView teamTextView;
        Button deleteButton;

        public TeamsViewHolder(View itemView) {
            super(itemView);

            teamTextView = itemView.findViewById(R.id.teamTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    deleteItem();
                }
            });

        }
           void bind(String teamName) {
              teamTextView.setText(teamName);
        }
           void deleteItem(){
               teams.remove(getAdapterPosition());
               countOfTeamsItems--;
               notifyDataSetChanged();
           }
    }
}
