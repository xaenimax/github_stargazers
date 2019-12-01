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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false));
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

        @BindView(R.id.user_type_tv)
        TextView userTypeTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void bindData(Stargazer user) {
            Picasso.get()
                    .load(user.avatarUrl)
                    .placeholder(R.drawable.user_placeholder)
                    .into(userImageView);
            usernameTextView.setText(String.format(this.itemView.getContext().getString(R.string.username_text), user.login));
            userUrlTextView.setText(String.format(this.itemView.getContext().getString(R.string.user_url_text), user.htmlUrl));
            userTypeTextView.setText(String.format(this.itemView.getContext().getString(R.string.user_type_text), user.type));

        }
    }
}
