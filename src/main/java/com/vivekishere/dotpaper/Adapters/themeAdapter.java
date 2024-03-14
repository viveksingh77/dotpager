package com.vivekishere.dotpaper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import androidx.cardview.widget.CardView;

import com.vivekishere.dotpaper.R;

import java.util.ArrayList;

public class themeAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> colorList ;
  OnThemeClickListener onThemeClickListener;

    public interface OnThemeClickListener{
        void  onThemeClick(int position , int itemColor);
    }
    public void setOnThemeClickListener(OnThemeClickListener onThemeClickListener){
        this.onThemeClickListener= onThemeClickListener;
    }

    public themeAdapter(Context context, ArrayList<Integer> colorList) {
        this.context = context;
        this.colorList = colorList;
    }

    @Override
    public int getCount() {
        return colorList.size();
    }

    @Override
    public Object getItem(int i) {
        return colorList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View c, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.color_card,null);
        CardView cardView = view.findViewById(R.id.chooseColor);
        cardView.setCardBackgroundColor(context.getColor(colorList.get(i)));
        view.setOnClickListener(view1 -> {
            if (onThemeClickListener!=null){
                onThemeClickListener.onThemeClick(i ,colorList.get(i)); // i is position
            }
        });
        return view;
    }
}
