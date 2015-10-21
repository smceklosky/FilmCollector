package com.example.susie.filmcollector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CustomAdapter extends ArrayAdapter<ActorData>{

    ActorData actor;

    // create an adapter for the Movie activity.  the adapter should contain
    // the actors stored in the actor table and it should be formatted like
    // the custom_row resource
    public CustomAdapter(Context context, ActorData[] actors) {
        super(context, R.layout.custom_row, actors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        // get actor data at the current position
        actor = getItem(position);

        // get references to the first and last name and id TextViews in the custom_row resource
        TextView firstName = (TextView) customView.findViewById(R.id.tvFirstName);
        TextView lastName = (TextView) customView.findViewById(R.id.tvLastName);
        TextView id = (TextView) customView.findViewById(R.id.tvId);

        // set the text in the TextViews based on the data in the actor
        firstName.setText(actor.getFirstName());
        lastName.setText(actor.getLastName());
        id.setText(Integer.toString(actor.getId()));

        return customView;
    }
}
