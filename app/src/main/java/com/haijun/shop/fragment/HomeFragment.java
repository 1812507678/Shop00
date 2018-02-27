package com.haijun.shop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.haijun.shop.R;
import com.haijun.shop.activity.GoodsDetailActivity;
import com.haijun.shop.activity.GoodsListActivity;
import com.haijun.shop.adapter.HorizontalListViewAdapter;
import com.haijun.shop.bean.Goods;
import com.haijun.shop.bean.ProductCategory;
import com.haijun.shop.util.CacheUtil;
import com.haijun.shop.util.LogUtil;
import com.haijun.shop.util.ToastUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{


    private static final String TAG = HomeFragment.class.getSimpleName();
    private View inflate;
    private ArrayList<Goods> dailyGoodsArrayList;
    private ArrayList<Goods> phoneGoodsArrayList;
    private ArrayList<Goods> computerGoodsArrayList;
    private ArrayList<Goods> cameraGoodsArrayList;
    private ArrayList<Goods> earphoneGoodsArrayList;
    private GridView mgv_home_daily;
    private GridView mgv_home_phone;
    private GridView mgv_home_computer;
    private GridView mgv_home_camera;
    private GridView mgv_home_earphone;
    private ImageView iv_home_top;
    private ProgressBar pb_progress;
    private SwipeRefreshLayout swipeRefreshLayout;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != inflate) {
            ViewGroup parent = (ViewGroup) inflate.getParent();
            if (null != parent) {
                parent.removeView(inflate);
            }
        } else {
            inflate = inflater.inflate(R.layout.fragment_home, container, false);
            LogUtil.i("HomeFragment","onCreateView");
            initView();// 控件初始化
            initData();
        }
        return inflate;
    }


    private void initView() {
        dailyGoodsArrayList = new ArrayList<>();
        phoneGoodsArrayList = new ArrayList<>();
        computerGoodsArrayList = new ArrayList<>();
        cameraGoodsArrayList = new ArrayList<>();
        earphoneGoodsArrayList = new ArrayList<>();

        iv_home_top = inflate.findViewById(R.id.iv_home_top);
        mgv_home_daily = inflate.findViewById(R.id.mgv_home_daily);
        mgv_home_phone = inflate.findViewById(R.id.mgv_home_phone);
        mgv_home_computer = inflate.findViewById(R.id.mgv_home_computer);
        mgv_home_camera = inflate.findViewById(R.id.mgv_home_camera);
        mgv_home_earphone = inflate.findViewById(R.id.mgv_home_earphone);

        //pb_progress = inflate.findViewById(R.id.pb_progress);

        RelativeLayout rl_home_daily = inflate.findViewById(R.id.rl_home_daily);
        RelativeLayout rl_home_phone = inflate.findViewById(R.id.rl_home_phone);
        RelativeLayout rl_home_compute = inflate.findViewById(R.id.rl_home_compute);
        RelativeLayout rl_home_camera = inflate.findViewById(R.id.rl_home_camera);
        RelativeLayout rl_home_earphone = inflate.findViewById(R.id.rl_home_earphone);

        rl_home_daily.setOnClickListener(this);
        rl_home_phone.setOnClickListener(this);
        rl_home_compute.setOnClickListener(this);
        rl_home_camera.setOnClickListener(this);
        rl_home_earphone.setOnClickListener(this);



        mgv_home_daily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getActivity().startActivity(new Intent(getActivity(), GoodsDetailActivity.class));
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.sr1);
        swipeRefreshLayout.setColorSchemeResources(R.color.app_main_color,R.color.app_main_color);
        swipeRefreshLayout.setOnRefreshListener(new MySwipeRefreshLayoutListener());
        swipeRefreshLayout.setRefreshing(true);





    }

    private void initData() {
        //首页商品信息先从缓存中取数据，然后从网络上取
        List<Goods> goodsFromCache = CacheUtil.getGoodsFromCache();
        if (goodsFromCache!=null && goodsFromCache.size()>0){
            classifyGoods(goodsFromCache);
        }

        BmobQuery<Goods> bmobQuery = new BmobQuery<>();
        //bmobQuery.addWhereEqualTo("isToHomePage",true);

        bmobQuery.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                swipeRefreshLayout.setRefreshing(false);
                if (e==null){
                    classifyGoods(list);
                    CacheUtil.putGoodsToCache(list);
                }
                else {
                    ToastUtil.showToask("数据加载失败，请检查网络:"+e);
                }
            }
        });


    }

    private void classifyGoods(List<Goods> list) {
        dailyGoodsArrayList.clear();
        phoneGoodsArrayList.clear();
        computerGoodsArrayList.clear();
        cameraGoodsArrayList.clear();
        earphoneGoodsArrayList.clear();

        for (Goods goods:list){
            switch (goods.getCategoryType()){
                case ProductCategory_Daily:
                    dailyGoodsArrayList.add(goods);
                    break;
                case ProductCategory_Phone:
                    phoneGoodsArrayList.add(goods);
                    break;
                case ProductCategory_Computer:
                    computerGoodsArrayList.add(goods);
                    break;
                case ProductCategory_Camera:
                    cameraGoodsArrayList.add(goods);
                    break;
                case ProductCategory_Earphone:
                    earphoneGoodsArrayList.add(goods);
                    break;
            }
        }

        mgv_home_daily.setAdapter(new HorizontalListViewAdapter(getActivity(), dailyGoodsArrayList));
        mgv_home_phone.setAdapter(new HorizontalListViewAdapter(getActivity(), phoneGoodsArrayList));
        mgv_home_computer.setAdapter(new HorizontalListViewAdapter(getActivity(), computerGoodsArrayList));
        mgv_home_camera.setAdapter(new HorizontalListViewAdapter(getActivity(), cameraGoodsArrayList));
        mgv_home_earphone.setAdapter(new HorizontalListViewAdapter(getActivity(), earphoneGoodsArrayList));

        mgv_home_daily.setOnItemClickListener(new MyOnItemClickListener(dailyGoodsArrayList));
        mgv_home_phone.setOnItemClickListener(new MyOnItemClickListener(phoneGoodsArrayList));
        mgv_home_computer.setOnItemClickListener(new MyOnItemClickListener(computerGoodsArrayList));
        mgv_home_camera.setOnItemClickListener(new MyOnItemClickListener(cameraGoodsArrayList));
        mgv_home_earphone.setOnItemClickListener(new MyOnItemClickListener(earphoneGoodsArrayList));

        final Goods goods = dailyGoodsArrayList.get(0);
        x.image().bind(iv_home_top, goods.getLogoUrl());
        iv_home_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("goods",goods);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), GoodsListActivity.class);
        switch (view.getId()){
            case R.id.rl_home_daily:
                intent.putExtra("productCategory", (Parcelable) ProductCategory.ProductCategoryType.ProductCategory_Daily);
                break;

            case R.id.rl_home_phone:
                intent.putExtra("productCategory", (Parcelable) ProductCategory.ProductCategoryType.ProductCategory_Phone);
                break;

            case R.id.rl_home_compute:
                intent.putExtra("productCategory", (Parcelable) ProductCategory.ProductCategoryType.ProductCategory_Computer);
                break;

            case R.id.rl_home_camera:
                intent.putExtra("productCategory", (Parcelable) ProductCategory.ProductCategoryType.ProductCategory_Camera);
                break;

            case R.id.rl_home_earphone:
                intent.putExtra("productCategory", (Parcelable) ProductCategory.ProductCategoryType.ProductCategory_Earphone);
                break;
        }
        startActivity(intent);
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        private List<Goods> goodsList;

        public MyOnItemClickListener(List<Goods> goodsList) {
            this.goodsList = goodsList;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Goods goods = goodsList.get(i);
            LogUtil.i(TAG,"goods:"+goods);
            Intent intent = new Intent(getActivity(),GoodsDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("goods",goods);
            intent.putExtra("bundle",bundle);
            startActivity(intent);
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
