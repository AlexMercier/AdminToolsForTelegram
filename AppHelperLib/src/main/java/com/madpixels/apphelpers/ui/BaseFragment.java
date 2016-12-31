package com.madpixels.apphelpers.ui;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

/**
 * Created by Snake on 28.07.2016.
 */
public class BaseFragment extends Fragment{

    public void runOnUi(final Runnable r){
        if(getActivity()!=null && isAdded() && !getActivity().isFinishing())
            getActivity().runOnUiThread(r);
    }

    public <T> T getView(@IdRes int id) {
        return (T) getView().findViewById(id);
    }


    public void onSelect() {
    }
    public void onSelect(Object o){

    }
}
