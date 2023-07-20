package com.example.northstar.middle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.northstar.R;

import java.util.ArrayList;

public class RadaSafi extends ArrayAdapter<FindRequest> {
    public ArrayList<FindRequest> MainList;
    public ArrayList<FindRequest> SubjectListTemp;
    public RadaSafi.SubjectDataFilter subjectDataFilter;

    public RadaSafi(Context context, int id, ArrayList<FindRequest> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<FindRequest>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<FindRequest>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new RadaSafi.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RadaSafi.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.muembe, null);

            holder = new RadaSafi.ViewHolder();

            holder.Completion = convertView.findViewById(R.id.sitedCompletion);
            holder.Company = convertView.findViewById(R.id.sitedComp);
            holder.Field = convertView.findViewById(R.id.sitedField);
            holder.imageView = convertView.findViewById(R.id.sitedImage);

            convertView.setTag(holder);

        } else {
            holder = (RadaSafi.ViewHolder) convertView.getTag();
        }

        FindRequest subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.Company.setText(+pos + ".  " + subject.getCompany());
        pos++;
        holder.Field.setText(subject.getField());
        holder.Completion.setText(subject.getEngend());
        Glide.with(convertView).load(subject.getImage()).into(holder.imageView);

        return convertView;

    }

    public class ViewHolder {
        TextView Completion;
        TextView Company;
        TextView Field;
        ImageView imageView;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<FindRequest> arrayList1 = new ArrayList<FindRequest>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    FindRequest subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<FindRequest>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }

}
