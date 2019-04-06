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
import java.util.Random;

import by.fabric.kewbr.talking_crocodile.Model.TeamNamesModel;
import by.fabric.kewbr.talking_crocodile.R;

import by.fabric.kewbr.talking_crocodile.Model.WordStatusModel;

public class TeamsAdapter extends
        RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>  {

    private int countOfTeamsItems;
    private List<TeamNamesModel> teams;
    private List<TeamNamesModel> allTeams;

    public TeamsAdapter (List<TeamNamesModel> teams, Context context, List<TeamNamesModel> allTeams) {

        this.countOfTeamsItems = teams.size();
        this.teams = new ArrayList<>(teams);
        this.allTeams = new ArrayList<>(allTeams);
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

    public void addItem() {
        if(countOfTeamsItems <=20) {
            Random random = new Random();
            int i = random.nextInt(54);
            while (teams.contains(allTeams.get(i))) {
                i = random.nextInt(54);
            }
            teams.add(allTeams.get(i));
            countOfTeamsItems++;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return countOfTeamsItems;
    }

    public List<TeamNamesModel> getTeams() {
        return teams;
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

            teamTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    updateTeamName();
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

            void updateTeamName(){
            Random random = new Random();
            int i = random.nextInt(54);
            while(teams.contains(allTeams.get(i))){i = random.nextInt(54);}
            teams.set(getAdapterPosition(), allTeams.get(i));
            teamTextView.setText(allTeams.get(i).getTeamName());
            notifyDataSetChanged();
           }
    }
}
