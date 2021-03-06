package com.example.mylibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Activity启动工具类
 */
public class ActivityUtils {
  /**
   * 启动Fragment
   *
   * @param fragment 当前所在 {@link Fragment}
   * @param clazz 目标 {@link Fragment},必须为{@link BaseFragment}的子类,且必须声明泛型参数.
   * 泛型参数必须是{@link BaseActivity}及其子类
   * @param bundle 参数
   * @param requestCode 请求码
   */
  public static void startActivity(@NonNull Fragment fragment,
      @NonNull Class<? extends BaseFragment<? extends BaseActivity>> clazz, @Nullable Bundle bundle,
      int requestCode) {
    if (bundle == null) bundle = new Bundle();

    // 指定使用哪个 Fragment 显示在 Activity 中
    bundle.putString(BaseActivity.FRAGMENT_CLASS_NAME, clazz.getName());

    Intent intent = new Intent();
    intent.putExtras(bundle);
    // 泛型参数指定打开那个Activity
    intent.setClass(fragment.getContext(), (Class) getGenericType(clazz));

    if (requestCode != -1) {
      fragment.startActivityForResult(intent, requestCode);
    } else {
      fragment.startActivity(intent);
    }
  }

  /**
   * 启动Fragment
   *
   * @param fragment 当前所在 {@link Fragment}
   * @param clazz 目标 {@link Fragment},必须为{@link BaseFragment}的子类,且必须声明泛型参数.
   * 泛型参数必须是{@link BaseActivity}及其子类
   * @param bundle 参数
   */
  public static void startActivity(@NonNull Fragment fragment,
      @NonNull Class<? extends BaseFragment<? extends BaseActivity>> clazz,
      @Nullable Bundle bundle) {
    startActivity(fragment, clazz, bundle, -1);
  }

  /**
   * 启动Fragment
   *
   * @param fragment 当前所在 {@link Fragment}
   * @param clazz 目标 {@link Fragment},必须为{@link BaseFragment}的子类,且必须声明泛型参数.
   * 泛型参数必须是{@link BaseActivity}及其子类
   */
  public static void startActivity(@NonNull Fragment fragment,
      @NonNull Class<? extends BaseFragment<? extends BaseActivity>> clazz) {
    startActivity(fragment, clazz, null, -1);
  }

  /**
   * 启动Fragment
   *
   * @param activity 当前所在Fragment
   * @param clazz 目标Fragment,必须为{@link BaseFragment}的子类,且必须声明泛型参数.
   * 泛型参数必须是{@link BaseActivity}及其子类
   * @param bundle 参数
   * @param requestCode 请求码
   */
  public static void startActivity(Activity activity,
      Class<? extends BaseFragment<? extends BaseActivity>> clazz, Bundle bundle, int requestCode) {
    if (bundle == null) bundle = new Bundle();
    bundle.putString(BaseActivity.FRAGMENT_CLASS_NAME, clazz.getName());

    Intent intent = new Intent();
    intent.putExtras(bundle);
    // 泛型参数指定打开那个Activity
    intent.setClass(activity, (Class) getGenericType(clazz));

    if (requestCode != -1) {
      activity.startActivityForResult(intent, requestCode);
    } else {
      activity.startActivity(intent);
    }
  }

  /**
   * 启动Fragment
   *
   * @param activity 当前所在Fragment
   * @param clazz 目标Fragment,必须为{@link BaseFragment}的子类,且必须声明泛型参数.
   * 泛型参数必须是{@link BaseActivity}及其子类
   * @param bundle 参数
   */
  public static void startActivity(Activity activity,
      Class<? extends BaseFragment<? extends BaseActivity>> clazz, Bundle bundle) {
    startActivity(activity, clazz, bundle, -1);
  }

  /**
   * 启动Fragment
   *
   * @param activity 当前所在Fragment
   * @param clazz 目标Fragment,必须为{@link BaseFragment}的子类,且必须声明泛型参数.
   * 泛型参数必须是{@link BaseActivity}及其子类
   */
  public static void startActivity(Activity activity,
      Class<? extends BaseFragment<? extends BaseActivity>> clazz) {
    startActivity(activity, clazz, null, -1);
  }

  /**
   * 获取泛型的参数类型 eg: public class Test extends Base<????> 我们获取的是 ???? 的类型
   *
   * @param clazz 需要获取泛型类型的类的字节码
   * @return {@link Type} 可以强转为Class
   */
  private static Type getGenericType(Class clazz) {
    Type superClass = clazz.getGenericSuperclass();
    if (superClass instanceof Class<?>) { // sanity check, should never happen
      throw new IllegalArgumentException(
          "Internal error: TypeReference constructed without actual type information");
    }
        /*
         * 22-Dec-2008, tatu: Not sure if this case is safe -- I suspect
		 * it is possible to make it fail?
		 * But let's deal with specific
		 * case when we know an actual use case, and thereby suitable
		 * workarounds for valid case(s) and/or error to throw
		 * on invalid one(s).
		 */
    return ((ParameterizedType) superClass).getActualTypeArguments()[0];
  }
}
