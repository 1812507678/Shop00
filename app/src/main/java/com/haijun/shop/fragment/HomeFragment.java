package com.haijun.shop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.haijun.shop.R;
import com.haijun.shop.activity.GoodsDetailActivity;
import com.haijun.shop.activity.GoodsListActivity;
import com.haijun.shop.adapter.HorizontalListViewAdapter;
import com.haijun.shop.bean.Goods;
import com.haijun.shop.bean.ProductCategory;
import com.haijun.shop.util.LogUtil;
import com.haijun.shop.util.ToastUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

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






        /*goods.setCategoryType(ProductCategory.ProductCategoryType.ProductCategory_Camera);
        goods.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                LogUtil.i(TAG,"goods: s:"+s+"e:"+e);
            }
        });

        ShopCart shopCart = new ShopCart("11","hh","ww","ss",11);
        shopCart.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                LogUtil.i(TAG,"shopCart s:"+s+"e:"+e);
            }
        });

        List<Goods> goodsList = new ArrayList<>();
        goodsList.add(goods);
        goodsList.add(goods);
        ProductCategory productCategory = new ProductCategory("hhh",ProductCategory.ProductCategoryType.ProductCategory_Daily,goodsList);
        productCategory.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                LogUtil.i(TAG,"productCategory s:"+s+"e:"+e);
            }
        });*/

        /*
        List<String> photoImageUrlList = new ArrayList<>();
        photoImageUrlList.add("https://imgs1.fenqile.com/poporder2/M00/00/60/KaUHAFoJEEyARYldAALsXukKWb4823_680x680.jpg?e=1517785785&token=a7e1ce3b9d78dcce3130ae35dfd7f945d7df54fa:934e4458e8578b15e4e9b499b11f6d81267441ae");
        photoImageUrlList.add("https://cimgs1.fenqile.com/product/M00/C9/59/hhoGAFoBdSmAN07xAALsXukKWb4197_800x800.jpg");
        photoImageUrlList.add("https://cimgs1.fenqile.com/product/M00/C9/59/hhoGAFoBdSmAHEtjAAR92vpDJQ4710_800x800.jpg");
        photoImageUrlList.add("https://cimgs1.fenqile.com/product/M00/DF/D6/hRoGAFpMSoWAMuxvAASkv42g4c4473_800x800.jpg");
        photoImageUrlList.add("https://cimgs1.fenqile.com/product/M00/D5/4D/hhoGAFpXEBOAPUETAAUrFGTRAr4271_800x800.jpg");

        List<String> introduceImageUrlList = new ArrayList<>();
        introduceImageUrlList.add("https://cimg1.fenqile.com/product/M00/D5/4D/hhoGAFpXEGyAPKIoAAO2vyrDfsg993.jpg");
        introduceImageUrlList.add("https://cimg1.fenqile.com/product/M00/E1/DE/hRoGAFpXEHaAOuypAAU6Edgh_Yg691.jpg");
        introduceImageUrlList.add("https://cimg1.fenqile.com/product/M00/C9/59/hhoGAFoBdcKAbAIpAAOYpq9iM-Y357.jpg");
        introduceImageUrlList.add("https://cimg1.fenqile.com/product/M00/D5/A0/hRoGAFoBdcOABIrOAAKN161TsvU982.jpg");
        introduceImageUrlList.add("https://cimg1.fenqile.com/product/M00/C9/59/hhoGAFoBdcOAJXsGAAHWZaxJpyk613.jpg");
        introduceImageUrlList.add("https://cimg1.fenqile.com/product/M00/C9/59/hhoGAFoBdcSAS3dSAAKr6Kr9rsA392.jpg");
        introduceImageUrlList.add("https://cimg1.fenqile.com/product/M00/D5/A0/hRoGAFoBdcSAbNOdAAGfqaHJzpg819.jpg");
        introduceImageUrlList.add("https://cimg1.fenqile.com/product/M00/D5/A0/hRoGAFoBdcWAUsbfAAGY4po00Tw501.jpg");

        Goods goods = new Goods("College Home 男士毛衣圆领加绒加厚冬季韩版潮流个性针织衫线衣",
                "https://imgs1.fenqile.com/poporder2/M00/00/60/KaUHAFoJEEyARYldAALsXukKWb4823_680x680.jpg?e=1517785785&token=a7e1ce3b9d78dcce3130ae35dfd7f945d7df54fa:934e4458e8578b15e4e9b499b11f6d81267441ae",
                "JRY5235黑色 L",
                119,
                137,
                photoImageUrlList,introduceImageUrlList);
        goods.setCategoryType(ProductCategory.ProductCategoryType.ProductCategory_Daily);


        List<String> photoImageUrlList1 = new ArrayList<>();
        photoImageUrlList1.add("https://cimgs1.fenqile.com/product/M00/C6/29/hRoGAFlXuNOAXOsDAAJYxIx0hQY879_680x680.jpg");
        photoImageUrlList1.add("https://cimgs1.fenqile.com/product/M00/C6/28/hRoGAFlXuLCAF6o3AAJYxIx0hQY372_800x800.jpg");
        photoImageUrlList1.add("https://cimgs1.fenqile.com/product/M00/C6/28/hRoGAFlXuLCAF6o3AAJYxIx0hQY372_800x800.jpg");
        photoImageUrlList1.add("https://cimgs1.fenqile.com/product/M00/C6/28/hRoGAFlXuLCAF6o3AAJYxIx0hQY372_800x800.jpg");
        photoImageUrlList1.add("https://cimgs1.fenqile.com/product/M00/BA/9E/hhoGAFlXuLCAKtC5AAJuFMxEitE517_800x800.jpg");

        List<String> introduceImageUrlList1 = new ArrayList<>();
        introduceImageUrlList1.add("https://cimg1.fenqile.com/product/M00/BB/38/hBoGAFlXuOCAQOBJAAJYxIx0hQY578.jpg");
        introduceImageUrlList1.add("https://cimg1.fenqile.com/product/M00/BB/38/hBoGAFlXuOCAUXk3AAEsCHKOsFg431.jpg");
        introduceImageUrlList1.add("https://cimg1.fenqile.com/product/M00/BA/9E/hhoGAFlXuOCARq4kAAIkBbcue_g896.jpg");
        introduceImageUrlList1.add("https://cimg1.fenqile.com/product/M00/C6/29/hRoGAFlXuOGAKm5kAAJuFMxEitE965.jpg");
        introduceImageUrlList1.add("https://cimg1.fenqile.com/product/M00/C6/29/hRoGAFlXuOGAEAkCAAI1c6Sxf4c945.jpg");
        introduceImageUrlList1.add("https://cimg1.fenqile.com/product/M00/C6/29/hRoGAFlXuOCAHQGWAAC_wAJ-I_Y030.jpg");

        Goods goods1 = new Goods("COACH 蔻驰 女款黑色牛皮双肩包 38263 IMBLK",
                "https://cimgs1.fenqile.com/product/M00/C6/29/hRoGAFlXuNOAXOsDAAJYxIx0hQY879_680x680.jpg",
                "黑色",
                1519,
                1756,
                photoImageUrlList1,introduceImageUrlList1);
        goods1.setCategoryType(ProductCategory.ProductCategoryType.ProductCategory_Daily);


        List<String> photoImageUrlList2 = new ArrayList<>();
        photoImageUrlList2.add("https://cimgs1.fenqile.com/ibanner2/M00/00/91/jagHAFphlb-AE-anAAGOtLTpQJU518_1360x1360.jpg");
        photoImageUrlList2.add("https://cimgs1.fenqile.com/ibanner2/M00/00/91/jagHAFphl9mAS29yAAAjKylLDZE976_400x300.jpg");
        photoImageUrlList2.add("https://cimgs1.fenqile.com/ibanner2/M00/00/91/jagHAFphl4mAVM9GAAFj5rAnen0937_638x638.jpg");

        List<String> introduceImageUrlList2 = new ArrayList<>();
        introduceImageUrlList2.add("https://cimg1.fenqile.com/product/M00/D8/39/hRoGAFoZZJ-AEfaLAANlUtBizxk033.jpg");
        introduceImageUrlList2.add("https://cimg1.fenqile.com/product/M00/CB/E6/hhoGAFoZZKCAfoM4AAN7Z3EoCCg782.jpg");
        introduceImageUrlList2.add("https://cimg1.fenqile.com/product/M00/D8/39/hRoGAFoZZKCARmjuAAOOnpoPqGY995.jpg");
        introduceImageUrlList2.add("https://cimg1.fenqile.com/product/M00/CB/E6/hhoGAFoZZKCAU5I0AAOM9MoQ-Ng893.jpg");
        introduceImageUrlList2.add("https://cimg1.fenqile.com/product/M00/D8/39/hRoGAFoZZKGAdSr-AAL-c2so_FY342.jpg");
        introduceImageUrlList2.add("https://cimg1.fenqile.com/product/M00/CB/E6/hhoGAFoZZKGALrDfAAK6Ckkiyg0517.jpg");
        introduceImageUrlList2.add("https://cimg1.fenqile.com/ibanner/M00/01/5C/wycJAFlThoyAX3hNAAG5meGDE-A925.jpg");

        Goods goods2 = new Goods("御泥坊美白嫩肤3件套装",
                "https://cimgs1.fenqile.com/ibanner2/M00/00/91/jagHAFphlb-AE-anAAGOtLTpQJU518_1360x1360.jpg",
                " ",
                99,
                129,
                photoImageUrlList2,introduceImageUrlList2);
        goods2.setCategoryType(ProductCategory.ProductCategoryType.ProductCategory_Daily);


        List<String> photoImageUrlList3 = new ArrayList<>();
        photoImageUrlList3.add("https://cimgs1.fenqile.com/product/M00/31/6E/hBoGAFiEkiWAbmxCAAAbBmbZiXs313_680x680.jpg");
        photoImageUrlList3.add("https://cimgs1.fenqile.com/product/M00/31/6E/hBoGAFiEkVCAfbIFAAAbBmbZiXs445_800x800.jpg");
        photoImageUrlList3.add("https://cimgs1.fenqile.com/product/M00/31/70/hhoGAFiEkVaAYGBBAAAmjsVIcp0657_800x800.jpg");
        photoImageUrlList3.add("https://cimgs1.fenqile.com/product/M00/31/6E/hBoGAFiEkWeAcFjrAAAZts4Fbao307_800x800.jpg");
        photoImageUrlList3.add("https://cimgs1.fenqile.com/product/M00/31/6E/hBoGAFiEkWmAaSPzAAAmjsVIcp0938_800x800.jpg");

        List<String> introduceImageUrlList3 = new ArrayList<>();
        introduceImageUrlList3.add("https://img20.360buyimg.com/vc/jfs/t3706/118/1358090168/124293/ec74db6b/58233894N80183dc7.jpg");
        introduceImageUrlList3.add("https://img20.360buyimg.com/vc/jfs/t3826/234/1145007129/229507/24a4b661/582338b0N26e61b9c.jpg");
        introduceImageUrlList3.add("https://img20.360buyimg.com/vc/jfs/t3826/234/1145007129/229507/24a4b661/582338b0N26e61b9c.jpg");
        introduceImageUrlList3.add("https://img20.360buyimg.com/vc/jfs/t3625/264/1289362047/256424/e9bc900/58218f50Nb480b720.jpg");
        introduceImageUrlList3.add("https://img20.360buyimg.com/vc/jfs/t3625/264/1289362047/256424/e9bc900/58218f50Nb480b720.jpg");
        introduceImageUrlList3.add("https://img20.360buyimg.com/vc/jfs/t3811/127/1115999889/164160/444e8a46/582338c3N881f4f9c.jpg");
        introduceImageUrlList3.add("https://img20.360buyimg.com/vc/jfs/t3811/127/1115999889/164160/444e8a46/582338c3N881f4f9c.jpg");
        introduceImageUrlList3.add("https://img20.360buyimg.com/vc/jfs/t3619/254/1271484469/143636/a5e8d22d/5821909fN894ab61b.jpg");
        introduceImageUrlList3.add("https://img20.360buyimg.com/vc/jfs/t3565/278/1297621073/307390/8abd3a5a/582190adN7ad5901c.jpg");

        Goods goods3 = new Goods("小米（MI）小米圈铁耳机 Pro",
                "https://cimgs1.fenqile.com/product/M00/31/6E/hBoGAFiEkiWAbmxCAAAbBmbZiXs313_680x680.jpg",
                "小米圈铁耳机 Pro",
                149,
                178,
                photoImageUrlList3,introduceImageUrlList3);
        goods3.setCategoryType(ProductCategory.ProductCategoryType.ProductCategory_Daily);

        List<Goods> goodsList = new ArrayList<>();
        goodsList.add(goods);
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);

        goods.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                LogUtil.i(TAG,"goods: s:"+s+"e:"+e);
            }
        });

        goods1.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                LogUtil.i(TAG,"goods: s:"+s+"e:"+e);
            }
        });
        goods2.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                LogUtil.i(TAG,"goods: s:"+s+"e:"+e);
            }
        });
        goods3.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                LogUtil.i(TAG,"goods: s:"+s+"e:"+e);
            }
        });

        ProductCategory productCategory = new ProductCategory("每日排行",ProductCategory.ProductCategoryType.ProductCategory_Daily,goodsList);
        productCategory.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                LogUtil.i(TAG,"productCategory s:"+s+"e:"+e);
            }
        });

        */

        //SELECT * FROM Goods LEFT JOIN ShopCart ON Goods.objectId = ShopCart.goodsId
        String bql ="select * from Goods where objectId in (select goodsId from ShopCart where userId = '111111')";

        new BmobQuery<Goods>().doSQLQuery(bql,new SQLQueryListener<Goods>(){
            @Override
            public void done(BmobQueryResult<Goods> result, BmobException e) {
                if(e ==null){
                    LogUtil.i(TAG, "result:"+result.getResults().size());
                    LogUtil.i(TAG, "result:"+result.getResults());
                }else{
                    LogUtil.i(TAG, "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        });


    }

    private void initData() {
        BmobQuery<ProductCategory> bmobQuery = new BmobQuery<>();
        bmobQuery.setLimit(5);
        bmobQuery.findObjects(new FindListener<ProductCategory>() {
            @Override
            public void done(List<ProductCategory> list, BmobException e) {
                if (e==null){
                    classifyGoods(list);
                }
                else {
                    ToastUtil.showToask("数据加载失败，请检查网络:"+e);
                }
            }
        });
    }


    private void classifyGoods(List<ProductCategory> list) {
        for (ProductCategory productCategory:list){
            List<Goods> goodsList = productCategory.getGoodsList();
            switch (productCategory.getProductCategoryType()){
                case ProductCategory_Daily:
                    dailyGoodsArrayList.addAll(goodsList);
                    break;
                case ProductCategory_Phone:
                    phoneGoodsArrayList.addAll(goodsList);
                    break;
                case ProductCategory_Computer:
                    computerGoodsArrayList.addAll(goodsList);
                    break;
                case ProductCategory_Camera:
                    cameraGoodsArrayList.addAll(goodsList);
                    break;
                case ProductCategory_Earphone:
                    earphoneGoodsArrayList.addAll(goodsList);
                    break;
            }
        }

        mgv_home_daily.setAdapter(new HorizontalListViewAdapter(getContext(), dailyGoodsArrayList));
        mgv_home_phone.setAdapter(new HorizontalListViewAdapter(getContext(), phoneGoodsArrayList));
        mgv_home_computer.setAdapter(new HorizontalListViewAdapter(getContext(), computerGoodsArrayList));
        mgv_home_camera.setAdapter(new HorizontalListViewAdapter(getContext(), cameraGoodsArrayList));
        mgv_home_earphone.setAdapter(new HorizontalListViewAdapter(getContext(), earphoneGoodsArrayList));

        mgv_home_daily.setOnItemClickListener(new MyOnItemClickListener(dailyGoodsArrayList));
        mgv_home_phone.setOnItemClickListener(new MyOnItemClickListener(phoneGoodsArrayList));
        mgv_home_computer.setOnItemClickListener(new MyOnItemClickListener(computerGoodsArrayList));
        mgv_home_camera.setOnItemClickListener(new MyOnItemClickListener(cameraGoodsArrayList));
        mgv_home_earphone.setOnItemClickListener(new MyOnItemClickListener(earphoneGoodsArrayList));

        final Goods goods = dailyGoodsArrayList.get(2);
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
}
