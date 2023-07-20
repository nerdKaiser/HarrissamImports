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

public class UploadedSupAdap extends ArrayAdapter<UploadedSup> {
    public ArrayList<UploadedSup> MainList;
    public ArrayList<UploadedSup> SubjectListTemp;
    public UploadedSupAdap.SubjectDataFilter subjectDataFilter;

    public UploadedSupAdap(Context context, int id, ArrayList<UploadedSup> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<UploadedSup>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<UploadedSup>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new UploadedSupAdap.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UploadedSupAdap.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.fetch_image, null);

            holder = new UploadedSupAdap.ViewHolder();

            holder.SubjectSupplier = (TextView) convertView.findViewById(R.id.companyId);
            holder.SubjectCompany = (TextView) convertView.findViewById(R.id.productId);
            holder.SubjectQuantity = (ImageView) convertView.findViewById(R.id.quantityId);

            convertView.setTag(holder);

        } else {
            holder = (UploadedSupAdap.ViewHolder) convertView.getTag();
        }

        UploadedSup subject = SubjectListTemp.get(position);
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
                ArrayList<UploadedSup> arrayList1 = new ArrayList<UploadedSup>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    UploadedSup subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<UploadedSup>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }

}

