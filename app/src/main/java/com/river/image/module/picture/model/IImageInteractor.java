package com.river.image.module.picture.model;

import com.river.image.callback.LoadImageCallback;
import rx.Subscription;

/**
 * Created by Administrator on 2016/9/9.
 */
public interface IImageInteractor {
  Subscription requestImageInfo(int page, int size, LoadImageCallback callback);
}
