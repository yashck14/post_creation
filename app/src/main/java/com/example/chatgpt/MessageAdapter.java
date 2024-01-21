package com.example.chatgpt;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{
    List<message> messageList;
    public MessageAdapter(List<message> messageList) {
        this.messageList = messageList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(chatView);
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        message Message = messageList.get (position);
        if (Message.getSentBy().equals(Message.SENT_BY_ME)){
        holder.leftChat.setVisibility(View.GONE);
        holder.RightChat.setVisibility(View.VISIBLE);
        holder.RightText.setText (Message.getMessage());
    }
    else{
        holder.RightChat.setVisibility(View.GONE);
        holder.leftChat.setVisibility(View.VISIBLE);
        holder.leftText.setText(Message.getMessage());
    }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftChat,RightChat;
        TextView leftText,RightText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChat = itemView.findViewById(R.id.left_chat_view);
            RightChat = itemView.findViewById(R.id.right_chat_view);
            leftText = itemView.findViewById(R.id.left_chat_text_vierf);
            RightText = itemView.findViewById(R.id.right_chat_text_vierf);
        }
    }
}
