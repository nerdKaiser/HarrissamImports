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

public class FeedOff extends ArrayAdapter<FindRequest> {
    public ArrayList<FindRequest> MainList;
    public ArrayList<FindRequest> SubjectListTemp;
    public FeedOff.SubjectDataFilter subjectDataFilter;

    public FeedOff(Context context, int id, ArrayList<FindRequest> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<FindRequest>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<FindRequest>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new FeedOff.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FeedOff.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.feedhist, null);

            holder = new FeedOff.ViewHolder();

            holder.Request = convertView.findViewById(R.id.fdReq);
            holder.Feed = convertView.findViewById(R.id.fdFd);
            holder.fino = convertView.findViewById(R.id.fdComp);

            convertView.setTag(holder);

        } else {
            holder = (FeedOff.ViewHolder) convertView.getTag();
        }

        FindRequest subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.Request.setText(+pos + ".  " + subject.getField());
        pos++;
        holder.Feed.setText(subject.getClient());
        holder.fino.setText(subject.getEngend());

        return convertView;

    }

    public class ViewHolder {
        TextView Request;
        TextView Feed;
        TextView fino;
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

