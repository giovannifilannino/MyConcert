package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.RegistrationFragment;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.loginFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    static loginFragment loginFragmentml;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                loginFragment tab1 = new loginFragment();
                loginFragmentml = tab1;
                return tab1;
            case 1:
                RegistrationFragment tab2 = new RegistrationFragment();
                return tab2;
            default:
                return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Fragment getFragment(int position) {
        try {
            Field f = FragmentStatePagerAdapter.class.getDeclaredField("mFragments");
            f.setAccessible(true);
            ArrayList<Fragment> fragments = (ArrayList<Fragment>) f.get(this);
            if (fragments.size() > position) {
                return fragments.get(position);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}