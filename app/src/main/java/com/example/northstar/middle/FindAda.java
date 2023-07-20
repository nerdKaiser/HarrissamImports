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

public class FindAda extends ArrayAdapter<FindRequest> {
    public ArrayList<FindRequest> MainList;
    public ArrayList<FindRequest> SubjectListTemp;
    public FindAda.SubjectDataFilter subjectDataFilter;

    public FindAda(Context context, int id, ArrayList<FindRequest> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<FindRequest>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<FindRequest>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new FindAda.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FindAda.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.fetche_me, null);

            holder = new FindAda.ViewHolder();

            holder.Request = convertView.findViewById(R.id.ftReq);
            holder.Status = convertView.findViewById(R.id.ftStat);

            convertView.setTag(holder);

        } else {
            holder = (FindAda.ViewHolder) convertView.getTag();
        }

        FindRequest subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.Request.setText(+pos + ".  " + subject.getField());
        pos++;
        holder.Status.setText(subject.getMgrstatus());

        return convertView;

    }

    public class ViewHolder {
        TextView Request;
        TextView Status;
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

