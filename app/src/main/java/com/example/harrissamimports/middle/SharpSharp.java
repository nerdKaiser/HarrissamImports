package com.example.harrissamimports.middle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.harrissamimports.R;

import java.util.ArrayList;

public class SharpSharp extends ArrayAdapter<FindRequest> {
    public ArrayList<FindRequest> MainList;
    public ArrayList<FindRequest> SubjectListTemp;
    public SharpSharp.SubjectDataFilter subjectDataFilter;

    public SharpSharp(Context context, int id, ArrayList<FindRequest> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<FindRequest>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<FindRequest>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new SharpSharp.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SharpSharp.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.complete, null);

            holder = new SharpSharp.ViewHolder();

            holder.Request = convertView.findViewById(R.id.onreq);
            holder.Company = convertView.findViewById(R.id.onComp);
            holder.Ending = convertView.findViewById(R.id.onkuisha);

            convertView.setTag(holder);

        } else {
            holder = (SharpSharp.ViewHolder) convertView.getTag();
        }

        FindRequest subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.Request.setText(+pos + ".  " + subject.getField());
        pos++;
        holder.Company.setText(subject.getCompany());
        holder.Ending.setText(subject.getEngineer());

        return convertView;

    }

    public class ViewHolder {
        TextView Request;
        TextView Company;
        TextView Ending;
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
