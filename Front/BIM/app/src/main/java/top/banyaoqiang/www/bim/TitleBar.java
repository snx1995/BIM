package top.banyaoqiang.www.bim;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by byq on 18-2-28.
 */

public class TitleBar extends LinearLayout {
    private Button back;
    private Button menu;
    private TextView title;

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_bar, this);
        back = findViewById(R.id.title_bar_back);
        title = findViewById(R.id.title_bar_title);
        menu = findViewById(R.id.title_bar_menu);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });
    }

    public void setBackBtnOnClick(View.OnClickListener listener){
        back.setOnClickListener(listener);
    }

    public void setMenuBtnOnClick(View.OnClickListener listener){
        menu.setOnClickListener(listener);
    }

    public void setTitleText(String title){
        if(title.length()>10) title = title.substring(0,10) + " ..";
        this.title.setText(title);
    }

    public void setTitleText(int resId){
        title.setText(resId);
    }

    public void setBackText(String text){
        back.setText(text);
    }

    public void setBackText(int resId){
        back.setText(resId);
    }

    public void setMenuText(String text){
        menu.setText(text);
    }

    public void setMenuText(int resId){
        menu.setText(resId);
    }

    public void setBackButtonEnabled(Boolean enabled){
        back.setVisibility(enabled? View.VISIBLE : View.INVISIBLE);
    }

    public void setMenuButtonEnabled(Boolean enabled){
        menu.setVisibility(enabled? View.VISIBLE : View.INVISIBLE);
    }
}
