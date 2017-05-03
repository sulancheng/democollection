package com.example.su.mygzzx.tablyout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.su.mygzzx.R;

/**
 * Created by su
 * on 2017/4/21.
 */
public class Fragmenttwo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_fragmentone, container, false);
        TextView textfrag = (TextView) inflate.findViewById(R.id.textfrag);
        textfrag.setText("is two");
        return inflate;
    }
}
