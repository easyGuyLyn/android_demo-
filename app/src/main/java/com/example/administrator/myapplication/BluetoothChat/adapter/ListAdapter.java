package com.example.administrator.myapplication.BluetoothChat.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;


/**
 * 适配器框架类
 * 框架提供了接口供外部为Item视图内的视图组件绑定单击事件监听器
 *
 * @param <T> 适配器中加载的数据源的数据类型
 * @author tarena.sunwei
 */
public abstract class ListAdapter<T> extends BaseAdapter {
    /**
     * 上下文，供继承者使用
     */
    private Context context;
    /**
     * 适配器中加载的数据源
     */
    private List<T> datasource;
    /**
     * 适配器中用来“膨胀”Item视图的LayoutInflater
     */
    private LayoutInflater inflater;
    /**
     * 呈现提示信息
     */
    private Toast toast;
    /**
     * 用来装载为该Item中的视图绑定的监听器
     * 采用SparseArray数据结构，该数据结构是安卓为key为Integer类型的Map数据结构做的优化升级
     * 在listeners中，key为内部视图的id值，value值为该视图的监听器，监听器类型为OnViewItemClickListener
     */
    private SparseArray<OnViewItemClickListener<T>> listeners;

    public ListAdapter(Context context, List<T> datasource) {
        super();
        this.context = context;
        this.datasource = datasource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datasource.size();
    }

    @Override
    public T getItem(int position) {
        return datasource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 方法中，调用抽象方法getItemView来进行Item视图的构建
     * 调用bindListeners方法，进行Item内部视图单击事件监听器的绑定
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = getItemView(position, convertView, parent);
        bindListeners(itemView, position);
        return itemView;
    }

    /**
     * 如果listeners属性不为空，说明必然在外部调用过setOnViewItemClickListener方法
     * 为Item中的某个或某几个内部视图指定了监听器
     * 因此要利用该方法完成指定监听器的绑定
     *
     * @param itemView 一个Item的视图，相当于getView方法中convertView
     * @param position Item的位置，相当于getView方法中的position
     */
    private void bindListeners(final View itemView, final int position) {
        if (listeners != null) {
            final T t = getItem(position);
            for (int i = 0; i < listeners.size(); i++) {
                Integer key = listeners.keyAt(i);
                final View inView = itemView.findViewById(key);
                final OnViewItemClickListener<T> inviewListener = listeners.get(key);
                if (inView != null && inviewListener != null) {
                    inView.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            inviewListener.onClick(itemView, inView, position, t);
                        }
                    });
                }
            }
        }

    }

    /**
     * 抽象方法，由继承者实现，该方法的作用相同于标准的BaseAdapter中的getView方法
     * 获得Item的视图（即convertView）
     *
     * @param position    与getView方法中的position相同
     * @param convertView 与getView方法中的convertView相同
     * @param parent      与getView方法中的parent相同
     * @return Item的视图，相当于getView方法的返回值convertView
     */
    public abstract View getItemView(int position, View convertView, ViewGroup parent);

    /**
     * 工具方法，获得适配器中的数据源，供外部或继承者的操作便利性
     */
    public List<T> getDatasource() {
        return datasource;
    }

    /**
     * 工具方法，设定适配器的数据源，供外部或继承者的操作便利性
     */
    public void setDatasource(List<T> datasource) {
        this.datasource = datasource;
        notifyDataSetChanged();
    }

    /**
     * 工具方法，获得LayoutInflater对象，供继承者实现getItemView方法时使用
     */
    public LayoutInflater getInflater() {
        return inflater;
    }

    /**
     * 工具方法，获得LayoutInflater对象，供继承者实现getItemView方法时使用
     */
    public Context getContext() {
        return context;
    }

    /**
     * 工具方法，向适配器中的数据源添加一个元素
     */
    public void add(T t) {
        datasource.add(t);
        notifyDataSetChanged();
    }

    /**
     * 工具方法，向适配器中的数据源添加一组数据
     */
    public void addAll(List<T> list) {
        datasource.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 工具方法，移除适配器中数据源指定位置的元素
     */
    public void remove(int position) {
        datasource.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 工具方法，如果适配器中数据源包含参数中指定的元素则移除掉
     */
    public void remove(T t) {
        if (datasource.contains(t)) {
            datasource.remove(t);
            notifyDataSetChanged();
        }
    }

    /**
     * 工具方法，Toast指定的参数信息
     */
    public void toast(final String text) {
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (toast == null) {
                        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                    } else {
                        toast.setText(text);
                    }
                    toast.show();
                }
            });
        }
    }

    /**
     * 工具方法，在Logcat中输出指定的参数信息
     */
    public void log(String text) {
        Log.d("myBlu", text);
    }

    /**
     * 为一个Item视图（即convertView）中的子视图绑定监听器。
     * 这里必须要对标准的View.OnClickListener做扩展
     * 因为标准的View.OnClickListener只能接收一个View类型的参数，而对于Item内部的一个子视图来说，点击时执行的动作
     * 往往需要配合操作该Item中用来显示的那个数据
     * 所以，作为一个BaseAdapter框架类对外公开的接口，该接口不是在ListAdapter内部实现
     * 而是由ListAdapter的继承者实现，但是数据是封装在父类中的，所以在子类实现时，父类有义务将Item中相关的内容（例如数据，位置等信息）传入接口中的方法
     * 方便继承者进行业务逻辑的实现
     *
     * @param id       convertView中的子视图resId
     * @param listener 子视图的单击事件监听器
     */
    public void setOnViewItemClickListener(Integer id, OnViewItemClickListener<T> listener) {
        if (listeners == null) {
            listeners = new SparseArray<OnViewItemClickListener<T>>();
        }
        listeners.append(id, listener);
    }

    /**
     * 内部接口
     * 如果要为Item内部的子视图绑定单击事件监听器的时候，需要使用该接口
     * 该接口在功能相当于一个View.OnClickListener
     * 但是因为该接口用于Item内部的子视图，所以为了方便子视图功能的扩展
     * 特别设计了该接口，接口提供了更丰富的参数，更适合Item内部子视图与Item中呈现数据的结合
     */
    public interface OnViewItemClickListener<T> {
        void onClick(View parentV, View v, Integer position, T t);
    }
}
