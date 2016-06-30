package com.example.administrator.myapplication.TestXUtils.fragment;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.TestXUtils.adapter.MyAdapter;
import com.example.administrator.myapplication.TestXUtils.bean.Person;

@ContentView(R.layout.fragment_first)
public class FirstFragment extends Fragment{
	private MyAdapter adapter;
	private List<Person> list=new ArrayList<>();
	private List<String> listUrl=new ArrayList<>();
	private List<String> listName=new ArrayList<>();

	@ViewInject(R.id.btn_test)
	Button btn_test;
	@ViewInject(R.id.listView)
	ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return  x.view().inject(this, inflater, container);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		btn_test.setText("测试");
		//添加测试头像地址
		listUrl.add("http://down.tutu001.com/d/file/20101129/2f5ca0f1c9b6d02ea87df74fcc_560.jpg");
		listUrl.add("http://pic24.nipic.com/20121022/9252150_193011306000_2.jpg");
		listUrl.add("http://pic1.nipic.com/2008-08-12/200881211331729_2.jpg");
		listUrl.add("http://pic28.nipic.com/20130402/9252150_190139450381_2.jpg");
		listUrl.add("http://pic9.nipic.com/20100812/3289547_144304019987_2.jpg");
		listUrl.add("http://imgsrc.baidu.com/forum/pic/item/3ac79f3df8dcd1004e9102b8728b4710b9122f1e.jpg");
		listUrl.add("http://www.th7.cn/Article/UploadFiles/200801/2008012120273536.jpg");
		listUrl.add("http://img.sucai.redocn.com/attachments/images/201012/20101213/20101211_0e830c2124ac3d92718fXrUdsYf49nDl.jpg");
		listUrl.add("http://a2.att.hudong.com/38/59/300001054794129041591416974.jpg");
		listUrl.add("http://pic13.nipic.com/20110415/1347158_132411659346_2.jpg");
		listUrl.add("http://down.tutu001.com/d/file/20101129/2f5ca0f1c9b6d02ea87df74fcc_560.jpg");
		listUrl.add("http://pic24.nipic.com/20121022/9252150_193011306000_2.jpg");
		listUrl.add("http://pic1.nipic.com/2008-08-12/200881211331729_2.jpg");
		listUrl.add("http://pic28.nipic.com/20130402/9252150_190139450381_2.jpg");
		listUrl.add("http://pic9.nipic.com/20100812/3289547_144304019987_2.jpg");
		listUrl.add("http://imgsrc.baidu.com/forum/pic/item/3ac79f3df8dcd1004e9102b8728b4710b9122f1e.jpg");
		listUrl.add("http://www.th7.cn/Article/UploadFiles/200801/2008012120273536.jpg");
		listUrl.add("http://img.sucai.redocn.com/attachments/images/201012/20101213/20101211_0e830c2124ac3d92718fXrUdsYf49nDl.jpg");
		listUrl.add("http://a2.att.hudong.com/38/59/300001054794129041591416974.jpg");
		listUrl.add("http://pic13.nipic.com/20110415/1347158_132411659346_2.jpg");
		//添加测试名字
		listName.add("武松");
		listName.add("吴用");
		listName.add("林冲");
		listName.add("李逵");
		listName.add("华荣");
		listName.add("宋江");
		listName.add("卢俊义");
		listName.add("鲁智深");
		listName.add("杨志");
		listName.add("柴进");
		listName.add("武松");
		listName.add("吴用");
		listName.add("林冲");
		listName.add("李逵");
		listName.add("华荣");
		listName.add("宋江");
		listName.add("卢俊义");
		listName.add("鲁智深");
		listName.add("杨志");
		listName.add("柴进");
		for(int i=0;i<19;i++){
			Person p=new Person();
			p.setImgUrl(listUrl.get(i));
			p.setName(listName.get(i));
			list.add(p);
		}
		adapter=new MyAdapter(getActivity(), list);
		listView.setAdapter(adapter);
	}

	@Event(value={R.id.btn_test})
	private void test(View view){
		Toast.makeText(getActivity(), btn_test.getText().toString().trim(), 0).show();
	}
}
