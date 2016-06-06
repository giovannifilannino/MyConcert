package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class loginFragment extends Fragment{

    private EditText username;
    private EditText password;
    private Button login;
    private OnLoginConfirmed mLogin;
    Toolbar toolbar;

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
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        //toolbar=(Toolbar)getActivity().findViewById(R.id.tool_bar);
        //toolbar.setNavigationIcon(R.drawable.ic_drawer);
        login = (Button) getActivity().findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().compareTo("pippo") == 0 && password.getText().toString().compareTo("pippo") == 0){
                    mLogin.goToSearchFragment();
                }
            }
        });

        super.onActivityCreated(savedInstanceState);
    }
}

