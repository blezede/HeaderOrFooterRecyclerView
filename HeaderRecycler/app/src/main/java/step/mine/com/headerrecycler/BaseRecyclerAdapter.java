package step.mine.com.headerrecycler;

/**
 * Created by Step on 2018/1/15.
 */

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseRecyclerViewHolder>{
    /**
     * normal item view type
     */
    public static final int TYPE_NORMAL = 0;
    /**
     * header view type
     */
    public static final int TYPE_HEADER = 1;
    /**
     * footer view type
     */
    public static final int TYPE_FOOTER = 2;
    private View mHeaderView;
    private View mFooterView;
    private OnItemClickListener onItemClickListener;

    public List<T> mData = new ArrayList<>();

    public void setData(List< T > data){
        if (data == null) {
            mData.clear();
            return;
        }
        mData.clear();
        mData.addAll(data);
    }

    public T getItemData(int dataPosition) {
        T res = null;
        if(dataPosition < mData.size()) {
            res = mData.get(dataPosition);
        }
        return res;
    }

    public void clearData(){
        if(mData != null){
            mData.clear();
        }
    }

    public int getDataSize() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener li) {
        onItemClickListener = li;
    }

    /**
     * Add header view
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    /**
     * Add header view res
     * @param c,layoutId
     */
    public void setHeaderView(Context c, int layoutId) {
        if (c == null && layoutId < 0) {
            return;
        }
        mHeaderView = LayoutInflater.from(c).inflate(layoutId, (ViewGroup)null);
        notifyItemInserted(0);
    }

    /**
     * Get header view
     * @return
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    /**
     * Add footer view
     * @param footerView
     */
    public void setFooterView(View footerView) {
        this.mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * Add footer view res
     * @param c,layoutId
     */
    public void setFooterView(Context c, int layoutId) {
        if (c == null && layoutId < 0) {
            return;
        }
        this.mFooterView = LayoutInflater.from(c).inflate(layoutId, (ViewGroup)null);
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * Get footer view
     * @return
     */
    public View getFooterView() {
        return this.mFooterView;
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        }
        if (mFooterView != null && position == getItemCount() -1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    /**
     * 获得item的实际数据位置
     * @param holder
     * @return
     */
    private int getRealPosition(BaseRecyclerViewHolder holder) {
        int position = holder.getPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new FooterViewHolder(mFooterView);
        }
        return onCreateRecyclerViewHolder(parent, viewType);
    }


    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        //如果是header footer view就直接返回,不需要绑定数据
        if(getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        }
        final int pos = getRealPosition(holder);
        final T data = mData.get(pos);
        bindData(holder ,position);

        if(onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(pos, data);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int count = mData.size();
        if (mHeaderView != null) {
            count ++;
        }
        if (mFooterView != null) {
            count ++;
        }
        return count;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = ((GridLayoutManager) manager);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == TYPE_HEADER) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (getItemViewType(position) == TYPE_FOOTER) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
        //LinearLayoutManager不需要设置
    }

    /**
     * Create item view holder
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract BaseRecyclerViewHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType);

    /**
     * Bind data.
     * @param holder
     * @param position
     */
    public abstract void bindData(BaseRecyclerViewHolder holder, int position);

    static abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

        public BaseRecyclerViewHolder(View itemView) {
            super(itemView);
            findView(itemView);
        }

        public abstract void findView(View v);
    }

    /**
     *header view ViewHolder
     */
    class HeaderViewHolder extends BaseRecyclerViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void findView(View v) {
        }
    }

    /**
     *footer view ViewHolder
     */
    class FooterViewHolder extends BaseRecyclerViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void findView(View v) {
        }
    }

    /**
     * item 点击事件接口
     * @param <T>position 数据实际位置
     */
    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}