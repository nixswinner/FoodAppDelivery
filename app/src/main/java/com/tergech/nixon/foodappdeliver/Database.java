package com.tergech.nixon.foodappdeliver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	public Database(Context context) {
		super(context, "demoappdelivery_db", null, 1);// modify here
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// modify this query to create your own table
		/*String sql = "CREATE TABLE  students"//IF NOT EXISTS,students
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " name TEXT NOT NULL, " + " email TEXT NOT NULL,"
				+ " course TEXT NOT NULL," + " age TEXT NOT NULL"+ "phone TEXT NOT NULL)";//,"+ "phone TEXT NOT NULL*/
		String sql = "CREATE TABLE  orders"//IF NOT EXISTS,students
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " food_ordered TEXT NOT NULL, " + " cost TEXT NOT NULL,"
				 + " date TEXT NOT NULL)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*String sql = "DROP TABLE IF EXISTS students";// modify to suit your table //students*/
		String sql = "DROP TABLE IF EXISTS orders";
		db.execSQL(sql);
	}
	//getting calories based on now
	public String getOrders(String tdate)
	{
		String food_ordered = null;
		SQLiteDatabase db = this.getReadableDatabase();
		String sql ="SELECT * FROM orders WHERE date='"+tdate+"'";// modify here to reflect your table
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				 food_ordered = cursor.getString(1);
				//total_calo+=Integer.parseInt(name);
			} while (cursor.moveToNext()); // modify here
		}
		return food_ordered;

	}



	//getting food_ordeded
	public String get_food_Ordered(String tdate)
	{
		String food_name="";
		SQLiteDatabase db = this.getReadableDatabase();
		String sql ="SELECT * FROM orders WHERE date='"+tdate+"'";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				 food_name = cursor.getString(1);
                 food_name=""+food_name;

			} while (cursor.moveToNext()); // modify here
		}
		return food_name;

	}
	//getting food_ordeded
	public String get_food_cost(String tdate)
	{
		String food_name="";
		SQLiteDatabase db = this.getReadableDatabase();
		String sql ="SELECT * FROM orders WHERE date='"+tdate+"'";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				food_name = cursor.getString(2);
				food_name=""+food_name;

			} while (cursor.moveToNext()); // modify here
		}
		return food_name;

	}

	//getting food_ordeded
	public String get_food_order_date(String tdate)
	{
		String food_name="";
		SQLiteDatabase db = this.getReadableDatabase();
		String sql ="SELECT * FROM orders WHERE date='"+tdate+"'";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				food_name = cursor.getString(3);
				food_name=""+food_name;

			} while (cursor.moveToNext()); // modify here
		}
		return food_name;

	}




	public void save_orders(String food_ordered, String cost, String _date)// Modify here to

	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("food_ordered", food_ordered);// modify here
		values.put("cost",cost);// modify here
		values.put("date", _date);// modify here
		db.insert("orders", null, values);// modify here students
		db.close();
	}

	//	Saving data in the database
	public void save_points(String food_ordered, String cost, String _date)// Modify here to

	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("food_ordered", food_ordered);// modify here
		values.put("cost",cost);// modify here
		values.put("date", _date);// modify here
		db.insert("orders", null, values);// modify here students
		db.close();
	}

	/**
	 * Fetches all records from the database and return an arraylist of type
	 * specified
	 */


//	Counts the records in the database
	public int count() {
		SQLiteDatabase db = this.getReadableDatabase();
		//String sql = "SELECT id,name,course FROM students";// modify here
		String sql = "SELECT * FROM orders";
		Cursor cursor = db.rawQuery(sql, null);
		return cursor.getCount();
	}

	/*
	 * Deletes a record with specified ID
	 */
	public void delete(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("users", "id=" + id, null); // Modify
		db.close();
	}

	/*
	 * Updates a record with specified fields
	 */
	public void update(String names, String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "UPDATE orders set names='" + names + "' WHERE id=" + id;
		db.rawQuery(query, null);
		db.close();
	}
}
