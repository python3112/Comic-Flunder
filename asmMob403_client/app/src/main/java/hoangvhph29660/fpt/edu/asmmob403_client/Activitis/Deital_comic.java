package hoangvhph29660.fpt.edu.asmmob403_client.Activitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import hoangvhph29660.fpt.edu.asmmob403_client.Adapter.viewpager2_deital_Adapter;
import hoangvhph29660.fpt.edu.asmmob403_client.R;

public class Deital_comic extends AppCompatActivity {
    private String Idcomic;
    ViewPager2 viewpager_deltail;

    TabLayout tablayout_deital;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deital_comic);
        viewpager_deltail = findViewById(R.id.viewpager_deltail);
        tablayout_deital = findViewById(R.id.tablayout_deital);

        viewpager2_deital_Adapter  adapter = new viewpager2_deital_Adapter(this);
        viewpager_deltail.setAdapter(adapter);
        // Kết nối TabLayout với ViewPager2
        tablayout_deital.addTab(tablayout_deital.newTab().setText("Comments"));
        tablayout_deital.addTab(tablayout_deital.newTab().setText("Deital"));
        tablayout_deital.addTab(tablayout_deital.newTab().setText("Read"));


        tablayout_deital.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager_deltail.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewpager_deltail.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tablayout_deital.selectTab(tablayout_deital.getTabAt(position));
                super.onPageSelected(position);
            }
        });









    }

    public String getIdcomic() {
        return Idcomic;
    }
}