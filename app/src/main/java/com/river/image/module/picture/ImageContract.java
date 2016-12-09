package com.river.image.module.picture;

import com.river.image.base.BasePresenter;
import com.river.image.base.BaseView;
import com.river.image.bean.GirlsBean;
import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 */
public interface ImageContract {
  interface View extends BaseView {
    void refresh(List<GirlsBean.ResultsEntity> datas);

    void load(List<GirlsBean.ResultsEntity> datas);

    void showError();

    void showNormal();
  }

  interface Presenter extends BasePresenter {
    void getGirls(int page, int size, boolean isRefresh);
  }
}
