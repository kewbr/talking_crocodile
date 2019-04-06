package by.fabric.kewbr.talking_crocodile.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.fabric.kewbr.talking_crocodile.Model.ProgressModel;
import by.fabric.kewbr.talking_crocodile.R;

public class StartRoundAdapter extends
        RecyclerView.Adapter<StartRoundAdapter.StartRoundViewHolder> {

    private List<ProgressModel> progresses;
    private int countOfTeamItems;

    public StartRoundAdapter (List<ProgressModel> progressFromDatabase) {

        this.progresses = new ArrayList<>(progressFromDatabase);
        this.countOfTeamItems = this.progresses.size();

    }

    @NonNull
    @Override
    public StartRoundAdapter.StartRoundViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int idLayoutTeamAndPointsCell = R.layout.team_and_points_cell;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(idLayoutTeamAndPointsCell, viewGroup, false);

        return new StartRoundAdapter.StartRoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StartRoundAdapter.StartRoundViewHolder startRoundViewHolder, int i) {
        //MARK – Here update element of recyclerView
        startRoundViewHolder.bind(this.progresses.get(i).getTeamName(), this.progresses.get(i).getGuessedCount());
    }

    @Override
    public int getItemCount() {
        return countOfTeamItems;
    }

    class StartRoundViewHolder extends RecyclerView.ViewHolder {

        TextView teamNameTextView;
        TextView guessedCountTextView;

        public StartRoundViewHolder(View itemView) {
            super(itemView);

            teamNameTextView = itemView.findViewById(R.id.teamName);
            guessedCountTextView = itemView.findViewById(R.id.teamPoints);

        }

        void bind(String currentTeam, Long points) {

            teamNameTextView.setText(currentTeam);
            guessedCountTextView.setText(String.valueOf(points));
        }

    }
}
