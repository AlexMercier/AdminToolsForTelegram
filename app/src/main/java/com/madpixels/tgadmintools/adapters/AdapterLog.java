package com.madpixels.tgadmintools.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.madpixels.apphelpers.UIUtils;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by Snake on 22.03.2016.
 */
public class AdapterLog extends BaseAdapter{

    public ArrayList<LogUtil.LogEntity> list=new ArrayList<>();
    final LayoutInflater inflater;

    public AdapterLog(Context c) {
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public LogUtil.LogEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final boolean isCreate = view==null;
        if(isCreate){
            view = inflater.inflate(R.layout.item_log_view, parent, false);
        }

        LogUtil.LogEntity log = getItem(position);

        final TextView logTitle = UIUtils.getHolderView(view, R.id.logTitle);
        final TextView logText = UIUtils.getHolderView(view, R.id.logText);


        logTitle.setText(log.getTitle());
        logText.setText(log.getLogText());


        return view;
    }
}
