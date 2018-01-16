package step.mine.com.headerrecycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CustomAdapter mCustomAdapter;
    private List<Integer> mData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initData();
    }

    private void initData() {
        mData.clear();
        for (int i = 0; i < 30; i ++) {
            mData.add(i);
        }
        mCustomAdapter.setData(mData);
        mRecyclerView.setAdapter(mCustomAdapter);
    }

    private void init() {
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mCustomAdapter = new CustomAdapter(this);
        mCustomAdapter.setFooterView(this, R.layout.header_or_footer_layout);
        mCustomAdapter.setHeaderView(this, R.layout.header_or_footer_layout);
        mCustomAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<Integer>() {
            @Override
            public void onItemClick(int position, Integer data) {
                Toast.makeText(getApplicationContext(), data + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
