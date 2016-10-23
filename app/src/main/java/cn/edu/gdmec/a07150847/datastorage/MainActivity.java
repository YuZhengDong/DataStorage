package cn.edu.gdmec.a07150847.datastorage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText et1,et2;
    private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=(EditText)findViewById(R.id.edittext1);
        et2=(EditText)findViewById(R.id.edittext2);
        t1=(TextView)findViewById(R.id.textView);
    }
    public void spWrite(View v){
        SharedPreferences user = getSharedPreferences("user",MODE_APPEND);
        SharedPreferences.Editor editor = user.edit();
        editor.putString("account",et1.getText().toString());
        editor.putString("pass",et2.getText().toString());
        editor.commit();
        Toast.makeText(MainActivity.this,"SharedPreferences写入成功",Toast.LENGTH_SHORT).show();
    }

    public void spRead(View v){
        SharedPreferences user =  getSharedPreferences("user",0);
        String account = user.getString("account","木有这个键值");
        String pass = user.getString("pass","木有这个值");
        t1.setText("账号"+account+"\n"+"密码"+pass);
        Toast.makeText(MainActivity.this,"SharedPreferences读取成功",Toast.LENGTH_SHORT).show();
    }


    public  void ROMWrite(View v){
        String accout = et1.getText().toString();
        String pass = et2.getText().toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput("user.txt",MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(accout+":"+pass);
            bufferedWriter.flush();
            fileOutputStream.close();
            Toast.makeText(this,"ROM写入成功",Toast.LENGTH_LONG).show();


        }catch(Exception e){
            e.printStackTrace();

        }
    }

    public void ROMRead(View v){
        try{
            FileInputStream fileInputStream = openFileInput("user.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer();
            String s;
            while((s=bufferedReader.readLine())!=null){
                sb.append(s+"\n");

            }

            fileInputStream.close();
            t1.setText(sb);
            Toast.makeText(this,"ROM读取成功",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }





    public void SDWrite(View v){
        String str = et1.getText().toString()+":"+et2.getText().toString();
        String sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = sdCardRoot+"/test.txt";
        File file = new File(filename);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(str.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(this,"SD卡写入成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,"异常",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void SDRead(View v){
        String sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = sdCardRoot+"/test.txt";
        File file = new File(filename);
        int length = (int)file.length();
        try {
            byte b[]=new byte[length];
            FileInputStream fis = new FileInputStream(file);
            fis.read(b,0,length);
            fis.close();
            t1.setText(new String(b));
            Toast.makeText(this,"SD卡读取成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,"异常",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }


}
