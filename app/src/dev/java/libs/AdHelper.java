package libs;

import android.app.Activity;
import android.view.View;

import com.madpixels.tgadmintools.activity.MainActivity;


/**
 * Dummy class with Ad implementation, just for flavors
 * Created by Snake on 15.05.2015.
 */
public class AdHelper {

    public static void setup(final MainActivity a) {


    }

    public static void onResume(Activity a) {

    }

    public static void showBanner(View bannerParent) {

    }

    private static boolean showAdInterstitial(final Activity a) {

        return false;
    }

    /**
     * показ без задержки
     */
    static boolean isInterstitialLoaded() {
        return false;
    }


    public static void onAppExit(final MainActivity a) {
            a.finish();

    }

    public static void showInterstitialForce(Activity a) {
        showAdInterstitial(a);
    }


    public static void onCloseActivity(Activity a) {
        a.finish();
    }


}
