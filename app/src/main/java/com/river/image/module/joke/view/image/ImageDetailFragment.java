package com.river.image.module.joke.view.image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import com.river.image.Constants;
import com.river.image.R;
import com.river.image.base.BaseFragment;
import com.river.image.bean.JokeBean.ShowapiResBodyBean.ContentlistBean;
import com.river.image.utils.BitmapUtil;
import com.river.image.widget.PinchImageView;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/9/22.
 */

public class ImageDetailFragment extends BaseFragment  implements ViewPager.OnPageChangeListener{
  //private Observable<List<ContentlistBean>> mImageJokeObservable;
  private ImageDetailAdapter mAdapter;

  private int current;
  private List<ContentlistBean> datas;
  @BindView(R.id.viewpager) ViewPager mViewPager;

  public static ImageDetailFragment getInstance(ArrayList<Parcelable> datas,int currentIndex){
    Bundle bundle = new Bundle();
    ImageDetailFragment imageDetailFragment = new ImageDetailFragment();
    bundle.putParcelableArrayList("image",datas);
    bundle.putInt("current",currentIndex);
    imageDetailFragment.setArguments(bundle);
    return imageDetailFragment;
  }
  @Override protected void initData() {
    //mImageJokeObservable = RxBus.get().register("imageJoke",null);
    //mImageJokeObservable.subscribe(contentlistBeen -> {
    //  datas=contentlistBeen;
    //  KLog.d("TAG","datas-->"+datas.size());
    //
    //});
   // Log.d("TAG","initData--》"+datas.getData().size());
    Bundle bundle = getArguments();
    if(null!=bundle){
      datas =   bundle.getParcelableArrayList("image");
      current = bundle.getInt("current");
    }
    mAdapter = new ImageDetailAdapter(mActivity,datas);
    mViewPager.setAdapter(mAdapter);
    mViewPager.setCurrentItem(current);
    mViewPager.addOnPageChangeListener(this);

  }
  public void onSave(){
    String imgUrl=datas.get(current).img;
    PinchImageView imageView = getCurrentImageView();
    Bitmap bitmap = BitmapUtil.drawableToBitmap(imageView.getDrawable());
    boolean isSuccess=BitmapUtil.saveBitmap(bitmap,
        Constants.SAVE_DIR,imgUrl.substring(imgUrl.lastIndexOf("/")+1,imgUrl.length()),true);
    if(isSuccess){
      //Snackbar.make(mRootView,"下载完成，请主人验收",Snackbar.LENGTH_LONG).show();
    }else{
      //Snackbar.make(mRootView,"下载失败，请重新下载",Snackbar.LENGTH_LONG).show();
    }
  }

  //public void showShare() {
  //  ShareSDK.initSDK(mActivity);
  //  String imgUrl =  datas.get(mViewPager.getCurrentItem()).getUrl();
  //  OnekeyShare oks = new OnekeyShare();
  //  //关闭sso授权
  //  oks.disableSSOWhenAuthorize();
  //  // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
  //  oks.setTitle("标题");
  //  // titleUrl是标题的网络链接，QQ和QQ空间等使用
  //  oks.setTitleUrl(imgUrl);
  //  // text是分享文本，所有平台都需要这个字段
  //  oks.setText("我是分享文本");
  //  // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
  //  //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
  //  // url仅在微信（包括好友和朋友圈）中使用
  //  oks.setUrl(imgUrl);
  //  // comment是我对这条分享的评论，仅在人人网和QQ空间使用
  //  oks.setComment(imgUrl);
  //  // site是分享此内容的网站名称，仅在QQ空间使用
  //  oks.setSite(getString(R.string.app_name));
  //  // siteUrl是分享此内容的网站地址，仅在QQ空间使用
  //  oks.setSiteUrl(imgUrl);
  //
  //  // 启动分享GUI
  //  oks.show(mActivity);
  //}
  private PinchImageView getCurrentImageView() {
    View currentItem = mAdapter.getPrimaryItem();
    if (currentItem == null) {
      return null;
    }
    PinchImageView imageView = (PinchImageView) currentItem.findViewById(R.id.img);
    if (imageView == null) {
      return null;
    }
    return imageView;
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_gril;
  }


  @Override public void onPageScrolled(int position, float positionOffset,
      int positionOffsetPixels) {

  }

  @Override public void onPageSelected(int position) {

  }

  @Override public void onPageScrollStateChanged(int state) {

  }

}
