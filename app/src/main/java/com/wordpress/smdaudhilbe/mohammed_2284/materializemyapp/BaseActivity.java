package com.wordpress.smdaudhilbe.mohammed_2284.materializemyapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by mohammed-2284 on 10/03/15.
 */
public abstract class BaseActivity extends ActionBarActivity {

    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResource());

        toolBar = (Toolbar) findViewById(R.id.myToolbar);

        if (toolBar != null) {
            setSupportActionBar(toolBar);
            getSupportActionBar().setTitle("");
        }
    }

    protected abstract int getLayoutResource();

    protected void setActionBarIcon(int iconRes) {

        if (toolBar != null) {
            toolBar.setNavigationIcon(iconRes);
        }
    }

    /*
    * SecondActivity
    * */
    protected void setSecondActivityActionBar(String title) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    //  setting colour for actionbar
    protected void setBackGroundColorActionBar(int colorActionBar) {
        if (toolBar != null) {
            toolBar.setBackgroundColor(colorActionBar);
        }
    }

    protected void setTitleTxtColorActionBar(int textColor) {
        if (toolBar != null) {
            toolBar.setTitleTextColor(textColor);
        }
    }

    protected Drawable getToolBarBackGroundDrawable() {

        if (toolBar != null) {
            return toolBar.getBackground();
        }
        return null;
    }

    protected void revertToolBarChanges() {
        if (toolBar != null) {
            toolBar.setTitleTextColor(Color.WHITE);
            toolBar.getBackground().setAlpha(255);
        }
    }


    /*
     * MainActivity
     * */
    protected void setMainActivityActionBar(String title) {
        getSupportActionBar().setTitle(title);
    }
}