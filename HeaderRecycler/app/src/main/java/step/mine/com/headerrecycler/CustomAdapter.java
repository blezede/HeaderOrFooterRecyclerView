package step.mine.com.headerrecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by weflow on 2018/1/15.
 */

public class CustomAdapter extends BaseRecyclerAdapter<Integer> {

    Context mContext;
    public CustomAdapter(Context c) {
        this.mContext = c;
    }

    @Override
    protected BaseRecyclerViewHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, null);
        return new CustomHolder(view);
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position) {
        CustomHolder customHolder = (CustomHolder) holder;
        customHolder.content.setText(mData.get(position - 1) + "");
    }

    class CustomHolder extends BaseRecyclerViewHolder {

        public RelativeLayout container;
        public TextView content;
        public CustomHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void findView(View v) {
            container = itemView.findViewById(R.id.container);
            content = itemView.findViewById(R.id.content);
            ViewGroup.LayoutParams layoutParams = container.getLayoutParams();
            layoutParams.height = mContext.getResources().getDisplayMetrics().widthPixels / 3;
            container.setLayoutParams(layoutParams);
            Random random = new Random();
            int ranColor = 0xff000000 | random.nextInt(0x00ffffff);
            content.setBackgroundColor(ranColor);
        }
    }
}
