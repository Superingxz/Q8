package com.jsonmo.xrefreshview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.ListView;

import com.jsonmo.xrefreshview.ILoadingLayout.State;
/**
 * 这个类实现了ListView下拉刷新，上加载更多和滑到底部自动加载
 * 
 * @author Li Hong
 * @since 2013-8-15
 */
public class PullToRefreshListView extends PullToRefreshBase<ListView> implements OnScrollListener {
    
    /**ListView*/
    private ListView mListView;
    /**用于滑到底部自动加载的Footer*/
    private LoadingLayout mLoadMoreFooterLayout;
    /**滚动的监听器*/
    private OnScrollListener mScrollListener;
    
    /**
     * 构造方法
     * 通过java代码创建时调用
     * @param context context
     */
    public PullToRefreshListView(Context context) {
        this(context, null);
    }
    
    /**
     * 构造方法
     * 通过xml文件引用时调用
     * @param context context
     * @param attrs attrs
     */
    public PullToRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    /**
     * 构造方法
     * 通过xml文件创建时调用,并且指定了style
     * @param context context
     * @param attrs attrs
     * @param defStyle defStyle
     */
    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        setPullLoadEnabled(false);		//设置当前上拉加载是否可用
    }

    @Override
    protected ListView createRefreshableView(Context context, AttributeSet attrs) {
        ListView listView = new ListView(context);
        mListView = listView;
        listView.setOnScrollListener(this);
        
        return listView;
    }
    
    /**
     * 设置是否有更多数据的标志
     * 
     * @param hasMoreData true表示还有更多的数据，false表示没有更多数据了
     */
    public void setHasMoreData(boolean hasMoreData) {
        if (!hasMoreData) {
            if (null != mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout.setState(State.NO_MORE_DATA);
            }
            
            LoadingLayout footerLoadingLayout = getFooterLoadingLayout();
            if (null != footerLoadingLayout) {
                footerLoadingLayout.setState(State.NO_MORE_DATA);
            }
        }
    }

    /**
     * 设置滑动的监听器
     * 
     * @param l 监听器
     */
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;					//有什么用?
    }
    
    @Override
    protected boolean isReadyForPullUp() {
        return isLastItemVisible();		//返回是否是最后一个的true或false
    }

    @Override
    protected boolean isReadyForPullDown() {
        return isFirstItemVisible();	//返回是否是第一个item的true或false
    }

    @Override
    protected void startLoading() {
        super.startLoading();
        
        if (null != mLoadMoreFooterLayout) {
            mLoadMoreFooterLayout.setState(State.REFRESHING);	//设置footer为正在刷新状态
        }
    }
    
    @Override
    public void onPullUpRefreshComplete() {
        super.onPullUpRefreshComplete();
        
        if (null != mLoadMoreFooterLayout) {
            mLoadMoreFooterLayout.setState(State.RESET);	//把上拉加载结束后的状态重置
        }
    }
    
    @Override
    public void setScrollLoadEnabled(boolean scrollLoadEnabled) {	//滑到底时是否自动加载数据
        super.setScrollLoadEnabled(scrollLoadEnabled);
        
        if (scrollLoadEnabled) {
            // 设置Footer
            if (null == mLoadMoreFooterLayout) {	//footer为空则创建
                mLoadMoreFooterLayout = new FooterLoadingLayout(getContext());
            }
            
            if (null == mLoadMoreFooterLayout.getParent()) {	//什么意思?
                mListView.addFooterView(mLoadMoreFooterLayout, null, false);
            }
            mLoadMoreFooterLayout.show(true);
        } else {
            if (null != mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout.show(false);
            }
        }
    }
    
    @Override
    public LoadingLayout getFooterLoadingLayout() {		//得到footer布局对象
        if (isScrollLoadEnabled()) {		//滑动到底部加载是否可用
            return mLoadMoreFooterLayout;
        }

        return super.getFooterLoadingLayout();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isScrollLoadEnabled() && hasMoreData()) {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE 
                    || scrollState == OnScrollListener.SCROLL_STATE_FLING) {
                if (isReadyForPullUp()) {
                    startLoading();
                }
            }
        }
        
        if (null != mScrollListener) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (null != mScrollListener) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }
    
    @Override
    protected LoadingLayout createHeaderLoadingLayout(Context context, AttributeSet attrs) {
        return new RotateLoadingLayout(context);
    }
    
    /**
     * 表示是否还有更多数据
     * 
     * @return true表示还有更多数据
     */
    private boolean hasMoreData() {
        if ((null != mLoadMoreFooterLayout) && (mLoadMoreFooterLayout.getState() == State.NO_MORE_DATA)) {
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
        final Adapter adapter = mListView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            return true;
        }

        int mostTop = (mListView.getChildCount() > 0) ? mListView.getChildAt(0).getTop() : 0;
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
        final Adapter adapter = mListView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            return true;
        }

        final int lastItemPosition = adapter.getCount() - 1;
        final int lastVisiblePosition = mListView.getLastVisiblePosition();

        /**
         * This check should really just be: lastVisiblePosition == lastItemPosition, but ListView
         * internally uses a FooterView which messes the positions up. For me we'll just subtract
         * one to account for it and rely on the inner condition which checks getBottom().
         */
        if (lastVisiblePosition >= lastItemPosition - 1) {
            final int childIndex = lastVisiblePosition - mListView.getFirstVisiblePosition();
            final int childCount = mListView.getChildCount();
            final int index = Math.min(childIndex, childCount - 1);
            final View lastVisibleChild = mListView.getChildAt(index);
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= mListView.getBottom();
            }
        }

        return false;
    }


}
