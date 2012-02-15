package org.agetac.menu;

import java.util.ArrayList;

import org.agetac.R;
import org.agetac.pictogram.PictogramGroup;
import org.agetac.pictogram.impl.Pictogram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MenuExpandableListView extends BaseExpandableListAdapter {
	
	private Context context;
	private ArrayList<PictogramGroup> groups;
	private LayoutInflater inflater;
	
	public MenuExpandableListView(Context context, ArrayList<PictogramGroup> groups) {
		 this.context = context;
		 this.groups = groups;
		 inflater = LayoutInflater.from(context);
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).getPictos().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final Pictogram picto = (Pictogram) getChild(groupPosition, childPosition);
		
		ChildViewHolder childView;
		if(convertView == null) {
			childView = new ChildViewHolder();
			
			convertView = inflater.inflate(R.layout.menu_sitac_childs, null);
			//childView.imgChild = (Bitmap) convertView.findViewById(id)(R.id.img);
			childView.textViewChild = (TextView) convertView.findViewById(R.id.sitac_child);

			
			convertView.setTag(childView);
		}
		else {
			childView = (ChildViewHolder) convertView.getTag();
		}
		childView.textViewChild.setText(picto.getName());
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).getPictos().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		GroupViewHolder groupView;
		PictogramGroup group = (PictogramGroup) getGroup(groupPosition);
		
		if(convertView == null) {
			groupView = new GroupViewHolder();
			convertView = inflater.inflate(R.layout.menu_sitac_groups, null);
			
			groupView.textViewGroup = (TextView) convertView.findViewById(R.id.sitac_group);
			convertView.setTag(groupView);
		}
		else {
			groupView = (GroupViewHolder) convertView.getTag();
		}
		groupView.textViewGroup.setText(group.getGroupName());
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	

    class GroupViewHolder {
        public TextView textViewGroup;
    }
    
    class ChildViewHolder {
        public TextView textViewChild;
        //public Bitmap imgChild;
    }
}
