package com.example.githubstargazers.ui.stargazer_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubstargazers.R;
import com.example.githubstargazers.model.Stargazer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StargazerListAdapter extends RecyclerView.Adapter<StargazerListAdapter.ViewHolder> {
    private List<Stargazer> mStargazerList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        ButterKnife.bind(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stargazer user = mStargazerList.get(position);
        holder.bindData(user);

    }

    @Override
    public int getItemCount() {
        return mStargazerList.size();
    }

    void setItemList(List<Stargazer> stargazers) {
        mStargazerList = stargazers;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_image_iv)
        ImageView userImageView;
        @BindView(R.id.username_tv)
        TextView usernameTextView;

        @BindView(R.id.user_url_tv)
        TextView userUrlTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void bindData(Stargazer user) {
            Picasso.get()
                    .load(user.avatarUrl)
                    .placeholder(R.drawable.user_placeholder)
                    .into(userImageView);
            usernameTextView.setText(user.login);
            userUrlTextView.setText(user.htmlUrl);

        }
    }
}
