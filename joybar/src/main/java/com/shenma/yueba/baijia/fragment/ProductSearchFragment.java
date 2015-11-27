package com.shenma.yueba.baijia.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.ProductsInfoBean;
import com.shenma.yueba.baijia.modle.newmodel.SearchProductBackBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.PerferneceUtil;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.yangjia.adapter.MyAttentionAndFansForSocialAdapter;
import com.shenma.yueba.yangjia.adapter.ProductSearchAdapter;
import com.shenma.yueba.yangjia.modle.AttationAndFansItemBean;
import com.shenma.yueba.yangjia.modle.AttationAndFansListBackBean;

import java.util.ArrayList;
import java.util.List;

import config.PerferneceConfig;

/**
 * 社交管理中的-----我的关注 and 我的粉丝
 * 
 * @author a
 * 
 */
public class ProductSearchFragment extends BaseFragment {

	private PullToRefreshListView pull_refresh_list;
	private ProductSearchAdapter adapter;
	private List<ProductsInfoBean> mList = new ArrayList<ProductsInfoBean>();
	private int page = 1;
	private boolean isRefresh = true;
	public TextView tv_nodata;
	private String key,storeId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	public ProductSearchFragment(String key,String storeId){
		this.key = key;
		this.storeId = storeId;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.refresh_listview_without_title_layout, null);
		pull_refresh_list = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		tv_nodata = (TextView) view
				.findViewById(R.id.tv_nodata);
		adapter = new ProductSearchAdapter(getActivity(),
				mList);
		pull_refresh_list.setMode(PullToRefreshBase.Mode.BOTH);
		pull_refresh_list.setAdapter(adapter);
		pull_refresh_list.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page = 1;
				isRefresh = true;
				getProductList(getActivity(),false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				page++;
				isRefresh = false;
				getProductList(getActivity(),false);
			}
		});
		return view;
	}




	/**
	 * 获取关注列表和fans列表
	 */
	public void getProductList( Context ctx, boolean showDialog){
		if(showDialog && mList!=null && mList.size()>0){
			return;
		}
		HttpControl httpControl = new HttpControl();
		String cityId = SharedUtil.getStringPerfernece(getActivity(), SharedUtil.getStringPerfernece(getActivity(), PerferneceConfig.SELECTED_CITY_ID));
		httpControl.searchProducts(key, SharedUtil.getUserId(ctx), cityId, storeId, "0", showDialog,page, new HttpCallBackInterface() {
			@Override
			public void http_Success(Object obj) {
				pull_refresh_list.postDelayed(new Runnable() {
					@Override
					public void run() {
						pull_refresh_list.onRefreshComplete();
					}
				}, 100);
				SearchProductBackBean bean = (SearchProductBackBean) obj;
				if (isRefresh) {
					if (bean != null && bean.getData() != null && bean.getData().getItems() != null && bean.getData().getItems().size() > 0) {
						mList.clear();
						mList.addAll(bean.getData().getItems());
						tv_nodata.setVisibility(View.GONE);
						adapter = new ProductSearchAdapter(getActivity(), mList);
						pull_refresh_list.setAdapter(adapter);
					} else {
						tv_nodata.setVisibility(View.VISIBLE);
					}
				} else {
					if (bean != null && bean.getData() != null && bean.getData().getItems() != null && bean.getData().getItems().size() > 0) {
						mList.addAll(bean.getData().getItems());
						adapter.notifyDataSetChanged();
					} else {
						Toast.makeText(getActivity(), "没有更多数据了...", Toast.LENGTH_SHORT).show();
					}
				}
			}

			@Override
			public void http_Fails(int error, String msg) {
				Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
			}
		}, ctx);
	}

}