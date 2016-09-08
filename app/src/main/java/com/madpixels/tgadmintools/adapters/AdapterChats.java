package com.madpixels.tgadmintools.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.madpixels.apphelpers.UIUtils;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.utils.TgImageGetter;

import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;

import io.github.rockerhieu.emojicon.EmojiconTextView;

/**
 * Created by Snake on 21.02.2016.
 */
public class AdapterChats extends BaseAdapter{

    public ArrayList<TdApi.Chat> list=new ArrayList<>(0);
    final LayoutInflater inflater;
    TgImageGetter images;

    public AdapterChats(Context c) {
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        images = new TgImageGetter().setRounded(true);
        images.setUpdateCallback(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public TdApi.Chat getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final boolean isCreated = view==null;
        if(isCreated){
            view = inflater.inflate(R.layout.list_item_chat, parent, false);
        }

        TdApi.Chat chat = getItem(position);

        final EmojiconTextView title = UIUtils.getHolderView(view, R.id.tvChatTitle);
        final TextView info = UIUtils.getHolderView(view, R.id.tvChatInfo);
        final ImageView icon = UIUtils.getView(view, R.id.ivIcon);
        final ImageView imageChatAva = UIUtils.getView(view, R.id.imageChatAva);
//        info.setVisibility(View.INVISIBLE);

        Bitmap ava = images.getPhoto(chat.photo.small.id);
        if(ava!=null){
            imageChatAva.setImageBitmap(ava);
        }else{
            imageChatAva.setImageResource(R.drawable.no_avatar);
        }

        TdApi.ChatParticipantRole role = TgUtils.getRole(chat);
        if(role.getConstructor()== TdApi.ChatParticipantRoleAdmin.CONSTRUCTOR)
            info.setText(R.string.textRoleAdmin);
        else
            info.setText(R.string.textRoleModerator);

        switch (chat.type.getConstructor()){
            case TdApi.Group.CONSTRUCTOR:
                //TdApi.Group g = chat.
                break;
        }

        if(TgUtils.isGroup(chat.type.getConstructor()))
            icon.setImageResource(R.drawable.icon_group);
        else {
            if(TgUtils.isChannel(chat))
                icon.setImageResource(R.drawable.icon_channel);
            else
                icon.setImageResource(R.drawable.icon_supergroup);
        }


        title.setText(chat.title);

        return view;
    }

    public void addAll(ArrayList<TdApi.Chat> mListAdminChats) {
        list.addAll(mListAdminChats);
        notifyDataSetChanged();
    }

    public void onDestroy(){
        images.onDestroy();
    }
}
