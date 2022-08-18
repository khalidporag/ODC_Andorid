package com.purebasicv2.app.ChapterandLecture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.Lecture;
import com.purebasicv2.app.utils.AppConstants;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    public interface LectureItemClickListener {
        void onLectureItemClickListener(Lecture lecture, int type);
    }

    LectureItemClickListener lectureItemClickListener;

    public void setLectureItemClickListener(LectureItemClickListener lectureItemClickListener) {
        this.lectureItemClickListener = lectureItemClickListener;
    }

    private Context _context;
    private List<ExpGroup> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Lecture>> _listDataChild;

    public ExpandableListAdapter(Context context, List<ExpGroup> listDataHeader,
                                 HashMap<String, List<Lecture>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getGroupName())
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Lecture childText = (Lecture) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        TextView txtListChild1 = (TextView) convertView
                .findViewById(R.id.lblListItem1);
        TextView lblListItemSubtitle = (TextView) convertView
                .findViewById(R.id.lblListItemSubtitle);
        Button btnNote = (Button) convertView
                .findViewById(R.id.btnNote);
        Button btnPdf = (Button) convertView
                .findViewById(R.id.btnPdf);
        Button btnVideo = (Button) convertView
                .findViewById(R.id.btnVideo);
        txtListChild.setText(childText.getTitle());
        lblListItemSubtitle.setText(childText.getCategory());
        String upperString = childText.getMember_type().substring(0, 1).toUpperCase() +
                childText.getMember_type().substring(1).toLowerCase();
        txtListChild1.setText(upperString);
        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lectureItemClickListener != null)
                    lectureItemClickListener.onLectureItemClickListener(childText, AppConstants.NOTE_CLICK);
            }
        });
        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lectureItemClickListener != null)
                    lectureItemClickListener.onLectureItemClickListener(childText, AppConstants.PDF_CLICK);
            }
        });
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lectureItemClickListener != null)
                    lectureItemClickListener.onLectureItemClickListener(childText, AppConstants.VIDEO_CLICK);
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        System.out.println("DATA11 " + _listDataHeader.size());
        System.out.println("DATA12 " + _listDataChild.size());
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getGroupName())
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
        ExpGroup headerTitle = (ExpGroup) getGroup(groupPosition);
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        LinearLayout llListHeader = (LinearLayout) convertView.findViewById(R.id.llListHeader);
        lblListHeader.setText(headerTitle.getGroupName());
        if (headerTitle.getExpanded()) {
            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    ResourcesCompat.getDrawable(lblListHeader.getResources(),
                            R.drawable.ic_arrow_up, null), null);
            llListHeader.setBackgroundColor(ContextCompat.getColor(_context,R.color.blue_btn_bg_color));
            lblListHeader.setTextColor(ContextCompat.getColor(_context,R.color.white));

        } else {
            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    ResourcesCompat.getDrawable(lblListHeader.getResources(),
                            R.drawable.ic_arrow_down, null), null);
            llListHeader.setBackgroundColor(ContextCompat.getColor(_context,R.color.white));
            lblListHeader.setTextColor(ContextCompat.getColor(_context,R.color.black_light));


        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
