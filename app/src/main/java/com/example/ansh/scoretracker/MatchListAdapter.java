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



public class MatchListAdapter extends ArrayAdapter<Match> {

    private static final String TAG = "MatchListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView winner;
        TextView loser;
        TextView setThing;
    }

    /**
     * Default constructor for the MatchListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public MatchListAdapter(Context context, int resource, ArrayList<Match> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the Matchs information
        String winner = getItem(position).get_winner();
        String loser = getItem(position).get_loser();
        String setThing = getItem(position).get_setString();

        //Create the Match object with the information
        Match match = new Match(winner, loser, setThing);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.winner = (TextView) convertView.findViewById(R.id.Winner);
            holder.loser = (TextView) convertView.findViewById(R.id.Loser);
            holder.setThing = (TextView) convertView.findViewById(R.id.setThing);
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

        holder.winner.setText(match.get_winner());
        holder.loser.setText(match.get_loser());
        holder.setThing.setText(match.get_setString());


        return convertView;
    }
}























