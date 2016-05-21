package org.grakovne.qook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.grakovne.qook.R;
import org.grakovne.qook.billing.IabException;
import org.grakovne.qook.billing.IabHelper;
import org.grakovne.qook.billing.IabResult;
import org.grakovne.qook.billing.Inventory;
import org.grakovne.qook.billing.Purchase;
import org.grakovne.qook.billing.SkuDetails;
import org.grakovne.qook.managers.LevelManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AdvancedActivity extends BaseActivity {
    private IabHelper mHelper;
    private boolean isBillingAvailable;
    private static final int OPEN_LEVELS_RESPONSE = 200;


    String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAha7b6VWXKrV4nGLs3lP38s9NJqc7vumuRELyMrp0Qv/8wQuFGWXL36uYmULDCN4YGHW0/u4RC3i1tUQoH2AflDGM/nun4idmoozrswIHwjI1dKep3NFMjHLpvgXm+4PnNRTEmRXEG42GLAgABn/47eIie/ODgXOwfmNhyMlPaieKjxbX462jXQ9/EaqntMMkhomBlfb57xi/2Vc+yGlAKO52sBj0xCR8tQT/67kkP4LkDR++07V4lbLQroiRb9p/TECQbr/UiZEXvPmARbqw6WjAeJBguJtrdR8OvCKKJdA0F/Mj6tBXEZQmePSChp1fb91QixH9eO5atkZZL6ueKwIDAQAB";
    @InjectView(R.id.title_text)
    TextView titleText;
    @InjectView(R.id.open_all_levels_buy_button)
    Button openAllLevelsBuyButton;
    @InjectView(R.id.help_button)
    Button helpButton;
    @InjectView(R.id.menu_list)
    LinearLayout menuList;
    @InjectView(R.id.menu_buttons_panel)
    ScrollView menuButtonsPanel;

    private LevelManager manager;
    private static final String open_levels_item = "open_all_levels";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        ButterKnife.inject(this);
        manager = LevelManager.build(this);

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        //TODO: should set to false on release
        mHelper.enableDebugLogging(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHelper != null) try {
            mHelper.dispose();
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
        mHelper = null;
    }

    @OnClick(R.id.help_button)
    public void onHelpButtonClick() {
        manager.dropProgress();
        switchActivity(HelpActivity.class);
    }

    @OnClick(R.id.open_all_levels_buy_button)
    public void onOpenAllLevelsClick() {
        try {
            Inventory inventory = mHelper.queryInventory();
            Log.d("Qook", String.valueOf(inventory.getSkuDetails(open_levels_item)));
        } catch (IabException e) {
            e.printStackTrace();
        }
    }

    private void processUpdatePremiumState() {
        try {
            Inventory inventory = mHelper.queryInventory();

            if (inventory.hasPurchase(open_levels_item)) {
                premiumOpenLevels();
            }

        } catch (IabException e) {
            e.printStackTrace();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OPEN_LEVELS_RESPONSE) {
            processUpdatePremiumState();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);


        if (mHelper.isSetupDone()) {
            checkPurchases();
            return;
        }

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    isBillingAvailable = false;
                    return;
                }

                isBillingAvailable = true;
                Log.d("Qook", "Billing is OK");

                checkPurchases();
            }
        });

    }

    public void checkPurchases() {
        if (mHelper == null || !isBillingAvailable || !mHelper.isSetupDone()) {
            return;
        }

        if (manager.getMaximalLevelNumber() == manager.getLevelsNumber() || !isBillingAvailable) {
            setOpenLevelsButtonClickable(false);
            return;
        }

        processUpdatePremiumState();
    }


    private void setOpenLevelsButtonClickable(boolean state) {
        openAllLevelsBuyButton.setClickable(state);

        if (state) {
            openAllLevelsBuyButton.setBackgroundResource(R.drawable.button_normal);
        } else {
            openAllLevelsBuyButton.setBackgroundResource(R.drawable.button_dark);
        }
    }

    private void premiumOpenLevels() {
        manager.openAllLevels();
        switchActivity(LevelSelectorActivity.class);
    }
}
