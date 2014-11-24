/**
 * File engine.database/DBHelper.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package engine.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import it.zerocool.series.R;

/**
 * Favorites Database Open Helper class. It is a singleton design pattern
 * 
 * @author Marco Battisti
 * 
 */
public class FavoriteDBHelper extends SQLiteOpenHelper {

	private Context context;
	private InputStream is;
	private BufferedReader br;
	private static FavoriteDBHelper singleton;
	private static SQLiteDatabase writableDB;

	/**
	 * Helper private constructor
	 * 
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	private FavoriteDBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	/**
	 * Helper private Constructor
	 * 
	 * @param context
	 *            is the application context
	 */
	private FavoriteDBHelper(Context context) {
		super(context, FavoriteDBManager.DB_NAME, null,
				FavoriteDBManager.DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			// Read SQLite commitment from file
			is = context.getResources().openRawResource(R.raw.build_db);
			br = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
			// Begin safety transaction
			db.beginTransaction();
			String data = br.readLine();
			while (data != null) {
				db.execSQL(data);
				data = br.readLine();
			}
			db.setTransactionSuccessful();
		} catch (IOException e) {
			Log.e("I/O ERROR", e.getMessage());
			e.printStackTrace();
		} catch (SQLiteException e) {
			Log.e("DATABASE ERROR", e.getMessage());
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/**
	 * Returns an instance of FavoriteDBHelper, to be shared between activities
	 * 
	 * @param context
	 * @return an instance of database
	 */
	public static FavoriteDBHelper getInstance(Context context) {
		if (singleton == null) {
			singleton = new FavoriteDBHelper(context);
		}
		return singleton;
	}

	/**
	 * Returns a writable instance of DB
	 * 
	 * @return a writable instance of DB
	 */
	public SQLiteDatabase getWritableDB() {
		if (writableDB == null || !writableDB.isOpen()) {
			writableDB = this.getWritableDatabase();
		}
		return writableDB;
	}

	/**
	 * Close the DB
	 */
	@Override
	public void close() {
		super.close();
		if (writableDB != null) {
			writableDB.close();
			writableDB = null;
		}
	}
}
