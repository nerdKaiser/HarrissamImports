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

public class EngAda extends ArrayAdapter<EngFate> {
    public ArrayList<EngFate> MainList;
    public ArrayList<EngFate> SubjectListTemp;
    public EngAda.SubjectDataFilter subjectDataFilter;

    public EngAda(Context context, int id, ArrayList<EngFate> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<EngFate>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<EngFate>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new EngAda.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EngAda.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.fetche_me, null);

            holder = new EngAda.ViewHolder();

            holder.Request = convertView.findViewById(R.id.ftReq);
            holder.Status = convertView.findViewById(R.id.ftStat);

            convertView.setTag(holder);

        } else {
            holder = (EngAda.ViewHolder) convertView.getTag();
        }

        EngFate subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.Request.setText(+pos + ".  " + subject.getField());
        pos++;
        holder.Status.setText(subject.getId());

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
                ArrayList<EngFate> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    EngFate subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<EngFate>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }

}


