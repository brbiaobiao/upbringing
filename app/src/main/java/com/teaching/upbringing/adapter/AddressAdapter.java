package com.teaching.upbringing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.teaching.upbringing.R;
import com.teaching.upbringing.utils.address.OnItemClickLisenter;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author bb
 * @time 2019/11/7 17:20
 * @des ${TODO}
 **/
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyHolder> {
    private Context mContext;
    private List<PoiItem> mList;
    private int selectPosition = -1;
    private OnItemClickLisenter mOnItemClickLisenter;

    public AddressAdapter(Context context, List<PoiItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setList(List<PoiItem> list) {
        this.mList = list;
        selectPosition = -1;
        notifyDataSetChanged();
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectPositon(){
        return selectPosition;
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.mOnItemClickLisenter = onItemClickLisenter;
    }

    @Override
    public AddressAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder myHolder = new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_address_info, parent, false));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.itemView.setTag(position);
        PoiItem poiItem = mList.get(position);
        if (position == selectPosition) {
            holder.mCheckBox.setChecked(true);
        } else {
            holder.mCheckBox.setChecked(false);
        }
        holder.mTvTitle.setText(poiItem.getTitle());
        holder.mTvMessage.setText(poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet());
        holder.itemView.setOnClickListener(view -> {
            int position1 = (Integer) view.getTag();
            setSelectPosition(position1);
            if (null != mOnItemClickLisenter) {
                mOnItemClickLisenter.onItemClick(position1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        TextView mTvMessage;
        CheckBox mCheckBox;


        public MyHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvMessage = itemView.findViewById(R.id.tv_message);
            mCheckBox = itemView.findViewById(R.id.checkBox);
        }
    }

}
