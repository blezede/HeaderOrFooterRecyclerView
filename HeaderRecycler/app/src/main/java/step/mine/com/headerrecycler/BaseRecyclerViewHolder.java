package step.mine.com.headerrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Base RecyclerViewHolder
 */
public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        getView(itemView);
    }

    public abstract void getView(View v);
}
