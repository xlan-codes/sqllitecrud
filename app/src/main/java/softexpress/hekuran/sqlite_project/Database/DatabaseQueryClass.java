package softexpress.hekuran.sqlite_project.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import softexpress.hekuran.sqlite_project.Features.CreatePerson.Person;
import softexpress.hekuran.sqlite_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DatabaseQueryClass {

    private Context context;

    public DatabaseQueryClass(Context context){
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertPeople(Person person){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PERSON_FIRST_NAME, person.getEmri());
        contentValues.put(Config.COLUMN_PERSON_LAST_NAME, person.getMbiemri());
        contentValues.put(Config.COLUMN_PERSON_PHONE, person.getTelefon());
        contentValues.put(Config.COLUMN_PERSON_BIRTH_PLACE, person.getVendlindja());
        contentValues.put(Config.COLUMN_PERSON_BIRTHDATE, String.valueOf(person.getDatelindja()));
        contentValues.put(Config.COLUMN_PERSON_GENDER, person.getGjinia());
        contentValues.put(Config.COLUMN_PERSON_EMPLOYED, person.isIpunesuar());
        contentValues.put(Config.COLUMN_PERSON_MARTIAL_STATUS, person.getGjendjamartesore());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_PERSON, null, contentValues);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Person> getAllPeople(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_PERSON, null, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Person> personList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PERSON_ID));
                        String first_name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_FIRST_NAME));
                        String last_name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_LAST_NAME));
                        String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_PHONE));
                        String birthdate = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_BIRTHDATE));
                        String birthplace = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_BIRTH_PLACE));
                        int employed = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PERSON_EMPLOYED));
                        String sex = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_GENDER));
                        String martialStatus = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_MARTIAL_STATUS));


                        personList.add(new Person(id, first_name, last_name, new Date(birthdate), phone, sex, employed, martialStatus, birthplace));
                    }   while (cursor.moveToNext());

                    return personList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }



    public List<Person> getPeople(String filter){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_PERSON, null,
                    "first_name like " + "'%"+filter+"%'", null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Person> personList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PERSON_ID));
                        String first_name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_FIRST_NAME));
                        String last_name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_LAST_NAME));
                        String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_PHONE));
                        String birthdate = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_BIRTHDATE));
                        String birthplace = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_BIRTH_PLACE));
                        int employed = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PERSON_EMPLOYED));
                        String sex = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_GENDER));
                        String martialStatus = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_MARTIAL_STATUS));


                        personList.add(new Person(id, first_name, last_name, new Date(birthdate), phone, sex, employed, martialStatus, birthplace));
                    }   while (cursor.moveToNext());

                    return personList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public Person getPeopleByRegNum(long registrationNum){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        Person person = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_PERSON, null,
                    Config.COLUMN_PERSON_ID + " = ? ", new String[]{String.valueOf(registrationNum)},
                    null, null, null);

            if(cursor.moveToFirst()){

                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PERSON_ID));
                String first_name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_FIRST_NAME));
                String last_name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_LAST_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_PHONE));
                String birthdate = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_BIRTHDATE));
                String birthplace = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_BIRTH_PLACE));
                int employed = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PERSON_EMPLOYED));
                String sex = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_GENDER));
                String martialStatus = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PERSON_MARTIAL_STATUS));


                person = new Person(id, first_name, last_name, new Date(birthdate), phone, sex, employed, martialStatus, birthplace);
            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return person;
    }

    public long updatePersonInfo(Person person){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PERSON_FIRST_NAME, person.getEmri());
        contentValues.put(Config.COLUMN_PERSON_LAST_NAME, person.getMbiemri());
        contentValues.put(Config.COLUMN_PERSON_PHONE, person.getTelefon());
        contentValues.put(Config.COLUMN_PERSON_BIRTH_PLACE, person.getVendlindja());
        contentValues.put(Config.COLUMN_PERSON_BIRTHDATE, String.valueOf(person.getDatelindja()));
        contentValues.put(Config.COLUMN_PERSON_GENDER, person.getGjinia());
        contentValues.put(Config.COLUMN_PERSON_EMPLOYED, person.isIpunesuar());
        contentValues.put(Config.COLUMN_PERSON_MARTIAL_STATUS, person.getGjendjamartesore());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_PERSON, contentValues,
                    Config.COLUMN_PERSON_ID + " = ? ",
                    new String[] {String.valueOf(person.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deletePersonByRegNum(long registrationNum) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_PERSON,
                                    Config.COLUMN_PERSON_ID + " = ? ",
                                    new String[]{ String.valueOf(registrationNum)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }

    public boolean deleteAllPeople(){
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {

            sqLiteDatabase.delete(Config.TABLE_PERSON, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_PERSON);

            if(count==0)
                deleteStatus = true;

        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deleteStatus;
    }

}