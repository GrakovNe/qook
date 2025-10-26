package org.grakovne.reqook.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.grakovne.reqook.R;

import java.util.List;

public class LevelGridAdapter extends ArrayAdapter<Integer> {
    private int maxOpenedLevel;
    private View.OnClickListener clickListener;

    public LevelGridAdapter(Context context, int resource, List<Integer> objects, int maxOpenedLevel, View.OnClickListener listener) {
        super(context, resource, objects);
        this.maxOpenedLevel = maxOpenedLevel;
        this.clickListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder") View view = vi.inflate(R.layout.level_item, null);

        Integer currentLevelNumber = getItem(position);
        if (currentLevelNumber != null) {
            Button levelButton = (Button) view.findViewById(R.id.level_item_button);
            if (levelButton != null) {

                levelButton.setText(String.valueOf(currentLevelNumber));

                if (position < maxOpenedLevel) {
                    levelButton.setBackgroundResource(R.drawable.opened_level_item);
                    levelButton.setClickable(true);
                    levelButton.setOnClickListener(clickListener);
                    levelButton.setId(currentLevelNumber);
                } else {
                    levelButton.setBackgroundResource(R.drawable.closed_level_item);
                    levelButton.setClickable(false);
                }
            }
        }

        return view;
    }
}
