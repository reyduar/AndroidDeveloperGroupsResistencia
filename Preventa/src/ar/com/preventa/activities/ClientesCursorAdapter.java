package ar.com.preventa.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import ar.com.preventa.db.PreventaDB.ClientesTable;

public class ClientesCursorAdapter extends SimpleCursorAdapter {
    private Cursor c;
    private Context context;
    private ContentResolver mContent;  

    public ClientesCursorAdapter(Context context, int layout, Cursor c,
            String[] from, int[] to) {

        super(context, layout, c, from, to);
        this.c = c;
        this.context = context;
        this.mContent = context.getContentResolver();
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see android.widget.SimpleCursorAdapter#bindView(android.view.View,
android.content.Context, android.database.Cursor)
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // TODO Auto-generated method stub
        //ImageView imageView = (ImageView)view.findViewById(R.id.contact_image);

//        int id = this.c.getColumnIndex(Phones._ID);
//        Uri uri = ContentUris
//                .withAppendedId(People.CONTENT_URI, this.c.getLong(id));
//
//        Bitmap bitmap = People.loadContactPhoto(this.context, uri,
//R.drawable.icon, null);

        //imageView.setImageBitmap(bitmap);

        super.bindView(view, context, cursor);
    }
    
    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint)
    {
           //Integer i = 0;
           
           //i=i+5;
            //if (constraint == null)
            //        return myDatabase.searchByTokenReturnName(" ");
            //return myDatabase.searchByTokenReturnName(constraint.toString());  //returns a cursor with the search results
           return mContent.query(ClientesTable.CONTENT_URI, new String[] {
										ClientesTable._ID, ClientesTable.RAZON_SOCIAL,
											ClientesTable.DOMICILIO }, ClientesTable.RAZON_SOCIAL
													+ " LIKE ?" , new String[] { "%" + constraint + "%" }, ClientesTable.DEFAULT_SORT_ORDER);
           
          // return null;
    }
}
