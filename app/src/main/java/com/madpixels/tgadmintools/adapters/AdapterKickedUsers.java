package com.madpixels.tgadmintools.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.apphelpers.Utils;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.ChatParticipantBan;
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
public class AdapterKickedUsers extends BaseAdapter{

    final LayoutInflater inflater;
    ArrayList<ChatParticipantBan> list=new ArrayList<>();
    TgImageGetter images;
    final Context mCtx;

    public AdapterKickedUsers(Context c) {
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        images=new TgImageGetter().setUpdateCallback(onImageLoaded).setRounded(true);
        mCtx = c;
    }

    private final Runnable onImageLoaded = new Runnable() {
        @Override
        public void run() {
            notifyDataSetChanged();
        }
    };

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ChatParticipantBan getItem(int position) {
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
            view = inflater.inflate(R.layout.list_item_kicked_user, parent, false);
        }

        ChatParticipantBan banUser = getItem(position);
        //TdApi.ChatMember member = banUser.chatParticipant;

        final EmojiconTextView tvUserName = UIUtils.getHolderView(view, R.id.tvUserName);
        final TextView tvUserInfo = UIUtils.getHolderView(view, R.id.tvUserInfo);
        final ImageView avatar = UIUtils.getHolderView(view, R.id.imageAvatar);
        final TextView tvBanText = UIUtils.getHolderView(view, R.id.tvBanText);
        final TextView tvOther = UIUtils.getHolderView(view, R.id.tvOther);
        final TextView tvUserLogin = UIUtils.getHolderView(view, R.id.tvUserLogin);
        TdApi.User user = TgUtils.getUser(banUser.user.id);
        if(user.type.getConstructor()== TdApi.UserTypeDeleted.CONSTRUCTOR){
            tvUserName.setTextColor(Color.parseColor("#BDBDBD"));
            tvUserName.setText(R.string.user_type_deleted);
            tvBanText.setVisibility(View.GONE);
            tvUserLogin.setVisibility(View.GONE);
        }else{
            tvUserName.setTextColor(Color.BLACK);
            tvBanText.setVisibility(View.VISIBLE);
            tvUserName.setText(user.firstName+" "+user.lastName);
            if(user.username==null || user.username.isEmpty())
                tvUserLogin.setVisibility(View.GONE);
            else {
                tvUserLogin.setVisibility(View.VISIBLE);
                tvUserLogin.setText("@"+user.username);
            }
        }

        if(banUser.banText!=null)
            tvBanText.setText(banUser.banText);
        else{
            tvBanText.setText("");
            getBanText(banUser);
        }

        if(user.status==null){
            tvUserInfo.setText("");
        }
        else
        if(user.status.getConstructor()== TdApi.UserStatusRecently.CONSTRUCTOR){
            tvUserInfo.setText(R.string.online_last_seen);
        }else if (user.status.getConstructor()== TdApi.UserStatusOffline.CONSTRUCTOR){
            TdApi.UserStatusOffline offline = (TdApi.UserStatusOffline) user.status;
            String time = Utils.TimestampToDate(offline.wasOnline);
            tvUserInfo.setText(mCtx.getString(R.string.last_seen_at)+" "+time);
            MyLog.log(user.status.toString());
        }else if(user.status.getConstructor()== TdApi.UserStatusOnline.CONSTRUCTOR){
            tvUserInfo.setText("online");
        }else{
            tvUserInfo.setText("");
        }

        String s =  mCtx.getString(R.string.text_ban_time);
        if(banUser.banDate==0) {
//            s += " 0\n";
            s = "";
        }
        else
            s += " "+banUser.getBanDate()+"\n";
        if(banUser.banAge==0)
            s += mCtx.getString(R.string.text_ban_permanently);
        else{
            s+= mCtx.getString(R.string.text_ban_temporary);// "Временный бан. Дата разбана: "+banUser.getBanExpiredTime();
        }
        if(s.isEmpty()) {
            //tvOther.setVisibility(View.GONE);
        }
        else {
            tvOther.setText(s);
            //tvOther.setVisibility(View.VISIBLE);
        }

        //int photoId = ;
        Bitmap bmp = user.profilePhoto==null?null:images.getPhoto(user.profilePhoto.small.id);

        if(bmp!=null)
            avatar.setImageBitmap(bmp);
        else
            avatar.setImageResource(R.drawable.no_avatar);

        getUserProfilePhoto(banUser);

        return view;
    }

    private HashMap<Integer, Boolean> tmp = new HashMap<>(0);
    void getUserProfilePhoto(final ChatParticipantBan b){
        if(!tmp.containsKey(b.user.id)){
            tmp.put(b.user.id, true);
            TgH.send(new TdApi.GetUser(b.user.id), new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if(object.getConstructor()== TdApi.User.CONSTRUCTOR){
                        TdApi.User u = (TdApi.User) object;
                        b.user.profilePhoto.small.id = u.profilePhoto.small.id;
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private void getBanText(ChatParticipantBan banUser) {
        ChatParticipantBan ban = DBHelper.getInstance().getBanById(banUser.chat_id, banUser.user.id);
        if(ban==null) return;
        banUser.banText = ban.banText;
        banUser.banAge=ban.banAge;
        banUser.banDate=ban.banDate;
    }

    public void addAll(List<ChatParticipantBan> items){
        list.addAll(items);
        notifyDataSetChanged();
    }


    public void onDestroy(){
        images.onDestroy();
    }

    public ArrayList getList() {
        return list;
    }
}
