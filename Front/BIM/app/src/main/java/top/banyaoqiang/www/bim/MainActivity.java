package top.banyaoqiang.www.bim;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Bim_debug";

    private SwipeRefreshLayout refreshLayout;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private ContentPagerAdapter adapter;

    private String[] tabNames = {"BIM", "通讯录", "查找"};
    private int[] tabIcons = {R.drawable.ic_message, R.drawable.icon_address_book, R.drawable.ic_search};

    private BimUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

//        user = new BimUser();
//        user.fromIntent(getIntent());
//
//        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.main_tool_bar);
//        setSupportActionBar(toolbar);
//
//        drawerLayout = findViewById(R.id.main_drawer_layout);
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        }
//
//        initRecyclerView();
//        initSwipeRefresh();
//        initButtons();
//        initNavigationView();

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("BIM");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        tabLayout = findViewById(R.id.main_tab_tab_layout);
        viewPager = findViewById(R.id.main_tab_view_pager);
        drawerLayout = findViewById(R.id.main_drawer_layout);

        initNavigationView();
        initViewPagers();
        initTabLayout();
    }

    private void initViewPagers(){
        fragments = new ArrayList<>();
        HistoryMsgFragment historyMsgFragment = new HistoryMsgFragment();
        historyMsgFragment.setParentActivity(this);
        fragments.add(historyMsgFragment);
        AddressBookFragment addressBookFragment = new AddressBookFragment();
        addressBookFragment.setParentActivity(this);
        fragments.add(addressBookFragment);
        fragments.add(new SearchFragment());
        adapter = new ContentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void initTabLayout(){
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        ViewCompat.setElevation(tabLayout, 10);
        tabLayout.setupWithViewPager(viewPager);
        for(int i=0;i<tabNames.length;i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if(tab != null){
                tab.setCustomView(R.layout.item_tab_main);
                TextView textView = tab.getCustomView().findViewById(R.id.item_tab_main_tab_name);
                textView.setText(tabNames[i]);
                ImageView imageView = tab.getCustomView().findViewById(R.id.item_tab_main_tab_icon);
                imageView.setImageResource(tabIcons[i]);
            }
        }
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_tool_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                Toast.makeText(this, item.getItemId() + "", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void initUserInfo(BimUser user){
        TextView name = findViewById(R.id.main_nav_name);
        TextView email = findViewById(R.id.main_nav_email);
        CircleImageView image = findViewById(R.id.main_nav_image);

        name.setText(user.getName());
    }

    private void initNavigationView(){
        NavigationView nav = findViewById(R.id.main_navigation);
        nav.setCheckedItem(R.id.main_nav_home_page);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_nav_logout:
                        BimFile.saveToFile(MainActivity.this, LauncherActivity.USER_INFO_FILE_NAME, "");
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.main_nav_home_page:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.main_nav_my_info:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.main_nav_welfare:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.main_nav_settings:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.main_nav_about_bim:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.main_nav_feedback:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.main_nav_update:
                        drawerLayout.closeDrawers();
                        break;
                    default:
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
