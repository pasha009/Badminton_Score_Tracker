package com.example.ansh.scoretracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/14/2017.
 */

public class ItemListAdapter extends ArrayAdapter<MyItem> {

    private static final String TAG = "ItemListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView winnerScore;
        TextView loserScore;
        TextView setIndex;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public ItemListAdapter(Context context, int resource, ArrayList<MyItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String setIndex = getItem(position).getSetIndex();
        String lscore = getItem(position).getLoserScore();
        String wscore = getItem(position).getWinnerScore();

        //Create the person object with the information
        MyItem person = new MyItem(setIndex, wscore, lscore);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.setIndex = (TextView) convertView.findViewById(R.id.set_index);
            holder.winnerScore = (TextView) convertView.findViewById(R.id.winner_points);
            holder.loserScore = (TextView) convertView.findViewById(R.id.loser_points);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.setIndex.setText(person.getSetIndex() + "");
        holder.winnerScore.setText(person.getWinnerScore() + "");
        holder.loserScore.setText(person.getLoserScore() + "");



        return convertView;
    }
}
























