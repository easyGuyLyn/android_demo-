package utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by 能 on 2016/3/30.
 */
public class ListViewUtils {

    //动态设置listview的高度
    public static void setListViewHeightBasedOnChildren(ListView listView, BaseAdapter adapter) {

        // 获取ListView对应的Adapter

        adapter = (BaseAdapter) listView.getAdapter();

        if (adapter == null) {

            return;

        }

        int totalHeight = 0;

        for (int i = 0, len = adapter.getCount(); i < len; i++) {

            // listAdapter.getCount()返回数据项的数目

            View listItem = adapter.getView(i, null, listView);

            // 计算子项View 的宽高

            listItem.measure(0, 0);

            int childHeight = listItem.getMeasuredHeight();

            // 统计所有子项的总高度

            totalHeight += childHeight;

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));

        // listView.getDividerHeight()获取子项间分隔符占用的高度

        // params.height最后得到整个ListView完整显示需要的高度

        listView.setLayoutParams(params);
    }


    /**
     * 计算listview高度
     * @param listView
     * @return
     */
    public static int measureListViewHeight(ListView listView) {

        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高，部分手机调用这个方法的原因是必须是LinearLayout布局开头。
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }
      return totalHeight;
  }

    /**
     * scrollview中加入listview不造成异常
     * @param listView
     */
    public  static void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高，部分手机调用这个方法的原因是必须是LinearLayout布局开头。
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}
