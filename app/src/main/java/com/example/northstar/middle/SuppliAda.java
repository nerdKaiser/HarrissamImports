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

public class SuppliAda extends ArrayAdapter<SupplMode> {
    public ArrayList<SupplMode> MainList;
    public ArrayList<SupplMode> SubjectListTemp;
    public SuppliAda.SubjectDataFilter subjectDataFilter;

    public SuppliAda(Context context, int id, ArrayList<SupplMode> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new SuppliAda.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SuppliAda.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.new_item, null);

            holder = new SuppliAda.ViewHolder();

            holder.SubjectSupplier = convertView.findViewById(R.id.textView);

            convertView.setTag(holder);

        } else {
            holder = (SuppliAda.ViewHolder) convertView.getTag();
        }

        SupplMode subject = SubjectListTemp.get(position);
        holder.SubjectSupplier.setText("Supplier: " + subject.getSupplier() + "\nAmountPayable: KSH" + subject.getPayment() + "\nStatus: " + subject.getDisburse());

        return convertView;

    }

    public class ViewHolder {
        TextView SubjectSupplier;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<SupplMode> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    SupplMode subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<SupplMode>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }

}