package com.example.yms.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends Activity {

    TextView dateView;

    Button SaveBtn;
    View deleteDialog, DatePickerDialog;
    DatePicker dp;
    EditText edtDiary;
    String fileName;
    String filePath, sdCardPath;
    String content;

    //폰트의 크기를 고정시킴
    private static final int BigFontSize = 30;
    private static final int FontSize = 20;
    private static final int SmallFontSize = 10;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Activity_Main
        dateView = (TextView) findViewById(R.id.DayTextView);
        edtDiary = (EditText) findViewById(R.id.diaryEditText);
        SaveBtn = (Button) findViewById(R.id.SaveBtn);

        //sdcard
        String sdCard;
        sdCard = Environment.getExternalStorageState();

        //sdcard mount or unmount
        if(sdCard.equals(Environment.MEDIA_MOUNTED))
            sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/diary";
        else
            sdCardPath = Environment.MEDIA_UNMOUNTED;
        final File mydiary = new File(sdCardPath);

        if(!mydiary.exists())
            mydiary.mkdir();

        //Datepicker_Dialog

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        //일기 수정 화면에 출력
        fileName = makeDiaryFileName(cYear, cMonth, cDay);
        filePath = sdCardPath + "/" + fileName;

        dateView.setText(cYear + "년 " + cMonth + "월 " + cDay + "일");
        content = readDiary(filePath);
        edtDiary.setText(content);

        //DatePicker Dialog Load
        dateView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog = View.inflate(MainActivity.this, R.layout.datepicker_dialog,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("날짜 선택");
                dp = (DatePicker) DatePickerDialog.findViewById(R.id.datePicker);
                dlg.setView(DatePickerDialog);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        fileName = makeDiaryFileName(dp.getYear(), dp.getMonth()+1, dp.getDayOfMonth());
                        dateView.setText(dp.getYear() + "년 " + (dp.getMonth()+1) + "월 " + dp.getDayOfMonth() + "일");
                        filePath = sdCardPath + "/" + fileName;
                        content = readDiary(filePath);
                        edtDiary.setText(content);
                    }
                });
                dlg.show();
            }
        });

        //Save Button ActionEvent
        SaveBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String Diary = edtDiary.getText().toString();
                saveDiary(filePath, Diary);
            }
        });
    }



    protected boolean ondCreateOpetionMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        filePath = sdCardPath + "/" + fileName;

        switch(item.getItemId()){
            //다시 읽기
            case R.id.ReReadMenu:
                edtDiary.setText(readDiary(filePath));
                break;

            //일기 삭제 Dialog 띄우기
            case R.id.DeleteMenu:
                deleteDialog = View.inflate(MainActivity.this, R.layout.diary_delete_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);

                dlg.setTitle("일기 삭제");
                dlg.setView(deleteDialog);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    //확인 클릭 후 상태 변화 --> 해당 날짜의 파일 삭제
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDiary(filePath);
                        readDiary(filePath);
                    }
                });

                //취소시에 아무런 상태 변화 없음
                dlg.setNegativeButton("취소", null);
                dlg.show();
                break;

            //기존의 글자크기에서 글자 크기 조정
            case R.id.SizeUp:
                edtDiary.setTextSize(BigFontSize);
                break;
            case R.id.SizeNormal:
                edtDiary.setTextSize(FontSize);
                break;
            case R.id.SizeSmall:
                edtDiary.setTextSize(SmallFontSize);
                break;
        }
        return onOptionsItemSelected(item);
    }

    // 다이어리 파일의 이름 생성
    public String makeDiaryFileName(int year, int month, int day) {
        return year + "_" + month + "_" + day + ".txt";
    }

    public void saveDiary(String filePath, String content) {
        try {
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            SaveBtn.setText("수정하기");
        } catch (IOException e) {
            SaveBtn.setText("새로 저장");
        }
    }

    String readDiary(String filePath){
        //먼저 경로에 있는 파일을 연다.
        String diaryStr = null;
        FileInputStream inFs;
        try{
            File file = new File(filePath);
            inFs = new FileInputStream(file);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();

        }catch(IOException e){
            edtDiary.setText("일기가 없습니다.");
        }
        return diaryStr;
    }

    public boolean deleteDiary(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
            SaveBtn.setText("새로 저장");
            return true;
        }
        else
            return false;
    }
}
