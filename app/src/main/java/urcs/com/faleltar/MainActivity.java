package urcs.com.faleltar;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends FragmentActivity
{

    private boolean isOnMain = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isOnMain = true;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.display, new mainFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed()
    {
        if (isOnMain)
        {
            super.onBackPressed();
        }
        else
        {
            toMainFragment();
        }
    }

    public void toNewFragment(View v)
    {
        isOnMain = false;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.display, new newFragment());
        fragmentTransaction.commit();
    }

    public void toUpdateFragment(String id)
    {
        isOnMain = false;
        Bundle bundle = new Bundle();
        bundle.putString("id", id);

        updateFragment updateFragment = new updateFragment();
        updateFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.display, updateFragment);
        fragmentTransaction.commit();
    }

    public void toMainFragment()
    {
        isOnMain = true;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.display, new mainFragment());
        fragmentTransaction.commit();
    }
}
