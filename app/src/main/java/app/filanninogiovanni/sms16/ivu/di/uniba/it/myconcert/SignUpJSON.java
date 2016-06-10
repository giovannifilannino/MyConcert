package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Giovanni on 10/06/2016.
 */
public class SignUpJSON extends AsyncTask<String,Void,String>{

    private Context context;
    private String URL_USER = "http://mymusiclive.altervista.org/user.php";

    public SignUpJSON(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String password = params[1];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result = "";

        try{
            data = "?username=" + URLEncoder.encode('"' + username + '"',"UTF-8");
            data += "&password=" + URLEncoder.encode('"'+ password + '"', "UTF-8");

            link = URL_USER + data;

            String link2 = "http://www.google.it";

            URL url = new URL(link2);



            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            int responseCode = con.getResponseCode();

            String panino = "panino";

            InputStream i = con.getInputStream();



            InputStream in = new BufferedInputStream(con.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return  result;


        }catch (Exception e){
            return new String ("Excetpion " + e.getMessage());
        } finally {
            return result;
        }

    }
}
