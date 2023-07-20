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

public class PayerAda extends ArrayAdapter<PayerMode> {
    public ArrayList<PayerMode> MainList;
    public ArrayList<PayerMode> SubjectListTemp;
    public PayerAda.SubjectDataFilter subjectDataFilter;

    public PayerAda(Context context, int id, ArrayList<PayerMode> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new PayerAda.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PayerAda.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.fetch_cash, null);

            holder = new PayerAda.ViewHolder();

            holder.Company = convertView.findViewById(R.id.cfComp);
            holder.Amount = convertView.findViewById(R.id.cfAmount);
            holder.Status = convertView.findViewById(R.id.cfStatus);

            convertView.setTag(holder);

        } else {
            holder = (PayerAda.ViewHolder) convertView.getTag();
        }

        PayerMode subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.Company.setText(+pos + ".  " + subject.getCompany());
        pos++;
        holder.Amount.setText("KSHs." + subject.getAmount());
        holder.Status.setText(subject.getStatus());

        return convertView;

    }

    public class ViewHolder {
        TextView Company;
        TextView Amount;
        TextView Status;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<PayerMode> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    PayerMode subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<PayerMode>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }

}

