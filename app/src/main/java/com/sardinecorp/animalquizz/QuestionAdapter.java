package com.sardinecorp.animalquizz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gonçalo on 25/05/2017.
 */

public class QuestionAdapter extends BaseAdapter{


    private LayoutInflater inflater;
    private List<String> objects;
    private Context mContext;

    public QuestionAdapter(LayoutInflater inflater, List<String> objects, Context mContext) {
        this.inflater = inflater;
        this.objects = objects;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }


    private class ViewHolder {
        TextView question;
        Spinner ans;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {
        ViewHolder holder = null;

        if(view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.question_template, null);
            holder.question = (TextView) view.findViewById(R.id.questionText);
            holder.ans = (Spinner) view.findViewById(R.id.questionSpinner);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.question.setText(objects.get(i));

        final int viewPos = i;

        holder.ans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // change the color of the text
                ((TextView) adapterView.getChildAt(0)).setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                MainActivity.mAnswers.set(viewPos, i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }


}
