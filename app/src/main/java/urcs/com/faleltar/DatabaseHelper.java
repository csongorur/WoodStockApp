package urcs.com.faleltar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ur on 03.05.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "store.db";
    public static final String TABLE_NAME = "store_table";
    public static final String COL_1 = "_id";
    public static final String COL_2 = "date";
    public static final String COL_3 = "size";
    public static final String COL_4 = "type";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertData(String date, String size, String type)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, date);
        contentValues.put(COL_3, size);
        contentValues.put(COL_4, type);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return res;
    }

    public Cursor getOneData(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + "=?", new String[]{id});

        if (cursor != null)
        {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public boolean updateData(String id, String date, String size, String type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, date);
        contentValues.put(COL_3, size);
        contentValues.put(COL_4, type);

        db.update(TABLE_NAME, contentValues, COL_1 + "=?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, COL_1 + "=?", new String[]{id});
    }

    public String getTotalDeszka()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_3 + ") FROM " + TABLE_NAME + " WHERE " + COL_4 + " = 1", null);

        if (cursor != null)
        {
            cursor.moveToFirst();
        }

        return cursor.getString(0);
    }

    public String getTotalFoszni()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_3 + ") FROM " + TABLE_NAME + " WHERE " + COL_4 + " = 2", null);

        if (cursor != null)
        {
            cursor.moveToFirst();
        }

        return cursor.getString(0);
    }
}
