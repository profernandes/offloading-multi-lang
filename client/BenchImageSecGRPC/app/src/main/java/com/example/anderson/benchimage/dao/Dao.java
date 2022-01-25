package com.example.anderson.benchimage.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.anderson.benchimage.util.DatabaseManager;

public abstract class Dao {
	protected SQLiteDatabase database;
	private final DatabaseManager databaseManager;

	public Dao(Context con) {
		databaseManager = new DatabaseManager(con);
	}

	public void openDatabase() {
		database = databaseManager.getWritableDatabase();
	}

	public void closeDatabase() {
		database.close();
	}
}