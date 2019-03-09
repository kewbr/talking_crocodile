package by.fabric.kewbr.talking_crocodile.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import by.fabric.kewbr.talking_crocodile.Database.MainDBHelper;
import by.fabric.kewbr.talking_crocodile.Database.Word;
import by.fabric.kewbr.talking_crocodile.Model.WordModel;
import by.fabric.kewbr.talking_crocodile.R;

public class RoundWordsAdapter extends
        RecyclerView.Adapter<RoundWordsAdapter.RoundWordsViewHolder> {

    private int countOfWordsItems;
    private MainDBHelper dbHelper;
    private SQLiteDatabase mDb;
    Cursor cursor;
    private ArrayList<WordModel> list;

    public RoundWordsAdapter (int countOfWordsItems, Context context) {

        this.countOfWordsItems = countOfWordsItems;
        list = new ArrayList<WordModel>();

        dbHelper = new MainDBHelper(context);
        try {
            dbHelper.updateDataBase();
        } catch (IOException ex) {
            Log.i("err", ex.getLocalizedMessage());
        }
        mDb = dbHelper.getWritableDatabase();

        cursor = mDb.rawQuery("SELECT * FROM words", null);
        cursor.moveToFirst();
        Log.i("DB", cursor.getString(1) );
        for (int j =0; j<countOfWordsItems;j++){
            list.add(j,new WordModel(cursor.getString(1),true));
            cursor.moveToNext();
        }
        Log.i("Word list count", "" + list.size() );
    }

    @NonNull
    @Override
    public RoundWordsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int idLayoutWordCheckCell = R.layout.word_check_cell;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(idLayoutWordCheckCell, viewGroup, false);

        return new RoundWordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoundWordsViewHolder roundWordsViewHolder, int i) {
        //MARK – Here update element of recyclerView
        //roundWordsViewHolder.bind("Привет скрам-мастер!", true);
        roundWordsViewHolder.bind(list.get(i).getWord(),list.get(i).getGuessed());

    }

    @Override
    public int getItemCount() {
        return countOfWordsItems;
    }

    class RoundWordsViewHolder extends RecyclerView.ViewHolder {

        TextView wordTextView;
        CheckBox wordCheckBox;
        WordModel word;
        public RoundWordsViewHolder(View itemView) {
            super(itemView);

            wordTextView = itemView.findViewById(R.id.word);
            wordCheckBox = itemView.findViewById(R.id.check_box_word);

        }

        void bind(String currentWord, boolean checked) {
            word = new WordModel(currentWord,checked);
            wordTextView.setText(currentWord);
            wordCheckBox.setChecked(checked);
        }

    }
}
