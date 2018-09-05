package org.liumy.picture.core.application;

import org.liumy.picture.core.util.CrashHandler;
import org.lmy.open.utillibrary.UtilApplication;

/**
 * @author: liumy
 * @date: 2018/09/04
 * @desctiption: xxxx
 */
public class Application extends UtilApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init();
    }
}
