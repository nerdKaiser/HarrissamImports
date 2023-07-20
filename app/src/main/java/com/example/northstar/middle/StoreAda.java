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

public class StoreAda extends ArrayAdapter<StoreHouse> {
    public ArrayList<StoreHouse> MainList;
    public ArrayList<StoreHouse> SubjectListTemp;
    public StoreAda.SubjectDataFilter subjectDataFilter;

    public StoreAda(Context context, int id, ArrayList<StoreHouse> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<StoreHouse>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<StoreHouse>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new StoreAda.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoreAda.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.fetch_image, null);

            holder = new StoreAda.ViewHolder();

            holder.SubjectSupplier = (TextView) convertView.findViewById(R.id.companyId);
            holder.SubjectCompany = (TextView) convertView.findViewById(R.id.productId);
            holder.SubjectQuantity = (ImageView) convertView.findViewById(R.id.quantityId);

            convertView.setTag(holder);

        } else {
            holder = (StoreAda.ViewHolder) convertView.getTag();
        }

        StoreHouse subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.SubjectSupplier.setText(+pos + ".  " + subject.getCompany());
        pos++;
        holder.SubjectCompany.setText(subject.getProduct());
        Glide.with(convertView).load(SubjectListTemp.get(position).getImage()).into(holder.SubjectQuantity);
        return convertView;

    }

    public class ViewHolder {
        TextView SubjectSupplier;
        TextView SubjectCompany;
        ImageView SubjectQuantity;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<StoreHouse> arrayList1 = new ArrayList<StoreHouse>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    StoreHouse subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<StoreHouse>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }

}
