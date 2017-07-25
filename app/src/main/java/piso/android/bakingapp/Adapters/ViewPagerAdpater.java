package piso.android.bakingapp.Adapters;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import piso.android.bakingapp.StepFragment;

public class ViewPagerAdpater extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ViewPagerAdpater(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

         if(position>=0 && position < mNumOfTabs) {
             StepFragment stepFragment = new StepFragment() ;
             Bundle bundle = new Bundle();
             bundle.putInt("position", position);
             stepFragment.setArguments(bundle);
             return (android.support.v4.app.Fragment) stepFragment;
         }
        else{
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}