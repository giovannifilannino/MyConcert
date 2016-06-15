package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;


import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;



public class loginFragment extends Fragment{

    private EditText username;
    private EditText password;
    private Button login;
    ErrorClass errorClass=new ErrorClass();
    private Dialog dialog;
    private OnLoginConfirmed mLogin;
    private String UserURL = "http://mymusiclive.altervista.org/user.php?username=";
    private String OutPut = "http://mymusiclive.altervista.org/output.json";
    private String PasswordURL = "&password=";
    private String formatJson = "&format=json";
    private CallbackManager callbackManager;
    private LoginButton loginButton;



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

        loginButton = (LoginButton) getActivity().findViewById(R.id.login_button);
        requestQueue = Volley.newRequestQueue(getActivity());



        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getActivity(), loginResult.getAccessToken().getUserId(), Toast.LENGTH_LONG).show();
                Log.d("Faceboook", loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity(),"Erroreeee",Toast.LENGTH_LONG).show();
                Log.d("Faceboook", "Erroreeeeee");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                Log.d("Faceboook", error.toString());
            }
        });


        login = (Button) getActivity().findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()){

                    String url = UserURL + "\"" + username.getText().toString() + "\"" + PasswordURL + "\"" + password.getText().toString() + "\"" + formatJson;
                    JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            JSONArray jsonArray = response;
                            franco = jsonArray.toString();
                            if (checkUtente(franco))
                                mLogin.goToSearchFragment();
                            else
                                Toast.makeText(getActivity(), "Utente non esistente", Toast.LENGTH_LONG);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(arrayRequest);

                }
                else {
                    dialog = ErrorClass.onCreateDialog(ErrorClass.DIALOG_NOCONNECTION_ID,getActivity());
                }
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}

