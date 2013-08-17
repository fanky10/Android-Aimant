package com.mawape.aimant.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RepositoryOpenHelper extends SQLiteOpenHelper {
	private static final String TAG = "CMassDatabase";
	protected final SQLiteDatabase database;

	// table names and creates (:
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "aimant_android.sql";
	private static final String T_NEGOCIOS = "peso_altura_persona";
	private static final String[] CREATE_TABLES = new String[]{
		"CREATE TABLE negocios (id integer primary key AUTOINCREMENT, nombre text,direccion text,img_path text, id_categoria integer unsigned)",
		"CREATE TABLE categorias (id integer primary key AUTOINCREMENT, nombre text)"
	};
	

	public RepositoryOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		database = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for(String st: CREATE_TABLES){
			db.execSQL(st);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + T_NEGOCIOS);
		onCreate(db);
	}

}