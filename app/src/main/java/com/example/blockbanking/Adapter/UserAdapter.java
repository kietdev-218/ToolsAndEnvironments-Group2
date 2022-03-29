package com.example.blockbanking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockbanking.Interface.UserEventListener;
import com.example.blockbanking.Models.User;
import com.example.blockbanking.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private List<User> users;
    private UserEventListener listener;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final User user = getUser(position);
        if (user != null) {
            holder.user.setText(user.getName());
            holder.birthday.setText(user.getNumberCard());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUserClick(user);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onUserLongClick(user);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public User getUser(int position){
        return users.get(position);
    }


    public void setListener(UserEventListener listener) {
        this.listener = listener;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView user, birthday;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.userNameView);
            birthday = itemView.findViewById(R.id.birthdayView);
        }
    }
}
