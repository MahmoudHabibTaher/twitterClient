package com.mondo.twitterclient;

/**
 * Created by mahmoud on 11/21/16.
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
