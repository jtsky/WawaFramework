package com.duiba.component_base.component;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.duiba.component_base.component.BaseActivity;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.orhanobut.logger.Logger;

/**
 * @author: jintai
 * @time: 2018/3/23-15:13
 * @Email: jintai@qccr.com
 * @desc:
 */
public class DuibaMvpPresenter<Model extends ViewModel, V extends DuibaMvpView> extends MvpBasePresenter<V> {

    /**
     * An Action that is executed to interact with the view.
     * Usually a Presenter should not get any data from View: so calls like view.getUserId() should not be done.
     * Rather write a method in your Presenter that takes the user id as parameter like this:
     * {@code
     * void doSomething(int userId){
     * // do something
     * ...
     * <p>
     * ifViewAttached( view -> view.showSuccessful())
     * }
     *
     * @param <V> The Type of the View
     */
    public interface ViewModelAction<Model> {

        /**
         * This method will be invoked to run the action. Implement this method to interact with the view.
         *
         * @param viewModel The reference to the viewModel. Not null.
         */
        void run(@NonNull Model viewModel);
    }

    /**
     * Calls {@link #ifViewAttached(boolean, ViewAction)} with false as first parameter (don't throw
     * exception if view not attached).
     *
     * @see #ifViewAttached(boolean, ViewAction)
     */
    protected final void ifViewModelAttached(ViewModelAction<Model> action) {
        ifViewModelAttached(false, action);
    }

    @Deprecated
    protected final void ifViewModelAttached(boolean exceptionIfViewNotAttached, ViewModelAction<Model> action) {
        ifViewAttached(view -> {
            if (view.getViewModel() == null) {
                Logger.e("viewModel 为空");
                //throw new NullPointerException("viewModel 为空");
            } else if (exceptionIfViewNotAttached) {
                throw new IllegalStateException(
                        "No ViewModel attached to Presenter");
            } else {
                action.run((Model) view.getViewModel());
            }
        });
    }
}
