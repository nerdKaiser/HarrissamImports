package com.example.northstar.middle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.northstar.R;

import java.util.ArrayList;

public class RateAda extends ArrayAdapter<RateMode> {
    public ArrayList<RateMode> MainList;
    public ArrayList<RateMode> SubjectListTemp;
    public RateAda.SubjectDataFilter subjectDataFilter;

    public RateAda(Context context, int id, ArrayList<RateMode> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new RateAda.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RateAda.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.rated, null);

            holder = new RateAda.ViewHolder();
            holder.ratingBar = convertView.findViewById(R.id.rateUs);
            holder.date = convertView.findViewById(R.id.textDate);
            holder.message = convertView.findViewById(R.id.textMess);

            convertView.setTag(holder);

        } else {
            holder = (RateAda.ViewHolder) convertView.getTag();
        }
        RateMode subject = SubjectListTemp.get(position);
        holder.message.setText("ClientID: "+subject.getClient()+"\nCompany: "+subject.getCompany()+"\nContact: "+subject.getPhone()+"\nRequetRef: "+subject.getReg_id()+"\nComment: "+subject.getMessage());
        holder.date.setText(subject.getReg_date());
        float rateValue = Float.parseFloat(subject.getRate());
        holder.ratingBar.setRating(rateValue);
       // holder.ratingBar.setIsIndicator(true);
        return convertView;

    }

    public class ViewHolder {
        TextView message;
        TextView date;
        RatingBar ratingBar;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<RateMode> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    RateMode subject = MainList.get(i);

                    if (subject.toString().toLowerCase().contains(charSequence))

                        arrayList1.add(subject);
                }
                filterResults.count = arrayList1.size();

                filterResults.values = arrayList1;
            } else {
                synchronized (this) {
                    filterResults.values = MainList;

                    filterResults.count = MainList.size();
                }
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            SubjectListTemp = (ArrayList<RateMode>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }
}


