package com.example.kaito.docsjuris.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.kaito.docsjuris.BuildConfig;
import com.example.kaito.docsjuris.MessengerActivity;
import com.example.kaito.docsjuris.R;
import com.example.kaito.docsjuris.helpers.ImageDownloader;
import com.example.kaito.docsjuris.helpers.VolleyRequest;
import com.example.kaito.docsjuris.models.ChatRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by one on 28/7/16.
 */
public class ChatRoomAdapter extends ArrayAdapter<ChatRoom> implements ImageDownloader.DownloadImageListener {

    Activity context;
    List<ChatRoom> items;
    Bitmap profileImage;

    public ChatRoomAdapter(Activity mainActivity, ArrayList<ChatRoom> dataArrayList) {
        super(mainActivity, 0, dataArrayList);
        this.context = mainActivity;
        this.items = dataArrayList;
    }

    @Override
    public void onDownloadImageDone(int responseCode, Bitmap result) {
        this.profileImage = result;
    }


    private class ViewHolder {
        TextView description, name, last_action, unseen_number;
        CircleImageView image;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatRoomAdapter.ViewHolder holder;
        ChatRoom chatRoom = items.get(position);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatroom_item, parent, false);

            holder = new ChatRoomAdapter.ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.description = convertView.findViewById(R.id.description);
            holder.last_action = convertView.findViewById(R.id.last_action);
            holder.unseen_number = convertView.findViewById(R.id.unseen_number);
            holder.image = convertView.findViewById(R.id.image);

            convertView.findViewById(R.id.container).setOnClickListener(e -> {
                Intent i = new Intent(context, MessengerActivity.class);
                i.putExtra("chatRoomId", chatRoom.getId());
                i.putExtra("name", chatRoom.getName());
                i.putExtra("avatar", chatRoom.getAvatar());
                context.startActivity(i);

                if (chatRoom.getTotalUnseen() > 0) {
                    new android.os.Handler().postDelayed(() -> holder.unseen_number.setVisibility(View.INVISIBLE), 300);
                    new VolleyRequest(BuildConfig.API_URL + "api/chatrooms/clients/seen/" + chatRoom.getId(), Request.Method.GET,
                            null, context, null, 0).execute();
                }
            });

            convertView.setTag(holder);

        } else {
            holder = (ChatRoomAdapter.ViewHolder) convertView.getTag();
        }


        holder.name.setText(chatRoom.getName());
        holder.description.setText(chatRoom.getPreview());
        if (chatRoom.getTotalUnseen() > 0)
            holder.unseen_number.setText("" + chatRoom.getTotalUnseen());
        else
            holder.unseen_number.setVisibility(View.INVISIBLE);

        holder.last_action.setText(chatRoom.getLastSeen());
        new ImageDownloader(holder.image, 0, this).execute(chatRoom.getAvatar());

        return convertView;
    }


}