package com.nhom6.davidsonfurniture.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nhom6.davidsonfurniture.R;
import com.nhom6.davidsonfurniture.Utils.ChatView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ChatView> mMessageList;
    private int sender; // 0: user, 1: admin
    private final int MESSAGE_SEND= 0;
    private final int MESSAGE_RECEIVE= 1;
    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

    public MessageListAdapter(Context context, List<ChatView> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    public void setSender(int sender){
        this.sender= sender;
    }

    @Override
    public int getItemViewType(int position) {
        if(sender == MESSAGE_SEND){
            return MESSAGE_SEND;
        }
        else return MESSAGE_RECEIVE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == MESSAGE_SEND) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_mychat, parent, false);
            return new SentMessageHolder(view);
        }
        else if (viewType == MESSAGE_RECEIVE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_appchat, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String message = mMessageList.get(position).getMessage();
        Bitmap bitmap = mMessageList.get(position).getBitmap();
        switch (holder.getItemViewType()) {
            case MESSAGE_SEND:
                ((SentMessageHolder) holder).bind(message, bitmap);
                break;
            case MESSAGE_RECEIVE:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        private ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_chat_message_app);
            timeText = itemView.findViewById(R.id.text_chat_date_app);
        }
        void bind(String message) {
            messageText.setText(message);
            timeText.setText(currentTime + ", " + currentDate);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        ImageView imvPhoto;

        private SentMessageHolder(View itemView) {
            super(itemView);
            imvPhoto= itemView.findViewById(R.id.imvPhotoMessage);
            messageText = itemView.findViewById(R.id.text_chat_message_me);
            timeText = itemView.findViewById(R.id.text_chat_date_me);
        }

        void bind(String message, Bitmap bitmap) {
            imvPhoto.setImageBitmap(bitmap);
            messageText.setText(message);
            timeText.setText(currentTime + ", " + currentDate);
        }
    }
}