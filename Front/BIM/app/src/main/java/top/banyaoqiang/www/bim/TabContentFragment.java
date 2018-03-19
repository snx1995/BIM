package top.banyaoqiang.www.bim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by byq on 18-3-13.
 */

public class TabContentFragment extends Fragment {
    private static final String EXTRA_CONTENT = "content";

    public static TabContentFragment newInstance(String content){
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        TabContentFragment tabContentFragment = new TabContentFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.tab_test_fragment, null);
        TextView textView = content.findViewById(R.id.tab_test_fragment_text_view);
        textView.setText(getArguments().getString(EXTRA_CONTENT));

        return content;
    }
}
