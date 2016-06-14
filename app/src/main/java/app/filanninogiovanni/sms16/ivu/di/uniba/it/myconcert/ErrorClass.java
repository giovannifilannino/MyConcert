package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by delmi on 13/06/2016.
 */

public class ErrorClass {
    private static final int DIALOG_NOCONNECTION_ID=1;
    public static Dialog onCreateDialog(int id, final Context context) {
        Dialog dialog = null;
        switch(id) {
            case 1:
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("No Connection found");
                builder.setMessage("Attivare connessione");
                builder.setCancelable(false);
                builder.setNegativeButton("Chiudi", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        System.exit(0);
                    }
                });
                builder.setPositiveButton("Attiva", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent AttConn =new Intent(Intent.CATEGORY_PREFERENCE);
                        context.startActivity(AttConn);
                    }
                });
                builder.show();
                break;
            case 2:
                // definire cosa fare in caso di conferma
                break;
            default:
                dialog = null;
        }
        return dialog;
    }

}
