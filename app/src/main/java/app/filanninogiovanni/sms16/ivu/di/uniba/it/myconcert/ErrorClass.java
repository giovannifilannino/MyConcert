package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by delmi on 13/06/2016.
 */

public class ErrorClass {
    private static final int DIALOG_NOCONNECTION_ID=1;
    private static final int DIALOG_NOARTISTFOUND_ID=2;
    private static final int DIALOG_NOVENUESFOUND_ID=3;


    public static Dialog onCreateDialog(int id, final Context context) {
        Dialog dialog = null;
        switch(id) {
            case 1:
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("No Connection found");
                builder.setMessage("Attivare connessione");
                builder.setCancelable(true);
                builder.setNegativeButton("Chiudi", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        System.exit(0);
                    }
                });
                builder.setPositiveButton("Attiva", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent AttConn =new Intent(Settings.ACTION_SETTINGS);
                        context.startActivity(AttConn);
                    }
                });
                builder.show();
                break;
            case 2:
                AlertDialog.Builder builder1=new AlertDialog.Builder(context);
                builder1.setTitle("No Artist found");
                builder1.setMessage("Non esiste alcun artista che corrisponda ai parametri di ricerca inseriti");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Chiudi",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.dismiss();
                    }
                });
                builder1.show();
                break;
            case 3:
                AlertDialog.Builder builder2=new AlertDialog.Builder(context);
                builder2.setTitle("No Venues found");
                builder2.setMessage("Non esiste alcuna piazza che corrisponda ai parametri di ricerca inseriti");
                builder2.setCancelable(true);
                builder2.setPositiveButton("Chiudi",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.dismiss();
                    }
                });
                builder2.show();
                break;

            default:
                dialog = null;
        }
        return dialog;
    }

}
