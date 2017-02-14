package com.jsonmo.xrefreshview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;


/**
 * Created by Administrator on 2016/10/14.
 */

public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView>  {
    private RecyclerView mRecyclerView;
    /**用于滑到底部自动加载的Footer*/
    private LoadingLayout mLoadMoreFooterLayout;
    public PullToRefreshRecyclerView(Context context) {
        this(context,null);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPullLoadEnabled(false);		//设置当前上拉加载是否可用
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context);
        mRecyclerView = recyclerView;
        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        return recyclerView;
    }



    @Override
    protected boolean isReadyForPullDown() {
        return isFirstItemVisible();
    }

    @Override
    protected boolean isReadyForPullUp() {
        return isLastItemVisible();
    }

    /**
     * 设置是否有更多数据的标志
     *
     * @param hasMoreData true表示还有更多的数据，false表示没有更多数据了
     */
    public void setHasMoreData(boolean hasMoreData) {
        if (!hasMoreData) {
            if (null != mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout.setState(ILoadingLayout.State.NO_MORE_DATA);
            }

            LoadingLayout footerLoadingLayout = getFooterLoadingLayout();
            if (null != footerLoadingLayout) {
                footerLoadingLayout.setState(ILoadingLayout.State.NO_MORE_DATA);
            }
        }
    }
    /**
     * 表示是否还有更多数据
     *
     * @return true表示还有更多数据
     */
    private boolean hasMoreData() {
        if ((null != mLoadMoreFooterLayout) && (mLoadMoreFooterLayout.getState() == ILoadingLayout.State.NO_MORE_DATA)) {
            return false;
        }

        return true;
    }
    /**
     * 判断第一个child是否完全显示出来
     *
     * @return true完全显示出来，否则false
     */
    private boolean isFirstItemVisible() {
        final RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = mRecyclerView.getAdapter();

        if (null == adapter || adapter.getItemCount() == 0) {
            return true;
        }

        int mostTop = (mRecyclerView.getChildCount() > 0) ? mRecyclerView.getChildAt(0).getTop() : 0;
        if (mostTop >= 0) {
            return true;
        }

        return false;
    }

    /**
     * 判断最后一个child是否完全显示出来
     *
     * @return true完全显示出来，否则false
     */
    private boolean isLastItemVisible() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = mRecyclerView.getScrollState();
        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == mRecyclerView.SCROLL_STATE_IDLE){
            return true;
        }else {
            return false;
        }
    }


}
