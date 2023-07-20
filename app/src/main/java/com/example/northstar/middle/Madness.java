package com.example.northstar.middle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.northstar.R;

import java.util.ArrayList;

public class Madness extends ArrayAdapter<FindRequest> {
    public ArrayList<FindRequest> MainList;
    public ArrayList<FindRequest> SubjectListTemp;
    public Madness.SubjectDataFilter subjectDataFilter;

    public Madness(Context context, int id, ArrayList<FindRequest> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new Madness.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Madness.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.wakuu, null);

            holder = new Madness.ViewHolder();

            holder.Request = convertView.findViewById(R.id.onreq);
            holder.Company = convertView.findViewById(R.id.onComp);

            convertView.setTag(holder);

        } else {
            holder = (Madness.ViewHolder) convertView.getTag();
        }

        FindRequest subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.Request.setText(+pos + ".  " + subject.getField());
        pos++;
        holder.Company.setText(subject.getCompany());

        return convertView;

    }

    public class ViewHolder {
        TextView Request;
        TextView Company;
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

