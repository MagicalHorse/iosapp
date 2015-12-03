package com.shenma.yueba.baijia.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.BaseRequest;
import com.shenma.yueba.baijia.modle.ProductsInfoBean;
import com.shenma.yueba.baijia.modle.newmodel.BuyerInfo;
import com.shenma.yueba.util.CreateAutoSizeViewManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

import java.util.List;

/**
 * Created by Administrator on 2015/11/12.
 * 认证买手 列表 适配器
 */
public class BuyerSearchAdapter extends BaseAdapterWithUtil {
    Context ctx;
    private List<BuyerInfo> mList;

    public BuyerSearchAdapter(Context ctx, List<BuyerInfo> mList) {
        super(ctx);
        this.ctx = ctx;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {

            holder = new Holder();
            convertView = View.inflate(ctx,R.layout.buyer_for_search_item,null);
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            holder.ll_touch = (LinearLayout)convertView.findViewById(R.id.ll_touch);
            holder.tv_touch = (TextView)convertView.findViewById(R.id.tv_touch);
            holder.tv_attention = (TextView)convertView.findViewById(R.id.tv_attention);
            holder.tv_store_name = (TextView)convertView.findViewById(R.id.tv_store_name);
            holder.tv_address = (TextView)convertView.findViewById(R.id.tv_address);
            holder.riv_head = (RoundImageView)convertView.findViewById(R.id.riv_head);
            holder.tv_touch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    touch(mList.get(position).getBuyerId());
                }
            });
            holder.authentication_item_productlist_linearlayout = (LinearLayout) convertView.findViewById(R.id.authentication_item_productlist_linearlayout);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.cvm = new CreateAutoSizeViewManager(((Activity) ctx), ctx.getResources().getDimensionPixelSize(R.dimen.item_margin), R.layout.authentication_chid_item_layout, 3, holder.authentication_item_productlist_linearlayout, new CreateAutoSizeViewManager.InflaterSucessListener() {
            @Override
            public void returnChildListView(List<View> view_array) {
                List<ProductsInfoBean> products =   mList.get(position).getProducts();
                for (int i=0;i<products.size();i++){
                    String pic  = products.get(i).getPic();
                    bitmapUtils.display((view_array.get(i)),pic);
                }
            }
        });
        if(mList.get(position).getProducts()==null || mList.get(position).getProducts().size() == 0){
            holder.ll_touch.setVisibility(View.VISIBLE);
            holder.authentication_item_productlist_linearlayout.setVisibility(View.GONE);
        }else{
            holder.ll_touch.setVisibility(View.GONE);
            holder.authentication_item_productlist_linearlayout.setVisibility(View.VISIBLE);
        }
        bitmapUtils.display(holder.riv_head, mList.get(position).getLogo());
        holder.tv_name.setText(ToolsUtil.nullToString(mList.get(position).getNickname()));
        holder.tv_attention.setText(ToolsUtil.nullToString(mList.get(position).isFllowed() ? "已关注" : "关注"));
        holder.tv_store_name.setText(ToolsUtil.nullToString(mList.get(position).getStoreName()));
        holder.tv_address.setText(ToolsUtil.nullToString(mList.get(position).getStoreLocal()));
        ;

        return convertView;
    }

    class Holder {
        CreateAutoSizeViewManager cvm;
        RoundImageView riv_head;
        TextView tv_name;
        TextView tv_attention;
        TextView tv_store_name;
        TextView tv_address;
        LinearLayout ll_touch;
        TextView tv_touch;
        LinearLayout authentication_item_productlist_linearlayout;
    }

    /**
     * 戳一下
     */
    public void touch(String buyerId) {
        HttpControl httpControl = new HttpControl();
        httpControl.touch(buyerId, new HttpControl.HttpCallBackInterface() {
            @Override
            public void http_Success(Object obj) {
                BaseRequest bean = (BaseRequest) obj;
                if (bean.isSuccessful()) {
                    Toast.makeText(ctx, "提醒成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, "提醒失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void http_Fails(int error, String msg) {
                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
            }
        }, ctx);
    }

}
