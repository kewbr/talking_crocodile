package by.fabric.kewbr.talking_crocodile.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainDBHelper extends SQLiteOpenHelper {

    private static MainDBHelper sInstance;

    private static String DB_NAME = "word_base.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;


    public static synchronized MainDBHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new MainDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    private MainDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }
}


//
//	private static final String PATH = "jdbc:sqlite:E:\\JTest\\folder\\word_base.db";
//	private static WordsBase instance = null;
//
//	private int index;
//	private String currentTopic;
//
//	private Connection connection;
//
//	private List<Word> wordsList;
//
//	public static synchronized WordsBase getInstance() throws SQLException{
//		if (instance == null)
//			instance = new WordsBase();
//		return instance;
//	}
//
//	// mixing an list
//	private List<Word> mix(List<Word> array){
//	    Random rnd = ThreadLocalRandom.current();
//	    for (int i = array.size() - 1; i > 0; i--)
//	    {
//	      int tempIndex = rnd.nextInt(i + 1);
//	      // Simple swap
//	      Word tempWord = array.get(tempIndex);
//	      array.set(tempIndex, array.get(i));
//	      array.set(i, tempWord);
//	    }
//	    return array;
//	  }
//
//	private WordsBase() throws SQLException{
//		DriverManager.registerDriver(new JDBC());
//		this.connection = DriverManager.getConnection(PATH);
//	}
//	// Will collect list of words by selected topic
//	public void getTopicWords(final String topic){
//		try(Statement statement = this.connection.createStatement()){
//			List<Word> words = new ArrayList<Word>();
//			ResultSet resultSet = statement.executeQuery(String.format("SELECT word FROM words WHERE topic='%s'", topic));
//			while(resultSet.next()) {
//				words.add(new Word(resultSet.getString("word")));
//			}
//			// randomizing if no � exception and index = 0
//						this.wordsList = this.mix(words);
//			this.index = 0;
//			this.currentTopic = topic;
//		} catch(SQLException e) {
//			e.printStackTrace();
//			this.wordsList = Collections.emptyList();
//			}
//		}
//
//	// return list of words (default 3 words)
//	public List<Word> getWords(int n){
//		List<Word> temp = new ArrayList<Word>();
//		for(int i = 0;i < n; i++, this.index++) {
//			try {
//				temp.add(this.wordsList.get(index));
//			} catch(IndexOutOfBoundsException e) {
//				i--;
//				this.getTopicWords(this.currentTopic);
//			}
//		}
//		return temp;
//	}
//	public List<Word> getWords(){
//		return getWords(3);
//	}
