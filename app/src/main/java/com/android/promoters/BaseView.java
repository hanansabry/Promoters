package com.android.promoters;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}
