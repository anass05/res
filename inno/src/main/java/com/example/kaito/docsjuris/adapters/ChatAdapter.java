package com.example.kaito.docsjuris.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.kaito.docsjuris.R;
import com.example.kaito.docsjuris.models.Message;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private Context context;
    private ArrayList<Message> messages;
    private Bitmap avatar;
    private MediaPlayer mp;

    public ChatAdapter(Context context, ArrayList<Message> messages, Bitmap avatar) {
        this.context = context;
        this.messages = messages;
        this.avatar = avatar;
        mp = MediaPlayer.create(context, R.raw.message_sent);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);

        if (message.isMeSender()) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bubble_me, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bubble_him, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public void loadOldMessages(ArrayList<Message> messages) {
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }

    public void writeMessage(Message message) {
        messages.add(0, message);
        notifyItemInserted(0);
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_from_me);
            timeText = itemView.findViewById(R.id.text_message_time);
        }

        void bind(Message message) {
            messageText.setText(message.getContent());
            timeText.setScaleX(message.getScal());
            timeText.setScaleY(message.getScal());
            timeText.setText(message.getDate());
            timeText.setCompoundDrawablesWithIntrinsicBounds(0, 0, message.getDrawable(), 0);
            if(message.getSound() != 0) {
                mp.start();
            }
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        CircleImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_from_him);
            timeText = itemView.findViewById(R.id.text_message_time);
            profileImage = itemView.findViewById(R.id.chat_image);
        }

        void bind(Message message) {
            messageText.setText(message.getContent());
            timeText.setText(message.getDate());
            profileImage.setImageBitmap(avatar);
        }
    }

}