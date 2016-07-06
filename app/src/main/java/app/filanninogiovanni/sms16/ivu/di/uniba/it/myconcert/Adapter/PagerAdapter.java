package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter;



import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;


import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.RegistrationFragment;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.loginFragment;

public class PagerAdapter extends android.support.v13.app.FragmentStatePagerAdapter {
    int mNumOfTabs;
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

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
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}