package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ChosenConcerts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_concerts);
        TableLayout tab = (TableLayout) findViewById(R.id.mytablayout);
        ArrayList<String> concerti = getIntent().getStringArrayListExtra("concertiscelti");
        for(int i=0;i<concerti.size();i++)
        {
            TableRow row=new TableRow(this);
            String debt = concerti.get(i);
            TextView tvDebt=new TextView(this);
            tvDebt.setText(""+debt);
            row.addView(tvDebt);
            tab.addView(row);
        }
    }
}
