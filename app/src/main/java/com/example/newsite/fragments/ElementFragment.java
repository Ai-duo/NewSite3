package com.example.newsite.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.newsite.BR;
import com.example.newsite.R;
import com.example.newsite.add.MarqueeView;
import com.example.newsite.add.SiteSets;
import com.example.newsite.databinding.ElementFragmentBinding;

//import com.xixun.add.ElementActivity;
public class ElementFragment extends Fragment {
    ElementFragmentBinding inflate;
    int size;
    Typeface typeface;
    TextView lv,rv;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("TAG_", "ElementFragment_onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG_", "ElementFragment_onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("TAG_", "ElementFragment_onCreateView");
        if(inflate==null) {
            inflate = DataBindingUtil.inflate(inflater, R.layout.element_fragment, container, false);
            if (size > 0) {
                inflate.setVariable(BR.textSize, size);
            }
            if (typeface != null) {
                inflate.setVariable(BR.typeFace, typeface);
            }
            inflate.setVariable(BR.orientation, SiteSets.getSiteTextSet().orientation);
            lv = inflate.leftText;
            rv = inflate.rightText;
        }
        if (!TextUtils.isEmpty(left)) {
            rv.setText(Html.fromHtml(left));
        }
        if (!TextUtils.isEmpty(right)) {
            lv.setText(Html.fromHtml(right));
        }
        return inflate.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("TAG_", "ElementFragment_onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("TAG_", "ElementFragment_onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("TAG_", "ElementFragment_onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("TAG_", "ElementFragment_onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("TAG_", "ElementFragment_onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("TAG_", "ElementFragment_onDestroyView");
    }
    @Override
    public void onDestroy() {
        Log.i("TAG_", "ElementFragment_onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("TAG_", "ElementFragment_onDetach");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
    String left = "<font color=\"#FFFF00\">温度:</font><font color=\"#FFFFFF\">--</font><font color=\"#FFFFFF\">℃<br></font>"
            +"<font color=\"#FFFF00\">风速:</font><font color=\"#FFFFFF\">--</font><font color=\"#FFFFFF\">m/s<br></font>"
            +"<font color=\"#FFFF00\">雨量:</font><font color=\"#FFFFFF\">--</font><font color=\"#FFFFFF\">mm<br></font>";

    String right = "<font color=\"#FFFF00\">湿度:</font><font color=\"#FFFFFF\">--</font><font color=\"#FFFFFF\">%RH<br></font>"
            +"<font color=\"#FFFF00\">风向:</font><font color=\"#FFFFFF\">--</font><font color=\"#FFFFFF\"><br></font>"
            +"<font color=\"#FFFF00\">气压:</font><font color=\"#FFFFFF\">--</font><font color=\"#FFFFFF\">hPa<br></font>";

    public void updateText(String leftText, String rightText) {
        Log.i("TAG_", "updateText:" + leftText + ";" + rightText);
        if (!TextUtils.isEmpty(leftText)) {
            left = leftText;
            inflate.rightText.setText(Html.fromHtml(leftText));
        }
        if (!TextUtils.isEmpty(rightText)) {
           right = rightText;
            inflate.leftText.setText(Html.fromHtml(rightText));
        }
    }

    public void setSizeAndType(int size, Typeface typeFace) {
        if (size > 0) {
            this.size = size;
        }
        if (typeFace != null) {
            this.typeface = typeFace;
        }
    }

    public void setDefultText() {
             updateText(left,right);
    }

}
