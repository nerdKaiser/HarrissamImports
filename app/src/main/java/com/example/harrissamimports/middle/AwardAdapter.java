package com.example.harrissamimports.middle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.harrissamimports.R;

import java.util.ArrayList;

public class AwardAdapter extends ArrayAdapter<AwardMod> {
    public ArrayList<AwardMod> MainList;
    public ArrayList<AwardMod> SubjectListTemp;
    public AwardAdapter.SubjectDataFilter subjectDataFilter;

    public AwardAdapter(Context context, int id, ArrayList<AwardMod> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<AwardMod>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<AwardMod>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new AwardAdapter.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AwardAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.view_image, null);

            holder = new AwardAdapter.ViewHolder();
            holder.SubjectQuantity = (ImageView) convertView.findViewById(R.id.theAward);

            convertView.setTag(holder);

        } else {
            holder = (AwardAdapter.ViewHolder) convertView.getTag();
        }
        Glide.with(convertView).load(SubjectListTemp.get(position).getImage()).into(holder.SubjectQuantity);
        return convertView;

    }

    public class ViewHolder {
        ImageView SubjectQuantity;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<AwardMod> arrayList1 = new ArrayList<AwardMod>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    AwardMod subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<AwardMod>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }

}