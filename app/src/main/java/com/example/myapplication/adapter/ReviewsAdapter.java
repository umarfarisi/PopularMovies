package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.listener.BaseListener;
import com.example.myapplication.model.Review;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public class ReviewsAdapter extends BaseAdapter<Review, ArrayList<Review>> {

    public ReviewsAdapter() {
        super(new ArrayList<Review>());
    }

    @Override
    public BaseViewHolder<Review> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reviews,parent,false),listener);
    }

    class ReviewsViewHolder extends BaseViewHolder<Review>{

        @BindView(R.id.tv_reviews_author)
        TextView reviewsAuthorTV;
        @BindView(R.id.tv_reviews_content)
        TextView reviewsContentTV;

        public ReviewsViewHolder(View itemView, BaseListener<Review> listener) {
            super(itemView, listener);
        }

        @Override
        public void setData(Review review) {
            super.setData(review);
            reviewsAuthorTV.setText(review.getAuthor());
            reviewsContentTV.setText(review.getContent());
        }
    }

}
