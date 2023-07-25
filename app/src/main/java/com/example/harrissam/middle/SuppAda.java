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

public class SuppAda extends ArrayAdapter<SuppFind> {
    public ArrayList<SuppFind> MainList;
    public ArrayList<SuppFind> SubjectListTemp;
    public SubjectDataFilter subjectDataFilter;

    public SuppAda(Context context, int id, ArrayList<SuppFind> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<SuppFind>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<SuppFind>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.sup_getter, null);

            holder = new ViewHolder();

            holder.SubjectSupplier = (TextView) convertView.findViewById(R.id.supplierId);
            holder.SubjectCompany = (TextView) convertView.findViewById(R.id.companyId);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SuppFind subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.SubjectSupplier.setText(+pos + ".  " + subject.getId());
        pos++;
        holder.SubjectCompany.setText(subject.getCompany());

        return convertView;

    }

    public class ViewHolder {
        TextView SubjectSupplier;
        TextView SubjectCompany;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<SuppFind> arrayList1 = new ArrayList<SuppFind>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    SuppFind subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<SuppFind>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }

}



