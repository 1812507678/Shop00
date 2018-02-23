package com.haijun.shop.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.haijun.shop.R;
import com.haijun.shop.activity.GoodsDetailActivity;
import com.haijun.shop.activity.WithDrawApplyActivity;
import com.haijun.shop.adapter.ShopcartListAdapter;
import com.haijun.shop.bean.Goods;
import com.haijun.shop.bean.ShopCart;
import com.haijun.shop.bean.User;
import com.haijun.shop.util.Constant;
import com.haijun.shop.util.LogUtil;
import com.haijun.shop.util.SPUtil;
import com.haijun.shop.util.ShopCartUtil;
import com.haijun.shop.util.ShowToaskDialogUtil;
import com.haijun.shop.util.ToastUtil;
import com.haijun.shop.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCartFragment extends Fragment implements View.OnClickListener{


    private static final String TAG = ShopCartFragment.class.getSimpleName();
    private View inflate;
    private ListView lv_shopcart_list;
    private TextView tv_shopcart_money;

    private float allMoney;
    private List<Goods> mGoodsArrayList;
    private ShopcartListAdapter mShopcartListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ShopCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null != inflate) {
            ViewGroup parent = (ViewGroup) inflate.getParent();
            if (null != parent) {
                parent.removeView(inflate);
            }
        } else {
            inflate = inflater.inflate(R.layout.fragment_shopcart, container, false);
            LogUtil.i("ShopCartFragment","onCreateView");
            initView();// 控件初始化
            initData();
        }
        return inflate;
    }

    private void initView() {
        lv_shopcart_list = inflate.findViewById(R.id.lv_shopcart_list);
        tv_shopcart_money = inflate.findViewById(R.id.tv_shopcart_money);
        TextView tv_shopcart_pay = inflate.findViewById(R.id.tv_shopcart_pay);


        tv_shopcart_pay.setOnClickListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.sr1);
        swipeRefreshLayout.setColorSchemeResources(R.color.my_information_option_pressed,R.color.my_information_option_pressed);
        swipeRefreshLayout.setOnRefreshListener(new MySwipeRefreshLayoutListener());
        swipeRefreshLayout.setRefreshing(true);


    }

    private void initData() {
        mGoodsArrayList = new ArrayList<>();

        mShopcartListAdapter = new ShopcartListAdapter(getContext(), mGoodsArrayList);
        lv_shopcart_list.setAdapter(mShopcartListAdapter);

        lv_shopcart_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                Goods goods = mGoodsArrayList.get(i);
                LogUtil.i(TAG,"goods:"+goods);
                Bundle bundle = new Bundle();
                bundle.putParcelable("goods",goods);
                intent.putExtra("bundle",bundle);
                getActivity().startActivity(intent);
            }
        });

        lv_shopcart_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                ShowToaskDialogUtil.showTipDialog(getContext(), "确定要移除购物车吗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        removeGoodsFromShopCart(i);
                    }
                });
                return true;
            }
        });

        mShopcartListAdapter.setOnListViewRadioButtonSelected(new ShopcartListAdapter.OnListViewRadioCheckedChangeListener() {
           @Override
           public void onChecked(float price, boolean checked) {
               if (checked){
                   allMoney += price;
               }
               else {
                   allMoney -= price;
               }

               tv_shopcart_money.setText("总计："+(int)allMoney);
           }
       });

        User userInfo = UserUtil.getUserInfo();
        if (userInfo!=null){
            String userId = userInfo.getObjectId();

            String bql ="select * from Goods where objectId in (select goodsId from ShopCart where userId = '"+userId+"')";

            new BmobQuery<Goods>().doSQLQuery(bql,new SQLQueryListener<Goods>(){
                @Override
                public void done(BmobQueryResult<Goods> result, BmobException e) {
                    swipeRefreshLayout.setRefreshing(false);
                    if(e ==null){
                        LogUtil.i(TAG, "result:"+result.getResults());
                        if (result.getResults()!=null && result.getResults().size()>0){
                            LogUtil.i(TAG, "result:"+result.getResults().size());
                            mGoodsArrayList.clear();
                            mGoodsArrayList.addAll(result.getResults());
                            mShopcartListAdapter.notifyDataSetChanged();
                            ShopCartUtil.getInstance().setGoodsShopCartList(result.getResults());
                        }
                    }else{
                        ToastUtil.showToask("数据加载失败，请检查网络:"+e);
                        LogUtil.i(TAG, "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                    }
                }
            });
        }
    }

    private void removeGoodsFromShopCart(final int i) {
        if (i<=mGoodsArrayList.size()){
            Goods goods = mGoodsArrayList.get(i);

            BmobQuery<ShopCart> bmobQuery = new BmobQuery();
            bmobQuery.addWhereEqualTo("goodsId",goods.getObjectId());
            bmobQuery.addWhereEqualTo("userId",UserUtil.getUserInfo().getObjectId());

            bmobQuery.findObjects(new FindListener<ShopCart>() {
                @Override
                public void done(List<ShopCart> list, BmobException e) {
                    if (e==null){
                        if (list!=null && list.size()==1){
                            ShopCart shopCart = list.get(0);
                            shopCart.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e ==null){
                                        mGoodsArrayList.remove(i);
                                        ToastUtil.showToask("移除成功");
                                        mShopcartListAdapter.notifyDataSetChanged();
                                    }else{
                                        ToastUtil.showToask("移除失败，请检查网络:"+e);
                                        LogUtil.i(TAG, "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                    else{
                        ToastUtil.showToask("移除失败，请检查网络:"+e);
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_shopcart_pay:
                //ToastUtil.showToask("去付款");
                boolean booleanValueFromSP = SPUtil.getBooleanValueFromSP(Constant.isApplyWithDraw);
                if (booleanValueFromSP){
                    ToastUtil.showToask("您已经申请额度，正在审核，审核通过后就可以购物了");
                }
                else {
                    ShowToaskDialogUtil.showTipDialog(getContext(), "您还没有申请额度，是否现在申请？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getActivity(),WithDrawApplyActivity.class));
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(TAG,"onResume");
        List<Goods> goodsShopCartList = ShopCartUtil.getInstance().getGoodsShopCartList();
        if (goodsShopCartList!=null && goodsShopCartList.size()>mGoodsArrayList.size()){
            mGoodsArrayList.clear();
            mGoodsArrayList.addAll(goodsShopCartList);
            mShopcartListAdapter.notifyDataSetChanged();
        }
    }

    private class MySwipeRefreshLayoutListener implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            Log.i(TAG,"onRefresh");
            initData();
        }
    }
}
