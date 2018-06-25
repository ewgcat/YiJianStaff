package com.yijian.staff.tab.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import com.yijian.staff.R;
import com.yijian.staff.tab.TabMenuHelper;
import com.yijian.staff.tab.adapter.holder.MenuEditRecyclerListHolder;
import com.yijian.staff.tab.entity.EditItem;
import com.yijian.staff.tab.entity.MenuItem;
import com.yijian.staff.tab.listener.OnDeleteListener;
import com.yijian.staff.tab.recyclerview.BaseHeaderFooterRecyclerAdapterWrapper;


/**
 * 描述:编辑页的头部包裹适配器，因为只有头部，所以尾部的代码不用写
 * <p>
 * 作者:陈俊森
 * 创建时间:2017年11月03日 17:57
 * 邮箱:chenjunsen@outlook.com
 *
 * @version 1.0
 */
public class MenuRecyclerListHeaderWrapper extends BaseHeaderFooterRecyclerAdapterWrapper<EditItem, EditItem, MenuEditRecyclerListHolder, MenuEditRecyclerListHolder> implements RecyclerViewDragDropManager.OnItemDragEventListener {
    private OnDeleteListener onDeleteClickListener;
    private RecyclerViewDragDropManager mDragDropManager;
    private MenuHeaderRecyclerGridAdapter adapter;//原装适配器
    private RecyclerView.Adapter dragAdapter;//对原装适配器封装包裹后的实现了拖拽功能的适配器
    private int itemMoveMode = RecyclerViewDragDropManager.ITEM_MOVE_MODE_DEFAULT;
    private boolean hasDragChanged;//优化刷新数据的参数，是否发生过拖拽
    private MenuRecyclerListAdapter listAdapter;
    private boolean showEditIcon = true;


    public void setOnDeleteListener(OnDeleteListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    /**
     * 具有设置Header和Footer功能的RecyclerView适配器
     *
     * @param adapter 原始的RecyclerView适配器
     */
    public MenuRecyclerListHeaderWrapper(RecyclerView.Adapter adapter) {
        super(adapter);
        this.listAdapter = (MenuRecyclerListAdapter) adapter;
    }

    @Override
    public MenuEditRecyclerListHolder createHeaderViewHolder(ViewGroup parent, int viewType) {
        return new MenuEditRecyclerListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.header_menu_edit_recycler, parent, false));
    }

    @Override
    public MenuEditRecyclerListHolder createFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void bindHeaderViewHolder(MenuEditRecyclerListHolder headerViewHolder, EditItem headerItem) {
        headerViewHolder.tv_group_name.setText(headerItem.getGroup());
        adapter = new MenuHeaderRecyclerGridAdapter(headerItem.getMenuItemList(), headerViewHolder.recyclerView, headerViewHolder.recyclerView.getContext());
        adapter.setOnDeleteListener(onDeleteClickListener);
        mDragDropManager = new RecyclerViewDragDropManager();
        mDragDropManager.setItemMoveMode(itemMoveMode);
        // Start dragging after long press
        mDragDropManager.setInitiateOnLongPress(true);
        mDragDropManager.setOnItemDragEventListener(null);
        mDragDropManager.setInitiateOnMove(false);
        mDragDropManager.setLongPressTimeout(750);
        mDragDropManager.setOnItemDragEventListener(this);
        // setup dragging item effects (NOTE: DraggableItemAnimator is required)
//        dragDropManager.setDragStartItemAnimationDuration(250);
//        dragDropManager.setDraggingItemAlpha(0.8f);
        mDragDropManager.setDraggingItemScale(1.1f);
//        dragDropManager.setDraggingItemRotation(15.0f);
        dragAdapter = mDragDropManager.createWrappedAdapter(adapter);
        headerViewHolder.recyclerView.setAdapter(dragAdapter);
        headerViewHolder.recyclerView.setLayoutManager(new GridLayoutManager(headerViewHolder.recyclerView.getContext(), 4));
        GeneralItemAnimator itemAnimator = new DraggableItemAnimator();
        headerViewHolder.recyclerView.setItemAnimator(itemAnimator);

        mDragDropManager.attachRecyclerView(headerViewHolder.recyclerView);//关键步骤，设置好DragDropManager和RecyclerView后将二者绑定实现拖拽功能
    }

    @Override
    public void bindFooterViewHolder(MenuEditRecyclerListHolder footerViewHolder, EditItem footerItem) {

    }

    @Override
    public void onItemDragStarted(int position) {

    }

    @Override
    public void onItemDragPositionChanged(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDragFinished(int fromPosition, int toPosition, boolean result) {
        if (fromPosition != toPosition && result) {
            if (adapter != null) {
                TabMenuHelper.savePreferFrequentlyList(adapter.getRecyclerItems());
                hasDragChanged = true;
            }
        }
    }

    @Override
    public void onItemDragMoveDistanceUpdated(int offsetX, int offsetY) {

    }

    /**
     * 外部页面销毁之前调用
     */
    public void releaseDragManager() {
        if (mDragDropManager != null) {
            mDragDropManager.release();
            mDragDropManager = null;
        }
        if (dragAdapter != null) {
            WrapperAdapterUtils.releaseAll(dragAdapter);
            dragAdapter = null;
        }
    }

    /**
     * 是否发生过拖拽变化
     *
     * @return
     */
    public boolean isHasDragChanged() {
        return hasDragChanged;
    }

    /**
     * 通知刷新子列表数据
     */
    public void notifyChildDataChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void notifyChildDataAdded(MenuItem item) {
        if (adapter != null) {
            if (!adapter.getRecyclerItems().contains(item)) {
                adapter.getRecyclerItems().add(item);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void notifyChildDataRemoved(MenuItem item) {
        if (adapter != null) {
            adapter.getRecyclerItems().remove(item);
            adapter.notifyDataSetChanged();
        }
    }
}
