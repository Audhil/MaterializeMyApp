package com.wordpress.smdaudhilbe.mohammed_2284.materializemyapp;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.transition.Slide;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wordpress.smdaudhilbe.transitionadapter.TransitionAdapter;
import com.wordpress.smdaudhilbe.utils.WindowsCompatUtils;

/**
 * Created by mohammed-2284 on 11/03/15.
 */
public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransition();

        //  postPoning activity "Enter" transition for a long delay work to complete (network)
        ActivityCompat.postponeEnterTransition(this);

        String itemTitle = getIntent().getStringExtra(MainActivity.EXTRA_TITLE);
        setSecondActivityActionBar(itemTitle);

        final ImageView iView = (ImageView) findViewById(R.id.imageSecondScreen);
        ViewCompat.setTransitionName(iView, MainActivity.EXTRA_IMAGE);

        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra(MainActivity.EXTRA_IMAGE)).into(iView, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) iView.getDrawable()).getBitmap();
                Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        applyPalette(palette, iView);
                    }
                });
            }

            @Override
            public void onError() {

            }
        });

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(itemTitle);

        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            sBuilder.append(getString(R.string.matterIs));
        }

        TextView description = (TextView) findViewById(R.id.description);
        description.setText(sBuilder.toString());
    }

    //  applying palette -  for colouring the bar
    private void applyPalette(Palette palette, ImageView iView) {
        int primaryDark = getResources().getColor(R.color.primary_dark);
        int primary = getResources().getColor(R.color.primary);

        int toolBarBackGroundColor = palette.getMutedColor(primary);
        setBackGroundColorActionBar(toolBarBackGroundColor);

        WindowsCompatUtils.setStatusBarColor(getWindow(), palette.getDarkMutedColor(primaryDark));

        initScrollFade(iView);

        updateBackground(findViewById(R.id.fab), palette);

        ActivityCompat.startPostponedEnterTransition(SecondActivity.this);
    }

    private void updateBackground(View view, Palette palette) {
        int lightMutedColor = palette.getLightMutedColor(getResources().getColor(R.color.accent));
        int mutedColor = palette.getMutedColor(getResources().getColor(R.color.accent));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RippleDrawable ripple = (RippleDrawable) view.getBackground();
            GradientDrawable rippleBackground = (GradientDrawable) ripple.getDrawable(0);
            rippleBackground.setColor(lightMutedColor);
            ripple.setColor(ColorStateList.valueOf(mutedColor));
        } else {
            StateListDrawable drawable = (StateListDrawable) view.getBackground();
            drawable.setColorFilter(mutedColor, PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initScrollFade(final ImageView iView) {
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        setComponentsStatus(scrollView, iView);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                setComponentsStatus(scrollView, iView);
            }
        });
    }

    private void setComponentsStatus(View scrollView, ImageView image) {
        int scrollY = scrollView.getScrollY();
        image.setTranslationY(-scrollY / 2);
        ColorDrawable background = (ColorDrawable) getToolBarBackGroundDrawable();
        int padding = scrollView.getPaddingTop();
        double alpha = (1 - (((double) padding - (double) scrollY) / (double) padding)) * 255.0;
        alpha = alpha < 0 ? 0 : alpha;
        alpha = alpha > 255 ? 255 : alpha;

        background.setAlpha((int) alpha);

        float scrollRatio = (float) (alpha / 255f);
        int titleColor = getAlphaColor(Color.WHITE, scrollRatio);
        setTitleTxtColorActionBar(titleColor);
    }

    private int getAlphaColor(int color, float scrollRatio) {
        return Color.argb((int) (scrollRatio * 255f), Color.red(color), Color.green(color), Color.blue(color));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_second;
    }

    public void initActivityTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    /**
     * It seems that the ActionBar view is reused between activities. Changes need to be reverted,
     * or the ActionBar will be transparent when we go back to Main Activity
     */
    private void restablishActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getReturnTransition().addListener(new TransitionAdapter() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    revertToolBarChanges();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        restablishActionBar();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            restablishActionBar();
        }

        return super.onOptionsItemSelected(item);
    }
}