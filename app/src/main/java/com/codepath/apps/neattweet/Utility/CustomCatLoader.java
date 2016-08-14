package com.codepath.apps.neattweet.Utility;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roger.catloadinglibrary.CatLoadingView;

/**
 * Created by vidhurvoora on 7/31/16.
 */
public class CustomCatLoader extends CatLoadingView {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

       // getDialog().getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.wood_paper_2));
        return super.onCreateDialog(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //TODO figure out how to change the background color
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
