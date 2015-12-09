package com.example.yms.myapplication;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    myDBHelper myHelper;
    EditText editName,editNumber,editNameResult,editNumberResult;
    Button btnInit,btnInput,btnModify,btnDelete,btnCheck;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = (EditText)findViewById(R.id.editName);
        editNumber = (EditText)findViewById(R.id.editNumber);
        editNameResult = (EditText)findViewById(R.id.editNameResult);
        editNumberResult = (EditText)findViewById(R.id.editNumberResult);

        btnInit = (Button)findViewById(R.id.btnInit);
        btnInput = (Button)findViewById(R.id.btnInput);
        btnModify = (Button)findViewById(R.id.btnModify);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnCheck = (Button)findViewById(R.id.btnCheck);

        myHelper = new myDBHelper(this);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.btnInit:
                dbInit();
                break;
            case R.id.btnInput:
                dbInput();
                dbCheck();
                break;

            case R.id.btnModify:
                dbModify();
                dbCheck();
                break;
            case R.id.btnDelete:
                dbDelete();
                dbCheck();
                break;
            case R.id.btnCheck:
                dbCheck();
                break;
        }
    }
    private void dbInit(){
        sqlDB = myHelper.getWritableDatabase();
        myHelper.onUpgrade(sqlDB,1,2);
        Toast.makeText(getApplicationContext(),"초기완료",Toast.LENGTH_SHORT).show();
        sqlDB.close();
    }
    private void dbInput(){
        sqlDB = myHelper.getWritableDatabase();
        sqlDB.execSQL("INSERT INTO groupTBL VALUES ('" + editName.getText().toString() + "'," + editNumber.getText().toString() + ");");
        sqlDB.close();
        Toast.makeText(getApplicationContext(),"입력완료",Toast.LENGTH_SHORT).show();
    }
    private void dbModify(){
        sqlDB = myHelper.getWritableDatabase();
        sqlDB.execSQL("UPDATE groupTBL SET gNumber = " + editNumber.getText().toString() + " WHERE gName = '"+editName.getText().toString()+"'");
        sqlDB.close();
        Toast.makeText(getApplicationContext(), "수정완료", Toast.LENGTH_SHORT).show();
    }
    private void dbDelete(){
        sqlDB = myHelper.getWritableDatabase();
        sqlDB.execSQL("DELETE FROM groupTBL WHERE gName = '"+editName.getText().toString()+"'");
        sqlDB.close();
        Toast.makeText(getApplicationContext(),"삭제완료",Toast.LENGTH_SHORT).show();
    }
    private void dbCheck(){
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;",null);
        String strNames = "그룹 인원"+"\r\n" + "----------"+"\r\n";
        String strNumber = "  인원  "+"\r\n" + "----------"+"\r\n";
        while(cursor.moveToNext()){
            strNames += cursor.getString(0) + "\r\n";
            strNumber += cursor.getString(1) + "\r\n";
        }
        editNameResult.setText(strNames);
        editNumberResult.setText(strNumber);
        cursor.close();
        sqlDB.close();
    }
}
