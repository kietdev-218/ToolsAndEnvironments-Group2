package com.example.blockbanking.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockbanking.Models.Block;
import com.example.blockbanking.R;

import java.util.List;

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BlockViewHolder>{
    List<Block> blocks;

    public BlockAdapter(List<Block> blocks) {
        this.blocks = blocks;
    }

    @NonNull
    @Override
    public BlockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.block_layout,parent,false);
        return new BlockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockViewHolder holder, int position) {
        holder.moneysend.setText("Money: " +blocks.get(position).getData().getMoneySend() +" VND");
        holder.nametake.setText("Name take: "+blocks.get(position).getData().getNameTake());
        holder.namesend.setText("Name send: "+blocks.get(position).getData().getNameSend());
        holder.messenger.setText("Messenger: "+blocks.get(position).getData().getMessenger());
        holder.datesend.setText(blocks.get(position).getTimeStamp());
    }

    @Override
    public int getItemCount() {
        return blocks.size();
    }

    public class BlockViewHolder extends RecyclerView.ViewHolder {
        TextView namesend, nametake, moneysend, messenger, datesend;
        public BlockViewHolder(@NonNull View itemView) {
            super(itemView);
            messenger = itemView.findViewById(R.id.messengersend);
            namesend = itemView.findViewById(R.id.namesend);
            nametake = itemView.findViewById(R.id.nametake);
            moneysend = itemView.findViewById(R.id.moneysend);
            datesend = itemView.findViewById(R.id.datesend);
        }
    }
}
