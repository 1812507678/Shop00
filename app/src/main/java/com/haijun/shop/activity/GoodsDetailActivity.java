package com.haijun.shop.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haijun.shop.R;
import com.haijun.shop.adapter.ImagesViewPageAdapter;
import com.haijun.shop.bean.Goods;
import com.haijun.shop.bean.ShopCart;
import com.haijun.shop.bean.User;
import com.haijun.shop.util.ChooseAlertDialogUtil;
import com.haijun.shop.util.Constant;
import com.haijun.shop.util.DialogUtil;
import com.haijun.shop.util.LogUtil;
import com.haijun.shop.util.SPUtil;
import com.haijun.shop.util.ShopCartUtil;
import com.haijun.shop.util.ShowToaskDialogUtil;
import com.haijun.shop.util.ToastUtil;
import com.haijun.shop.util.UserUtil;

import org.xutils.ImageManager;
import org.xutils.x;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = GoodsDetailActivity.class.getSimpleName();
    private ViewPager vp_goodsdetail_images;
    private int mPreviousSelectedPosition = 0;
    private TextView tv_detail_title;
    private TextView tv_detail_introduce;
    private TextView tv_goodsdetail_buy;
    private LinearLayout ll_detail_introimages;
    private TextView tv_goodsdetail_add;
    private LinearLayout ll_detail_point;
    private ImageManager mImageManager;
    private TextView tv_detail_curpirce;
    private TextView tv_detail_oldpirce;
    private Goods mGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
    }

    @Override
    protected void initView() {
        setCenterText("商品详情");
        setLeftImage(R.drawable.back_normal);

        vp_goodsdetail_images = findViewById(R.id.vp_goodsdetail_images);
        tv_detail_title = findViewById(R.id.tv_detail_title);
        tv_detail_introduce = findViewById(R.id.tv_detail_introduce);
        tv_goodsdetail_buy = findViewById(R.id.tv_goodsdetail_buy);
        tv_goodsdetail_add = findViewById(R.id.tv_goodsdetail_add);
        ll_detail_introimages = findViewById(R.id.ll_detail_introimages);
        tv_detail_curpirce = findViewById(R.id.tv_detail_curpirce);
        tv_detail_oldpirce = findViewById(R.id.tv_detail_oldpirce);

        ll_detail_point = findViewById(R.id.ll_detail_point);

        tv_goodsdetail_buy.setOnClickListener(this);
        tv_goodsdetail_add.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        mImageManager = x.image();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle!=null){
            mGoods = bundle.getParcelable("goods");
            if (mGoods !=null){
                initSlidePointView(mGoods);
                initDetailInroView(mGoods);

                ImagesViewPageAdapter imagesViewPageAdapter = new ImagesViewPageAdapter(mGoods.getPhotoImageUrlList(),this);
                vp_goodsdetail_images.setAdapter(imagesViewPageAdapter);

                tv_detail_title.setText(mGoods.getName());
                tv_detail_introduce.setText(mGoods.getSpecification());
                tv_detail_curpirce.setText("¥"+ mGoods.getCurPrice());
                tv_detail_oldpirce.setText("¥"+ mGoods.getOldPrice());
                tv_detail_oldpirce.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线

            }
        }



    }


    private void initSlidePointView(Goods goods) {
        for (int i = 0; i < goods.getPhotoImageUrlList().size(); i++){
            View pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.selector_bg_point);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
            if(i != 0)
                layoutParams.leftMargin = 10;
            pointView.setEnabled(false);
            ll_detail_point.addView(pointView, layoutParams);
        }

        vp_goodsdetail_images.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int newPosition = position % 4;
                ll_detail_point.getChildAt(mPreviousSelectedPosition).setEnabled(false);
                ll_detail_point.getChildAt(newPosition).setEnabled(true);
                mPreviousSelectedPosition  = newPosition;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initDetailInroView(Goods goods) {
        for (String imageUrl:goods.getIntrImageUrlList()){
            ImageView imageView = new ImageView(this);
            mImageManager.bind(imageView,imageUrl);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll_detail_introimages.addView(imageView, layoutParams);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_goodsdetail_add:
                addToShopCart();
                break;
            case R.id.tv_goodsdetail_buy:
                buyNow();
                break;
        }
    }

    private void addToShopCart() {
        if (mGoods!=null){
            User userFromSP = UserUtil.getUserInfo();
            if (userFromSP!=null){
                final ShopCart shopCart = new ShopCart(userFromSP.getObjectId(),mGoods.getObjectId());
                LogUtil.i(TAG,"shopCart:"+shopCart);
                shopCart.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        LogUtil.i(TAG,"s:"+s+"    e:"+e);
                        if (e==null){
                            ShopCartUtil.getInstance().addGoodsShopCartList(mGoods);
                            ToastUtil.showToask("加入购物车成功");
                        }
                        else if (e.getErrorCode()==401){
                            ToastUtil.showToask("该商品已经加入购物车，无需重复添加");
                        }
                        else {
                            ToastUtil.showToask("加入购物车失败："+e);
                        }
                    }
                });
            }
            else {
                ToastUtil.showToask("未登陆，请先登陆");
                startActivity(new Intent(this,LoginActivity.class));
            }
        }
    }

    private void buyNow() {
        boolean booleanValueFromSP = SPUtil.getBooleanValueFromSP(Constant.isApplyWithDraw);
        if (booleanValueFromSP){
            ToastUtil.showToask("您已经申请额度，正在审核，审核通过后就可以购物了");
        }
        else {
            ShowToaskDialogUtil.showTipDialog(this, "您还没有申请额度，是否现在申请？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(GoodsDetailActivity.this,WithDrawApplyActivity.class));
                    finish();
                }
            });
        }
    }
}
