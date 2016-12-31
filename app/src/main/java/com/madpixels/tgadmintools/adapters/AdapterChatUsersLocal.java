package com.madpixels.tgadmintools.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.madpixels.apphelpers.UIUtils;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.helper.TgUtils;

import org.drinkless.td.libcore.telegram.TdApi;

import io.github.rockerhieu.emojicon.EmojiconTextView;

/**
 * Created by Snake on 22.02.2016.
 */
public class AdapterChatUsersLocal extends AdapterChatUsers {

    public static class ChatMemberUser extends TdApi.ChatMember{
        public TdApi.User localUser;
    }

   // public ArrayList<TdApi.User> usersList=new ArrayList<>();

    public AdapterChatUsersLocal(Context c) {
        super(c);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final boolean isCreated = view == null;
        if (isCreated) {
            view = inflater.inflate(R.layout.list_item_chat_user, parent, false);
        }



        final EmojiconTextView tvUserName = UIUtils.getHolderView(view, R.id.tvUserName);
        final TextView tvUserInfo = UIUtils.getHolderView(view, R.id.tvUserInfo);
        final TextView tvInviter = UIUtils.getHolderView(view, R.id.tvInviter);
        final ImageView imageAvatar = UIUtils.getHolderView(view, R.id.imageAvatar);
        final ImageView ivIsAdmin = UIUtils.getHolderView(view, R.id.ivIsAdmin);
        if(isCreated) {
            ivIsAdmin.setImageDrawable(null);
            tvInviter.setText("");
        }

        final ChatMemberUser member2 = (ChatMemberUser) getItem(position);
        TdApi.User user = TgUtils.getUser(member2.userId);
        if(user.firstName.isEmpty())
            user = member2.localUser;

        String username = user.firstName+" "+user.lastName;

        if(user.type!=null && user.type.getConstructor()== TdApi.UserTypeDeleted.CONSTRUCTOR){
            tvUserName.setText(R.string.user_type_deleted);
            tvUserName.setTextColor(Color.parseColor("#BDBDBD"));
        }else{
            tvUserName.setText(user.firstName + " " + user.lastName);
        }
        if(TextUtils.isEmpty(user.username))
            tvUserInfo.setVisibility(View.GONE);
        else{
            tvUserInfo.setText("@"+user.username);
            tvUserInfo.setVisibility(View.VISIBLE);
        }

        Bitmap avatar = user.profilePhoto==null ? null : imageGetter.getPhoto(user.profilePhoto.small.id);
        if(avatar!=null) {
            Drawable drawable = new BitmapDrawable(mContext.getResources(), avatar);
            imageAvatar.setImageDrawable(drawable);
        }
        else
            imageAvatar.setImageResource(R.drawable.no_avatar);

        return view;
    }






}
