package com.example.b_can.FirstAppliSwipe.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b_can.FirstAppliSwipe.Activities.Conversation;
import com.example.b_can.FirstAppliSwipe.R;
import com.example.b_can.FirstAppliSwipe.SwipeToDismiss.SwipeDismissListViewTouchListener;

/**
 * Created by b_can on 13/02/2015.
 */

public class ConvSectionFragment extends Fragment {

    View rootView;
    ExpandableListView lv;
    private String[] groups;
    private String[][] children;

    public ConvSectionFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groups = new String[]{"ExpandableList1", "ExpandableList2"};

        children = new String[][]{
                {
                        "Test 1 1 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 1 2 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 1 3 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 1 4 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 1 5 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text."
                },
                {
                        "Test 2 1 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 2 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 3 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 4 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 5 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 6 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 7 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 8 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 9 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 10 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 11 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 12 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 13 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 14 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 15 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 16 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 17 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 18 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 19 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text.",
                        "Test 2 20 : Nom, abracadabra, test. Rajout de text pour tester : Contrary to popular belief, Lorem Ipsum is not simply random text."
                }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_section_conv, container, false);

        // Listview on child click listener
        lv = (ExpandableListView) rootView.findViewById(R.id.lvExp);
        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), Conversation.class);
                //TODO add bundle parameters
                startActivity(intent);
                return false;
            }
        });


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.lvExp);
        final ExpandableListAdapter adapter = new ExpandableListAdapter(groups, children);
        lv.setAdapter(adapter);
        lv.setGroupIndicator(null);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        lv,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                }
                            }
                        },
                        true);
        lv.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        lv.setOnScrollListener(touchListener.makeScrollListener());

    }


    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private String[] groups;
        private String[][] children;

        public ExpandableListAdapter(String[] groups, String[][] children) {
            this.groups = groups;
            this.children = children;
            inf = LayoutInflater.from(getActivity());
        }

        @Override
        public int getGroupCount() {
            return groups.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = inf.inflate(R.layout.list_item, parent, false);
                holder = new ViewHolder();

                holder.text = (TextView) convertView.findViewById(R.id.lblListItem);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getChild(groupPosition, childPosition).toString());

            return convertView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = inf.inflate(R.layout.list_group, parent, false);

                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.lblListHeader);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getGroup(groupPosition).toString());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private class ViewHolder {
            TextView text;
        }
    }
}