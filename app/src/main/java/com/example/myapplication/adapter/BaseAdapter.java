package com.example.myapplication.adapter;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.model.Movie;

import java.util.ArrayList;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public abstract class BaseAdapter<E extends Parcelable, L extends ArrayList<E>, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected L elements;

    protected BaseAdapter(L elements){
        this.elements = elements;
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

    @Override
    public int getItemCount() {
        return elements.size();
    }
}
