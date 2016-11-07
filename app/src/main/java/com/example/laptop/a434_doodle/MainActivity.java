package com.example.laptop.a434_doodle;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int brushColorProgress;
    int brushColor;
    int brushWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Auto-generated code by making a new navigation bar activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        brushWidth = 10;
        brushColor = Color.BLACK;
        brushColorProgress = 1792;
    }

    @Override
    //Auto-generated code by making a new navigation bar activity
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @TargetApi(16)
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Gets the DoodleView to change the brush settings
        final DoodleView doodleView = (DoodleView) findViewById(R.id.doodleView);

        if (id == R.id.paint_color) {
            // Handle Paint Color action by popping up an alertdialog with a seekbar to choose color
            final AlertDialog.Builder colorDialog = new AlertDialog.Builder(this);
            colorDialog.setTitle("Brush Color").setMessage("Select your brush color:");

            final SeekBar colorSeekBar = new SeekBar(this);
            colorSeekBar.setMax(1792);
            colorSeekBar.setKeyProgressIncrement(1);

            //Sets the color and progress of the seekbar to match the currently selected color
            colorSeekBar.setProgress(brushColorProgress);
            colorSeekBar.getProgressDrawable().setColorFilter(brushColor, PorterDuff.Mode.MULTIPLY);

            //Sets the size of the thumb to match the currently selected width
            ShapeDrawable thumb = new ShapeDrawable(new OvalShape());
            thumb.setIntrinsicHeight(brushWidth);
            thumb.setIntrinsicWidth(brushWidth);
            colorSeekBar.setThumb(thumb);

            colorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //changes color of progress bar according to the color chosen
                    seekBar.getProgressDrawable().setColorFilter(progressToColorInt(progress), PorterDuff.Mode.MULTIPLY);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    //do nothing
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //do nothing
                }
            });

            colorDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //stores progress and color ints to restore to this position when Paint Color is selected again later
                    brushColorProgress = colorSeekBar.getProgress();
                    brushColor = progressToColorInt(brushColorProgress);
                    doodleView.setPaintColor(brushColor);
                }
            });
            colorDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing
                }
            });

            colorDialog.setView(colorSeekBar);
            colorDialog.show();
        } else if (id == R.id.paint_width) {
            // Handle Paint Color action by popping up an alertdialog with a seekbar to choose color
            final AlertDialog.Builder widthDialog = new AlertDialog.Builder(this);
            widthDialog.setTitle("Brush Width").setMessage("Select your brush width:");

            final SeekBar widthSeekBar = new SeekBar(this);
            widthSeekBar.setMax(100);
            widthSeekBar.setKeyProgressIncrement(1);
            widthSeekBar.setProgress(brushWidth);

            //Sets the color of the seekbar to match the currently selected color
            widthSeekBar.getProgressDrawable().setColorFilter(brushColor, PorterDuff.Mode.MULTIPLY);


            //Sets the size of the thumb to match the currently selected width
            ShapeDrawable thumb = new ShapeDrawable(new OvalShape());
            thumb.setIntrinsicHeight(brushWidth);
            thumb.setIntrinsicWidth(brushWidth);
            widthSeekBar.setThumb(thumb);

            widthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //Modifies the size of the thumb on the seekbar according to brush size
                    ShapeDrawable thumb = new ShapeDrawable(new OvalShape());
                    thumb.setIntrinsicHeight(progress);
                    thumb.setIntrinsicWidth(progress);
                    widthSeekBar.setThumb(thumb);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    //do nothing
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //do nothing
                }
            });

            widthDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //stores brush width to restore to this position when Paint Width is selected again later
                    brushWidth = widthSeekBar.getProgress();
                    doodleView.setPaintWidth(brushWidth);
                }
            });
            widthDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing
                }
            });

            widthDialog.setView(widthSeekBar);
            widthDialog.show();
        } else if (id == R.id.undo) {
            if(!doodleView.undo()) {
                Toast.makeText(this, "Everything is already undone", Toast.LENGTH_SHORT);
            }
        } else if (id == R.id.redo) {
            if(!doodleView.redo()) {
                Toast.makeText(this, "Everything is already redone", Toast.LENGTH_SHORT);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Converts from progress between 0 and 1792 into a Color int
    private int progressToColorInt(int progress) {
        int red = 0;
        int green = 0;
        int blue = 0;
        if(progress < 256) {
            blue = progress;
        } else if(progress < 256*2) {
            green = progress%256;
            blue = 256 - progress%256;
        } else if(progress < 256*3) {
            green = 255;
            blue = progress%256;
        } else if(progress < 256*4) {
            red = progress%256;
            green = 256 - progress%256;
            blue = 256 - progress%256;
        } else if(progress < 256*5) {
            red = 255;
            green = 0;
            blue = progress%256;
        } else if(progress < 256*6) {
            red = 255;
            green = progress%256;
            blue = 256 - progress%256;
        } else if(progress < 256*7) {
            red = 255;
            green = 255;
            blue = progress%256;
        }
        return(Color.argb(255, red, green, blue));
    }
}
