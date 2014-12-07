package ar.com.preventa.data.entity;


public class Notes {
    
    public static final String DATABASE_NAME = "Monitor";
    public static final String DATABASE_TABLE = "Clientes";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_CREATE =
        "create table notes (_id integer primary key autoincrement, "
                + "title text not null, " +
                  "body text not null);";
    
    public static final String COL_TITLE = "title";
    public static final String COL_BODY = "body";
    
    private int id;
    private String title;
    private String body;
    
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setBody(String body) {
        this.body = body;
    }
}
