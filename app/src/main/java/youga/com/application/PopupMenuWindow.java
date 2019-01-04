package youga.com.application;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import youga.com.application.Toolbar.OnPopupMenuItemClickListener;

import static android.widget.ListPopupWindow.MATCH_PARENT;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/01/04 13:40
 * @description:
 */
public class PopupMenuWindow extends PopupWindow {

    private View mSpaceView;
    private RecyclerView mRecyclerView;
    private OnPopupMenuItemClickListener mMenuItemClickListener;

    public PopupMenuWindow(Context context, String[] menus, OnPopupMenuItemClickListener menuItemClickListener) {

        mMenuItemClickListener = menuItemClickListener;
        setWidth(MATCH_PARENT);
        setHeight(MATCH_PARENT);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.transparent)));


        View view = LayoutInflater.from(context).inflate(R.layout.window_popup_menu, null);
        setContentView(view);

        mSpaceView = view.findViewById(R.id.spaceView);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setBackgroundResource(R.drawable.shape_popup_menu_bg);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
        mRecyclerView.setAdapter(new InnerAdapter(menus));

        mSpaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void show(View parent, int yOffset) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mRecyclerView.getLayoutParams();
        params.topMargin = yOffset;
        showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
    }

    public class InnerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private String[] mStrings;

        public InnerAdapter(String[] strings) {
            mStrings = strings;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popup_menu, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.bindPosition(position);
        }

        @Override
        public int getItemCount() {
            return mStrings.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextView;

            ViewHolder(View convertView) {
                super(convertView);
                mTextView = (TextView) convertView;
            }

            public void bindPosition(final int position) {
                mTextView.setText(mStrings[position]);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mMenuItemClickListener != null) {
                            mMenuItemClickListener.onItemClick(mStrings[position], position);
                        }
                        dismiss();
                    }
                });
            }
        }
    }

}
