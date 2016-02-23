package com.example.user.infinity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class login extends AppCompatActivity {

    EditText user;
    EditText passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        user= (EditText)findViewById(R.id.em);
        passw = (EditText)findViewById(R.id.pw);
    }

    public void OnLogin(View view) {
        String button_text;
        button_text=((Button) view).getText().toString();
        if(button_text.equals("LOGIN")) {
            Log.d("login Page", "here");
            String username = user.getText().toString();
            String password = passw.getText().toString();
            new DownloadWebPageTask().execute(username, password);
            //Toast.makeText(getBaseContext(),"username",Toast.LENGTH_SHORT).show();
        }
    }

    public void OnSignUp(View view) {
        String button_text;
        button_text=((Button) view).getText().toString();
        if(button_text.equals("SIGN-UP")) {
            //Intent intent1=new Intent(this,signup.class);
            //startActivity(intent1);
            Toast.makeText(getBaseContext(),"successful",Toast.LENGTH_SHORT).show();
            //new DownloadWebPageTask().execute();
        }
    }
    private class DownloadWebPageTask extends AsyncTask<String, String, String> {

        int fd_pin;
        String fd_email="";


        @Override
        protected void onPreExecute(){

            Log.d("login Page","pre");
        }




        @Override
        protected String doInBackground(String... args) {
            //Log.d(username,password);
            String username = args[0];
            String password = args[1];
            JSONObject object = new JSONObject();
            try {
                object.put("name", username);
                object.put("pass", password);
            }catch (Exception e){
                Log.e("Error","BAd error");
            }
            String message = object.toString();
            ClientServerInterface ht = new ClientServerInterface();
            String result = ht.getdata(message,"http://192.168.1.39:5000/");
            return result;
        }

        // @Override
        protected void onPostExecute(String result) {


            Log.d("Constatnts", "backgound-" + result);
            Log.d("Constatnts", "in post execute.");

            try{
                Log.d("json","atleast here");
                //    JSONArray jArray = new JSONArray(result);
           /*     Log.d("json","this might be errror");
                JSONObject json_data=null;
                Log.d("json","json is reADY");
                for(int i=0;i<jArray.length();i++){*/
                JSONObject json_data = new JSONObject(result);
                //    json_data = jArray.getJSONObject(i);
                Log.d("json","before parse");
                fd_pin=json_data.getInt("status");
                fd_email=json_data.getString("message");
                //   password=json_data.getString("password");
                //  fd_id=json_data.getInt("FOOD_ID");
                //  fd_name=json_data.getString("FOOD_NAME");
                Log.d("json","parsing done");
                //  }
                //Toast.makeText(getBaseContext(),fd_email,Toast.LENGTH_LONG).show();
            }catch(JSONException e1){
                Toast.makeText(getBaseContext(), "No Food Found", Toast.LENGTH_LONG).show();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            if(fd_pin==1){
                Log.d("main","in intent");
                Intent intent=new Intent(getBaseContext(),YesOrNo.class);
                startActivity(intent);
                Toast.makeText(getBaseContext(),fd_email,Toast.LENGTH_SHORT).show();
            }
            if(fd_pin==0){
                Log.d("main","in intent");
                //Intent intent=new Intent(this,YesOrNo.class);
                //startActivity(inten
                Toast.makeText(getBaseContext(),"fail",Toast.LENGTH_SHORT).show();
            }

        }
    }
}
