/*
Copyright 2015 shizhefei（LuckyJayce）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.example.administrator.myapplication.mvcHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvcHelper.mvc.IDataAdapter;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends BaseAdapter implements IDataAdapter<List<Book>> {
	private List<Book> books = new ArrayList<Book>();
	private LayoutInflater inflater;

	public BooksAdapter(Context context) {
		super();
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return books.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_book, parent, false);
		}
		TextView textView = (TextView) convertView;
		textView.setText(books.get(position).getName());
		return convertView;
	}

	@Override
	public void notifyDataChanged(List<Book> data, boolean isRefresh) {
		if (isRefresh) {
			books.clear();
		}
		books.addAll(data);
		notifyDataSetChanged();
	}

	@Override
	public List<Book> getData() {
		return books;
	}

}
