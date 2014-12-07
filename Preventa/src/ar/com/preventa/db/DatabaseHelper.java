/*------------------------------------------------------------------------------
 **     Ident: Innovation en Inspiration > Google Android 
 **    Author: rene
 ** Copyright: (c) Jan 22, 2009 Sogeti Nederland B.V. All Rights Reserved.
 **------------------------------------------------------------------------------
 ** Sogeti Nederland B.V.            |  No part of this file may be reproduced  
 ** Distributed Software Engineering |  or transmitted in any form or by any        
 ** Lange Dreef 17                   |  means, electronic or mechanical, for the      
 ** 4131 NJ Vianen                   |  purpose, without the express written    
 ** The Netherlands                  |  permission of the copyright holder.
 *------------------------------------------------------------------------------
 *
 *   This file is part of OpenGPSTracker.
 *
 *   OpenGPSTracker is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   OpenGPSTracker is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with OpenGPSTracker.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package ar.com.preventa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import ar.com.preventa.db.PreventaDB.ClientesTable;
import ar.com.preventa.db.PreventaDB.ProductosTable;


/**
 * Class to hold bare-metal database operations exposed as functionality blocks To be used by database adapters, like a content provider, that implement a required functionality set
 * 
 * @version $Id: DatabaseHelper.java 524 2010-05-09 08:46:17Z rcgroot $
 * @author rene (c) Jan 22, 2009, Sogeti B.V.
 */


public class DatabaseHelper extends SQLiteOpenHelper
{
   private final static String TAG = "Preventa.DatabaseHelper";
   private static SQLiteDatabase db;
   private static Context mContext = null;
   private static DatabaseHelper instance;
   
   private DatabaseHelper(){
       super(mContext, PreventaDB.DATABASE_NAME, null, PreventaDB.DATABASE_VERSION);      
       Log.i(TAG, "Creating or opening database [ " + PreventaDB.DATABASE_NAME + " ].");
   }
   
   public static DatabaseHelper getInstance(Context context){
       if(instance == null){
           mContext = context;
    	   instance = new DatabaseHelper();
           try{
               Log.i(TAG, "Creating or opening the database [ " + PreventaDB.DATABASE_NAME + " ].");
               db = instance.getWritableDatabase();
           }catch(SQLiteException se){
               Log.e(TAG, "Cound not create and/or open the database [ " + PreventaDB.DATABASE_NAME + " ] that will be used for reading and writing.", se);
           }
       }
       return instance;
   }

   /*
    * (non-Javadoc)
    * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
    */
   @Override
   public void onCreate( SQLiteDatabase db )
   {
     //db.execSQL( ClientesTable.CREATE_STATEMENT );
     //db.execSQL( ProductosTable.CREATE_STATMENT );
   }

   
   @Override
   public void onUpgrade( SQLiteDatabase db, int current, int targetVersion )
   {
      Log.i( TAG, "Upgrading db from "+current+" to "+targetVersion );
   }
   
   public long insert(String table, ContentValues values){
       return db.insert(table, null, values);
   }
   
   public Cursor get(String table, String[] columns){
       return db.query(table, columns, null, null, null, null, null);
   }
   
  // query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
   public Cursor get(String table, String[] columns,String selection, String[] selectionArgs,String groupBy,String having,String orderBy){
       return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
   }
   
   public Cursor get(String table, String[] columns, long id){
       Cursor cursor =db.query(true, table, columns, BaseColumns._ID + "=" + id, null, null, null, null, null);
       if (cursor != null) {
           cursor.moveToFirst();
       }
       return cursor;
   }
   
   public int delete(String table) {
       return db.delete(table, "1", null);
   }
   
   public int delete(String table, long id) {
       return db.delete(table, BaseColumns._ID + "=" + id, null);
   }
   
   public int update(String table, long id, ContentValues values) {
       return db.update(table, values, BaseColumns._ID + "=" + id, null);
   }
   
   public void close(){
       if(instance != null){
           Log.i(TAG, "Closing the database [ " + PreventaDB.DATABASE_NAME + " ].");
           db.close();
           instance = null;
       }
   }
}
