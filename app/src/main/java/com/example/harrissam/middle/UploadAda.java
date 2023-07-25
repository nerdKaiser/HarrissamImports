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

public class UploadAda extends ArrayAdapter<UploadMod> {
    public ArrayList<UploadMod> MainList;
    public ArrayList<UploadMod> SubjectListTemp;
    public UploadAda.SubjectDataFilter subjectDataFilter;

    public UploadAda(Context context, int id, ArrayList<UploadMod> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<UploadMod>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<UploadMod>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new UploadAda.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UploadAda.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.fetched_data, null);

            holder = new UploadAda.ViewHolder();

            holder.SubjectSupplier = (TextView) convertView.findViewById(R.id.companyId);
            holder.SubjectCompany = (TextView) convertView.findViewById(R.id.productId);
            holder.SubjectQuantity = (TextView) convertView.findViewById(R.id.quantityId);

            convertView.setTag(holder);

        } else {
            holder = (UploadAda.ViewHolder) convertView.getTag();
        }

        UploadMod subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.SubjectSupplier.setText(+pos + ".  " + subject.getCompany());
        pos++;
        holder.SubjectCompany.setText(subject.getProduct());
        holder.SubjectQuantity.setText(subject.getQuantity() + " units");

        return convertView;

    }

    public class ViewHolder {
        TextView SubjectSupplier;
        TextView SubjectCompany;
        TextView SubjectQuantity;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<UploadMod> arrayList1 = new ArrayList<UploadMod>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    UploadMod subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<UploadMod>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }

}
