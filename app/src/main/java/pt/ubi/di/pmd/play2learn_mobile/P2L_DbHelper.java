package pt.ubi.di.pmd.play2learn_mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class P2L_DbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "P2L.db";
    private static final int DATABASE_VERSION = 1;
    private static final  String TABLE_NAME1 = "USERS";
    private static final  String COLUMN_ID1 = "_id1";
    private static final  String COLUMN_NAME1 = "Name_User";
    private static final  String COLUMN_EMAIL = "Email";
    private static final  String COLUMN_PASSWORD = "Password";
    private static final  String COLUMN_PROFILEPIC = "ProfilePic";
    private static final  String COLUMN_BIBLIO = "Biblio";
    private static final  String TABLE_NAME2 = "Subjects";
    private static final  String COLUMN_ID2 = "_id2";
    private static final  String COLUMN_NAME2 = "Name_Subject";
    private static final  String COLUMN_IMAGE = "Image";
    private static final  String TABLE_NAME3 = "Questions-PT";
    private static final  String COLUMN_ID3 = "_id3";
    private static final  String COLUMN_IDSUBJECT = "idSubject";
    private static final  String COLUMN_QUESTIONPT = "QuestionPT";
    private static final  String COLUMN_DIFICULTYPT = "DificultyPT";
    private static final  String COLUMN_RIGHT_ANSWERPT = "RightAnswerPT";
    private static final  String COLUMN_WRONG1PT = "Wrong1PT";
    private static final  String COLUMN_WRONG2PT = "Wrong2PT";
    private static final  String COLUMN_WRONG3PT = "Wrong3PT";
    private static final  String TABLE_NAME4 = "Questions-ENG";
    private static final  String COLUMN_ID4 = "_id4";
    private static final  String COLUMN_ID_SUBJECTENG = "idSubjectENG";
    private static final  String COLUMN_QUESTIONENG = "QuestionENG";
    private static final  String COLUMN_DIFICULTYENG = "DificultyENG";
    private static final  String COLUMN_RIGHT_ANSWERENG = "RightAnswerENG";
    private static final  String COLUMN_WRONG1ENG = "Wrong1ENG";
    private static final  String COLUMN_WRONG2ENG = "Wrong2ENG";
    private static final  String COLUMN_WRONG3ENG = "Wrong3ENG";
    private static final  String TABLE_NAME5 = "UserResults";
    private static final  String COLUMN_ID5 = "_id5";
    private static final  String COLUMN_ID_USER = "IDUser";
    private static final  String COLUMN_SUBJECT_ID = "SubjectID";
    private static final  String COLUMN_SCORE = "Score";
    private static final  String COLUMN_NUMCORRECTANS = "NumCorrectAns";
    private static final  String COLUMN_NUMWRONGANS = "NumWrongAns";
    private static final  String COLUMN_TIMETOSOLVE = "TimeToSolve";
    private static final  String TABLE_NAME6 = "UserFriends";
    private static final  String COLUMN_ID6 = "_id6";
    private static final  String COLUMN_IDUSER = "IDUser";
    private static final  String COLUMN_IDFRIEND = "IDFriend";

    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInBytes;

    //cria a base de dados
    public P2L_DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 =
                "CREATE TABLE " + TABLE_NAME1 + " (" + COLUMN_ID1 + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME1 + " TEXT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_PASSWORD + " TEXT, " +
                        COLUMN_PROFILEPIC + " BLOB, " + COLUMN_BIBLIO + "TEXT);";
        String query2 =
                "CREATE TABLE " + TABLE_NAME2 + " (" + COLUMN_ID2 + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME2 + " TEXT, " + COLUMN_IMAGE + " BLOB);";
        String query3 =
                "CREATE TABLE " + TABLE_NAME3 + " (" + COLUMN_ID3 + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_IDSUBJECT + " INTEGER, " + COLUMN_QUESTIONPT + " TEXT, " + COLUMN_DIFICULTYPT +
                        " TEXT, " + COLUMN_RIGHT_ANSWERPT + " TEXT, " + COLUMN_WRONG1PT + " TEXT, " +
                        COLUMN_WRONG2PT + " TEXT, " + COLUMN_WRONG3PT + " TEXT);";
        String query4 =
                "CREATE TABLE " + TABLE_NAME4 + " (" + COLUMN_ID4 + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ID_SUBJECTENG + " INTEGER, " + COLUMN_QUESTIONENG + " TEXT, " + COLUMN_DIFICULTYENG +
                        " TEXT, " + COLUMN_RIGHT_ANSWERENG + " TEXT, " + COLUMN_WRONG1ENG + " TEXT, " +
                        COLUMN_WRONG2ENG + " TEXT, " + COLUMN_WRONG3ENG + " TEXT);";
        String query5 =
                "CREATE TABLE " + TABLE_NAME5 + " (" + COLUMN_ID5 + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ID_USER + " INTEGER, " + COLUMN_SUBJECT_ID + " INTEGER, " + COLUMN_SCORE +
                        " INTEGER, " + COLUMN_NUMCORRECTANS + " INTEGER, " + COLUMN_NUMWRONGANS + " INTEGER, " +
                        COLUMN_TIMETOSOLVE + " TEXT);";
        String query6 =
                "CREATE TABLE " + TABLE_NAME6 + " (" + COLUMN_ID6 + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_IDUSER + " INTEGER, " + COLUMN_IDFRIEND + " INTEGER);";

        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
        db.execSQL(query5);
        db.execSQL(query6);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME5);
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME6);
        onCreate(db);
    }

    public void addUser(UserModel userModel){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = userModel.getImage();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);

            imageInBytes = objectByteArrayOutputStream.toByteArray();
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_NAME1, userModel.getName());
            cv.put(COLUMN_EMAIL, userModel.getEmail());
            cv.put(COLUMN_PASSWORD, userModel.getPassword());
            cv.put(COLUMN_PROFILEPIC, imageInBytes);
            cv.put(COLUMN_BIBLIO, userModel.getBiblio());

            long result = db.insert(TABLE_NAME1, null, cv);
            if (result == -1){
                Toast.makeText(context, "Failed to add user", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(context, "User added successfully", Toast.LENGTH_SHORT).show();
                db.close();
            }
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<UserModel> getAllUserData(){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<UserModel> UserList = new ArrayList<>();

            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);
            if (cursor.getCount() > 0){
                while (cursor.moveToNext()){
                    String name = cursor.getString(1);
                    String email = cursor.getString(2);
                    String password = cursor.getString(3);
                    byte[] imagebytes = cursor.getBlob(4);
                    Bitmap objBitmap = BitmapFactory.decodeByteArray(imagebytes, 0, imagebytes.length);
                    String biblio = cursor.getString(5);

                    UserList.add(new UserModel(name, email, password, objBitmap, biblio));
                }

                return UserList;
            } else {
                Toast.makeText(context, "No users exists in Database", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void addSubjects(SubjectsModel subjectsModel){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = subjectsModel.getImage();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);

            imageInBytes = objectByteArrayOutputStream.toByteArray();
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_NAME2, subjectsModel.getName_Subject());
            cv.put(COLUMN_IMAGE, imageInBytes);

            long result = db.insert(TABLE_NAME2, null, cv);
            if (result == -1){
                Toast.makeText(context, "Failed to add subject", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(context, "Subject added successfully", Toast.LENGTH_SHORT).show();
                db.close();
            }
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<SubjectsModel> getAllSubjectsData(){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<SubjectsModel> SubjectList = new ArrayList<>();

            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2, null);
            if (cursor.getCount() > 0){
                while (cursor.moveToNext()){
                    String nameSubject = cursor.getString(1);
                    byte[] imagebytes = cursor.getBlob(2);
                    Bitmap objBitmap = BitmapFactory.decodeByteArray(imagebytes, 0, imagebytes.length);

                    SubjectList.add(new SubjectsModel(nameSubject, objBitmap));
                }

                return SubjectList;
            } else {
                Toast.makeText(context, "No Subjects exists in Database", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
