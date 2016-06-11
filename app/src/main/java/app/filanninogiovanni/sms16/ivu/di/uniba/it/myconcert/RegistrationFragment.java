package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;


/**
 * Created by Giovanni on 01/06/2016.
 */
public class RegistrationFragment extends Fragment {

    private static final String SUCCESS_TAG = "success";
    private EditText user;
    private EditText password;
    private EditText nome;
    private EditText cognome;
    private Button registration;
    private String result;
    private RequestQueue requestQueue;
    private String URLRegistration = "http://mymusiclive.altervista.org/registration.php?";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registation_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity());
        user = (EditText) getActivity().findViewById(R.id.usernameReg);
        password = (EditText) getActivity().findViewById(R.id.passwordReg);
        nome = (EditText) getActivity().findViewById(R.id.nomeReg);
        cognome = (EditText) getActivity().findViewById(R.id.cognomeReg);
        registration = (Button) getActivity().findViewById(R.id.buttonl);

        final JSONObject jsonObject = new JSONObject();

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                URLRegistration += "&nome=" + '"' + nome.getText().toString() + '"' + "&cognome=" + '"' + cognome.getText().toString() + '"' + "&username=" + '"' + user.getText().toString() + '"' + "&password=" + '"' + password.getText().toString() + '"';

                JsonObjectRequest arrayRequest = new JsonObjectRequest(URLRegistration, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject test = response;
                        try {
                            String success = test.get(SUCCESS_TAG).toString();
                            Toast.makeText(getActivity(), success, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }


                });
                requestQueue.add(arrayRequest);
            }
        });
    }
}

