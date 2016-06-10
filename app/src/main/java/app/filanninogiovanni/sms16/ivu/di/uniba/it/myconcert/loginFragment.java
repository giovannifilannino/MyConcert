package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.IOException;


public class loginFragment extends Fragment{

    private EditText username;
    private EditText password;
    private Button login;
    private OnLoginConfirmed mLogin;
    private String UserURL = "http://mymusiclive.altervista.org/user.php?username=";
    private String OutPut = "http://mymusiclive.altervista.org/output.json";
    private String PasswordURL = "&password=";
    private String formatJson = "&format=json";



    String franco;


    RequestQueue requestQueue;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mLogin = (OnLoginConfirmed) context;
        } catch (Exception e){

        }
    }

    public interface OnLoginConfirmed{
        public void goToSearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.login_fragment,container,false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        username = (EditText) getActivity().findViewById(R.id.username);
        password = (EditText) getActivity().findViewById(R.id.password);
        requestQueue = Volley.newRequestQueue(getActivity());



        login = (Button) getActivity().findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = UserURL + "\""  + username.getText().toString() +"\"" +  PasswordURL + "\"" +  password.getText().toString() + "\"" + formatJson;
                JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray jsonArray = response;
                        franco= jsonArray.toString();
                        if(checkUtente(franco))
                            mLogin.goToSearchFragment();
                        else
                            Toast.makeText(getActivity(),"Utente non esistente",Toast.LENGTH_LONG);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(arrayRequest);

            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    private boolean checkUtente(String query){
        if(query.compareTo("[]")==0){
            return false;
        }
        return true;
    }


}

