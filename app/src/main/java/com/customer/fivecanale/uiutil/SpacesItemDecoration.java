package com.customer.fivecanale.uiutil;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

/*
 * Purpose – This class is using as RecyclerView Space between items
 * @author 3Embed
 * Created on Nov 25, 2019
 * Modified on
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
  private static final int HORIZONTAL = 0;
  public static final int VERTICAL = 1;
  private static final int GRID = 2;
  private final int spacing;
  private int displayMode;

  public SpacesItemDecoration(int spacing) {
    this(spacing, -1);
  }

  public SpacesItemDecoration(int spacing, Context context) {
    this(spacing, -1);
  }

  private SpacesItemDecoration(int spacing, int displayMode) {
    this.spacing = spacing;
    this.displayMode = displayMode;
  }

  @Override
  public void getItemOffsets(
      @NotNull Rect outRect, @NotNull View view, RecyclerView parent, RecyclerView.State state) {
    int position = parent.getChildViewHolder(view).getAdapterPosition();
    int itemCount = state.getItemCount();
    RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
    setSpacingForDirection(outRect, layoutManager, position, itemCount);
  }

  private void setSpacingForDirection(
      Rect outRect, RecyclerView.LayoutManager layoutManager, int position, int itemCount) {

    // Resolve display mode automatically
    if (displayMode == -1) {
      displayMode = resolveDisplayMode(layoutManager);
    }

    switch (displayMode) {
      case HORIZONTAL:
        outRect.left = spacing;
        outRect.right = position == itemCount - 1 ? spacing : 0;
        outRect.top = spacing;
        outRect.bottom = spacing;
        break;
      case VERTICAL:
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.top = spacing;
        outRect.bottom = position == itemCount - 1 ? spacing : 0;
        break;
      case GRID:
        if (layoutManager instanceof GridLayoutManager) {
          GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
          int cols = gridLayoutManager.getSpanCount();
          int rows = itemCount / cols;
          outRect.left = spacing;
          outRect.right = position % cols == cols - 1 ? spacing : 0;
          outRect.top = spacing;
          outRect.bottom = position / cols == rows - 1 ? spacing : 0;
        }
        break;
    }
  }

  private int resolveDisplayMode(RecyclerView.LayoutManager layoutManager) {
    if (layoutManager instanceof GridLayoutManager) {
      return GRID;
    }
    if (layoutManager.canScrollHorizontally()) {
      return HORIZONTAL;
    }
    return VERTICAL;
  }
}