package org.grakovne.qook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.grakovne.qook.R;
import org.grakovne.qook.billing.IabException;
import org.grakovne.qook.billing.IabHelper;
import org.grakovne.qook.billing.IabResult;
import org.grakovne.qook.billing.Inventory;
import org.grakovne.qook.billing.Purchase;
import org.grakovne.qook.managers.LevelManager;
import org.grakovne.qook.managers.SharedSettingsManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AdvancedActivity extends BaseActivity {
    @InjectView(R.id.toggle_animation_button)
    Button toggleAnimationButton;
    @InjectView(R.id.purchase_undo_buy_button)
    Button purchaseUndoBuyButton;
    private IabHelper mHelper;
    private static final int OPEN_LEVELS_RESPONSE = 200;
    private static final int OPEN_UNDO_RESPONSE = 201;
    private static final int BOUGHT_ALREADY_CODE = 7;
    private static final String OPEN_LEVEL_ITEM = "open_all_levels";
    private static final String OPEN_UNDO_ITEM = "open_undo_steps";

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

    private LevelManager levelManager;
    private SharedSettingsManager sharedSettingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        ButterKnife.inject(this);
        levelManager = LevelManager.build(this);
        sharedSettingsManager = SharedSettingsManager.build(this);

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    setOpenLevelsButtonClickable(false);
                    setUndoButtonClickable(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        overridePendingTransition(0, 0);

        if (levelManager.getMaximalLevelNumber() >= levelManager.getLevelsNumber()) {
            setOpenLevelsButtonClickable(false);
        }

        if (sharedSettingsManager.isUndoPurchased()){
            setUndoButtonClickable(false);
        }

        boolean isAnimationNeed = sharedSettingsManager.isAnimationNeed();
        if (isAnimationNeed) {
            toggleAnimationButton.setText(getString(R.string.off_animation));
        } else {
            toggleAnimationButton.setText(getString(R.string.on_animation));
        }
    }

    private void setOpenLevelsButtonClickable(boolean state) {
        openAllLevelsBuyButton.setClickable(state);

        if (state) {
            openAllLevelsBuyButton.setBackgroundResource(R.drawable.button_normal);
        } else {
            openAllLevelsBuyButton.setBackgroundResource(R.drawable.button_dark);
        }
    }

    private void setUndoButtonClickable(boolean state) {
        purchaseUndoBuyButton.setClickable(state);

        if (state) {
            purchaseUndoBuyButton.setBackgroundResource(R.drawable.button_normal);
        } else {
            purchaseUndoBuyButton.setBackgroundResource(R.drawable.button_dark);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHelper != null && mHelper.isSetupDone()) {
            try {
                mHelper.dispose();
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }

            mHelper = null;
        }
    }

    @OnClick(R.id.help_button)
    public void onHelpButtonClick() {
        switchActivity(HelpActivity.class);
    }

    private void premiumOpenLevels() {
        levelManager.openAllLevels();
        switchActivity(LevelSelectorActivity.class);
    }

    private void premiumOpenUndo(){
        sharedSettingsManager.makeUndoPurchased();
        Toast.makeText(this, getString(R.string.undo_purchased), Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.open_all_levels_buy_button)
    public void onOpenAllLevelsClick() {
        try {
            mHelper.launchPurchaseFlow(this, OPEN_LEVEL_ITEM, OPEN_LEVELS_RESPONSE, new IabHelper.OnIabPurchaseFinishedListener() {
                @Override
                public void onIabPurchaseFinished(IabResult result, Purchase info) {
                    if (result.getResponse() == BOUGHT_ALREADY_CODE) {
                        premiumOpenLevels();
                    }
                }
            });
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.purchase_undo_buy_button)
    public void onOpenUndoClick() {
        try {
            mHelper.launchPurchaseFlow(this, OPEN_UNDO_ITEM, OPEN_UNDO_RESPONSE, new IabHelper.OnIabPurchaseFinishedListener() {
                @Override
                public void onIabPurchaseFinished(IabResult result, Purchase info) {
                    if (result.getResponse() == BOUGHT_ALREADY_CODE) {
                        premiumOpenUndo();
                        setUndoButtonClickable(false);
                    }
                }
            });
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_LEVELS_RESPONSE) {
            try {
                Inventory inventory = mHelper.queryInventory();
                if (inventory.hasPurchase(OPEN_LEVEL_ITEM)) {
                    premiumOpenLevels();
                    setOpenLevelsButtonClickable(false);
                }
            } catch (IabException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == OPEN_UNDO_RESPONSE) {
            Inventory inventory = null;
            try {
                inventory = mHelper.queryInventory();
                if (inventory.hasPurchase(OPEN_UNDO_ITEM)) {
                    Log.d("Qook", "premium");
                    premiumOpenUndo();
                    setUndoButtonClickable(false);
                }
            } catch (IabException e) {
                e.printStackTrace();
            }

        }

    }

    @OnClick(R.id.toggle_animation_button)
    public void onToggleAnimationButtonClick() {
        boolean isAnimationNeed = sharedSettingsManager.isAnimationNeed();
        if (isAnimationNeed) {
            sharedSettingsManager.setIsAnimationNeed(false);
            toggleAnimationButton.setText(getString(R.string.on_animation));
        } else {
            sharedSettingsManager.setIsAnimationNeed(true);
            toggleAnimationButton.setText(getString(R.string.off_animation));
        }
    }


}
