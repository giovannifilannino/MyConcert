package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;

/**
 * Created by delmi on 13/06/2016.
 */

public class ErrorClass {
    public static final int DIALOG_NOCONNECTION_ID=1;
    public static final int DIALOG_NOARTISTFOUND_ID=2;
    public static final int DIALOG_NOVENUESFOUND_ID=3;
    public static final int PROGRESS_DIALOG_ID=4;
    public static final int DIALOG_FAILACCESS_ID=5;



    public static Dialog onCreateDialog(int id, final Context context) {
        Dialog dialog = null;
        switch(id) {
            case 1:
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle(context.getResources().getString(R.string.NoConn));
                builder.setMessage(context.getResources().getString(R.string.AttConn));
                builder.setCancelable(true);
                builder.setNegativeButton(context.getResources().getString(R.string.closea), new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        System.exit(0);
                    }
                });
                builder.setPositiveButton(context.getResources().getString(R.string.Imp), new DialogInterface.OnClickListener() {
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
                builder1.setTitle(context.getResources().getString(R.string.Artist));
                builder1.setMessage(context.getResources().getString(R.string.ArtistDescr));
                builder1.setCancelable(true);
                builder1.setPositiveButton(context.getResources().getString(R.string.closea),new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.dismiss();
                    }
                });
                builder1.show();
                break;
            case 3:
                AlertDialog.Builder builder2=new AlertDialog.Builder(context);
                builder2.setTitle(context.getResources().getString(R.string.venues));
                builder2.setMessage(context.getResources().getString(R.string.VenuesDescr));
                builder2.setCancelable(true);
                builder2.setPositiveButton(context.getResources().getString(R.string.closea),new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.dismiss();
                    }
                });

                break;
            case 4:
                ProgressDialog  progress = new ProgressDialog(context);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setMessage(context.getResources().getString(R.string.loading));


                progress.setCancelable(true);
                dialog=progress;
                break;
            case 5:
                final AlertDialog.Builder builder3=new AlertDialog.Builder(context);
                builder3.setTitle(context.getResources().getString(R.string.Autentication));
                builder3.setMessage(context.getResources().getString(R.string.AutenticationDescr));
                builder3.setCancelable(true);
                builder3.setPositiveButton(context.getResources().getString(R.string.closea),new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.dismiss();
                    }
                });
                builder3.show();
                break;

            default:
                dialog = null;
        }
        return dialog;
    }

}
