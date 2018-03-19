package top.banyaoqiang.www.bim;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutTestActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<String> tabNames;
    private List<Fragment> fragments;
    private ContentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_test);
        tabLayout = findViewById(R.id.tab_test_tab_layout);
        viewPager = findViewById(R.id.tab_test_view_pager);

        initContent();
        initTab();
    }

    private void initTab(){
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //tabLayout.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(tabLayout, 10);
        tabLayout.setupWithViewPager(viewPager);

        for(int i=0;i<tabNames.size();i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(R.layout.item_tab_main);
                TextView textView = tab.getCustomView().findViewById(R.id.item_tab_main_tab_name);
                textView.setText(tabNames.get(i));
            }
        }
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
    }

    private void initContent(){
        tabNames = new ArrayList<>();
        for(int i=0;i<3;i++){
            tabNames.add("Tab" + i);
        }
        fragments = new ArrayList<>();
        for(String name : tabNames){
            fragments.add(TabContentFragment.newInstance(name));
        }
        adapter = new ContentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return tabNames.size();
        }
    }
}
