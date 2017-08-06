package com.example.myapplication.adapter;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myapplication.adapter.listener.BaseListener;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public abstract class BaseAdapter<E extends Parcelable, L extends ArrayList<E>> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder<E>> {

    protected L elements;
    protected BaseListener<E> listener;

    protected BaseAdapter(L elements, BaseListener<E> listener){
        this.elements = elements;
        this.listener = listener;
    }

    protected BaseAdapter(L elements){
        this(elements,null);
    }

    public void addAll(ArrayList<E> newElements){
        elements.addAll(newElements);
        notifyDataSetChanged();
    }

    public void add(E element){
        elements.add(element);
        notifyDataSetChanged();
    }

    public void remove(E element){
        elements.remove(element);
        notifyDataSetChanged();
    }

    public void removeAll(){
        elements.clear();
        notifyDataSetChanged();
    }

    public L getElements() {
        return elements;
    }

    public void updateMovie(E oldElement, E newElemnt){
        elements.set(elements.indexOf(oldElement), newElemnt);
    }

    public boolean isEmpty(){
        return elements.isEmpty();
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(elements.get(position));
    }


    public static class BaseViewHolder<E> extends RecyclerView.ViewHolder{

        private E element;

        public BaseViewHolder(View itemView, final BaseListener<E> listener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            if(listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(element);
                    }
                });
            }
        }

        public void setData(E element){
            this.element = element;
        }

    }

}
