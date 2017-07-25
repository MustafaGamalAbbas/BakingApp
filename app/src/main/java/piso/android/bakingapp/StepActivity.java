package piso.android.bakingapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import piso.android.bakingapp.Adapters.ViewPagerAdpater;
import piso.android.bakingapp.Models.Recipe;

public class StepActivity extends AppCompatActivity {
    private int NumberofFragments = 0;
    private int position;
    private Button pre, next;
    private ViewPager viewPager;
    private Recipe mRecipe ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        if (getIntent() != null) {
            NumberofFragments = getIntent().getExtras().getInt("size");
            position = getIntent().getIntExtra("position", 0);
            mRecipe = (Recipe) getIntent().getSerializableExtra("recipe");
            Toast.makeText(this, String.valueOf(NumberofFragments), Toast.LENGTH_SHORT).show();

        }
        pre = (Button) findViewById(R.id.bt_previous);
        next = (Button) findViewById(R.id.bt_next);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        final ViewPagerAdpater adapter = new ViewPagerAdpater
                (getSupportFragmentManager(), NumberofFragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        if (position == 0) {
            pre.setVisibility(View.GONE);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    pre.setVisibility(View.GONE);
                } else if (position == NumberofFragments - 1) {
                    next.setVisibility(View.GONE);
                } else {
                    pre.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(-1), true);

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(+ 1), true);

            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            Intent intent = new Intent(this , TestAct.class) ;
            int pos = viewPager.getCurrentItem() ;
            if(!mRecipe.getSteps().get(pos).getVideoURL().isEmpty())
                intent.putExtra("url",mRecipe.getSteps().get(pos).getVideoURL());

             startActivity(intent);
            //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else{

            //Log.e("On Config Change","PORTRAIT");
        }

    }


    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
}
