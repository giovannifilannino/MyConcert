package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.RegistrationFragment;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.loginFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                loginFragment tab1 = new loginFragment();
                return tab1;
            case 1:
                RegistrationFragment tab2 = new RegistrationFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}