package com.example.substationadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.substationadmin.Activity.DetailUserActivity;
import com.example.substationadmin.Model.User;
import com.example.substationadmin.R;

import java.util.ArrayList;

public class KelolaUserAdapter extends RecyclerView.Adapter<KelolaUserAdapter.KelolaUserViewHolder>{

    ArrayList<User> listUser = new ArrayList<>();
    private Context context;
    String user = "";

    public KelolaUserAdapter(ArrayList<User> listUser, String user) {
        this.listUser.addAll(listUser);
        this.user = user;
    }

    public KelolaUserAdapter(Context ctx, ArrayList<User> listUser) {
        this.listUser.addAll(listUser);

    }

    @NonNull
    @Override
    public KelolaUserAdapter.KelolaUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelola_user, parent, false);
        return new KelolaUserAdapter.KelolaUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final KelolaUserAdapter.KelolaUserViewHolder holder, final int position) {
        final User user = listUser.get(position);

        final String [] wilayah = holder.itemView.getContext().getResources().getStringArray(R.array.jenis_wilayah);

        holder.tvIdUser.setText(user.getId());
        holder.tvEmail.setText(user.getEmail());
        holder.tvWilayah.setText(wilayah[Integer.valueOf(user.getWilayah())]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailUserActivity.class);
                intent.putExtra("data", listUser.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class KelolaUserViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmail, tvIdUser, tvWilayah;
        CardView cvKelolaUser;

        public KelolaUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvIdUser = itemView.findViewById(R.id.tvIdUser);
            tvWilayah = itemView.findViewById(R.id.tvWilayah);
            cvKelolaUser = itemView.findViewById(R.id.cv_kelola_user);
        }
    }
}


