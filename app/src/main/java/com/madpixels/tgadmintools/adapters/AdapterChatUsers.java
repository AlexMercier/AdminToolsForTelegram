package com.madpixels.tgadmintools.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.madpixels.apphelpers.UIUtils;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.utils.TgImageGetter;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.rockerhieu.emojicon.EmojiconTextView;

/**
 * Created by Snake on 22.02.2016.
 */
public class AdapterChatUsers extends BaseAdapter {

    final LayoutInflater inflater;
    ArrayList<TdApi.ChatMember> list = new ArrayList<>();
    TgImageGetter imageGetter;
    final Context mContext;

    public AdapterChatUsers(Context c) {
        mContext=c;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageGetter=new TgImageGetter().setAdapter(this).setRounded(true);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public TdApi.ChatMember getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final boolean isCreated = view == null;
        if (isCreated) {
            view = inflater.inflate(R.layout.list_item_chat_user, parent, false);
        }

        final TdApi.ChatMember member = getItem(position);

        final EmojiconTextView tvUserName = UIUtils.getHolderView(view, R.id.tvUserName);
        final TextView tvUserInfo = UIUtils.getHolderView(view, R.id.tvUserInfo);
        final TextView tvInviter = UIUtils.getHolderView(view, R.id.tvInviter);
        final ImageView imageAvatar = UIUtils.getHolderView(view, R.id.imageAvatar);
        final ImageView ivIsAdmin = UIUtils.getHolderView(view, R.id.ivIsAdmin);

        TdApi.User user = TgUtils.getUser(member.userId);

        if(user.type.getConstructor()== TdApi.UserTypeDeleted.CONSTRUCTOR){
            tvUserName.setText(R.string.user_type_deleted);
            tvUserName.setTextColor(Color.parseColor("#BDBDBD"));
        }else{
            tvUserName.setText(user.firstName + " " + user.lastName);
        }
        if(user.username.isEmpty())
            tvUserInfo.setVisibility(View.GONE);
        else{
            tvUserInfo.setText("@"+user.username);
            tvUserInfo.setVisibility(View.VISIBLE);
        }

        if(user.type.getConstructor()== TdApi.UserTypeBot.CONSTRUCTOR)
            ivIsAdmin.setImageResource(R.drawable.user_bot);
        else
        if(member.status.getConstructor()== TdApi.ChatMemberStatusCreator.CONSTRUCTOR)
            ivIsAdmin.setImageResource(R.drawable.user_creator);
        else if(member.status.getConstructor()==TdApi.ChatMemberStatusModerator.CONSTRUCTOR)
            ivIsAdmin.setImageResource(R.drawable.user_moder);

        else if(member.status.getConstructor()==TdApi.ChatMemberStatusEditor.CONSTRUCTOR)
            ivIsAdmin.setImageResource(R.drawable.user_admin);
        else {
            ivIsAdmin.setImageDrawable(null);
        }

        Bitmap avatar = user.profilePhoto==null?null:imageGetter.getPhoto(user.profilePhoto.small.id);
        if(avatar!=null) {
            // imageAvatar.setImageBitmap(avatar);
            Drawable drawable = new BitmapDrawable(mContext.getResources(), avatar);
            imageAvatar.setImageDrawable(drawable);

        }
        else
            imageAvatar.setImageResource(R.drawable.no_avatar);

        String inviter = getInviter(member.inviterUserId);


        tvInviter.setText(inviter != null ? mContext.getString(R.string.text_label_inviter) +" " + inviter : "");

        return view;
    }



    public void addAll(List<TdApi.ChatMember> items) {
        list.addAll(items);
        notifyDataSetChanged();
    }


    HashMap<Integer, String> inviters = new HashMap<>();

    String getInviter(final int inviterId) {
        if (inviterId == 0)
            return null;
        else if (inviters.containsKey(inviterId))
            return inviters.get(inviterId);
        TgH.TG().send(new TdApi.GetUser(inviterId), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.User.CONSTRUCTOR) {
                    TdApi.User user = (TdApi.User) object;
                    String name = user.firstName + " " + user.lastName;
                    inviters.put(inviterId, name);
                }
            }
        });
        return null;
    }

    public ArrayList getList() {
        return list;
    }

    public void onDestroy(){
        imageGetter.onDestroy();
    }







}
