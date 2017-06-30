package cc.a5156.xdkp.common.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cc.a5156.xdkp.common.base.App;
import cc.a5156.xdkp.common.sqlite.SQLiteHelper;

/**
 * Created by Administrator on 2017/6/9.
 */

public class SQLiteUtil {

    public static String get(String key) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = new SQLiteHelper(App.getContext()).getReadableDatabase();
            cursor = db.rawQuery("select * from temp where key=? ", new String[]{key});
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex("value"));
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return null;
    }

    public static void save(String key, String value) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = new SQLiteHelper(App.getContext()).getReadableDatabase();
            cursor = db.rawQuery("select * from temp where key=? ", new String[]{key});
            ContentValues contentValues = new ContentValues();
            contentValues.put("key", key);
            contentValues.put("value", value);
            if (cursor.moveToFirst()) {
                db.update("temp", contentValues, "key=?", new String[]{key});
            } else {
                db.insert("temp", null, contentValues);
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }

    public static void save(String key, boolean value) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = new SQLiteHelper(App.getContext()).getReadableDatabase();
            cursor = db.rawQuery("select * from temp where key=? ", new String[]{key});
            ContentValues contentValues = new ContentValues();
            contentValues.put("key", key);
            contentValues.put("value", value);
            if (cursor.moveToFirst()) {
                db.update("temp", contentValues, "key=?", new String[]{key});
            } else {
                db.insert("temp", null, contentValues);
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }
}
