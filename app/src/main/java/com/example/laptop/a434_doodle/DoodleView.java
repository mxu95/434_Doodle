package com.example.laptop.a434_doodle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Stack;

public class DoodleView extends View {

    private ArrayList<Paint> paints = new ArrayList<Paint>();
    private ArrayList<Path> paths = new ArrayList<Path>();
    private Stack<Paint> undonePaints = new Stack<Paint>();
    private Stack<Path> undonePaths = new Stack<Path>();
    private Paint paintDoodle = new Paint();
    private Path path = new Path();

    public DoodleView(Context context) {
        super(context);
        init();
    }

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paintDoodle.setColor(Color.BLACK);
        paintDoodle.setAntiAlias(true);
        paintDoodle.setStyle(Paint.Style.STROKE);
        paintDoodle.setStrokeWidth(10);
    }

    private void restorePaintSettings(){
        if(!paints.isEmpty()){
            paintDoodle.setAntiAlias(true);
            paintDoodle.setStyle(Paint.Style.STROKE);
            paintDoodle.setColor(paints.get(paints.size() - 1).getColor());
            paintDoodle.setStrokeWidth(paints.get(paints.size() - 1).getStrokeWidth());
        }
    }

    public void setPaintColor(int color) {
        paintDoodle.setColor(color);
    }

    public void setPaintWidth(float width) {
        paintDoodle.setStrokeWidth(width);
    }

    public boolean undo() {
        if(paints.size() >= 1) {
            undonePaints.push(paints.get(paints.size() - 1));
            undonePaths.push(paths.get(paints.size() - 1));
            paints.remove(paints.size() - 1);
            paths.remove(paths.size() - 1);
            invalidate();
            return true;
        } else {
            return false;
        }
    }

    public boolean redo() {
        if(undonePaints.size() >= 1) {
            paints.add(undonePaints.pop());
            paths.add(undonePaths.pop());
            invalidate();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i < paints.size(); i++) {
            canvas.drawPath(paths.get(i), paints.get(i));
        }
        canvas.drawPath(path, paintDoodle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();

        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                paints.add(paintDoodle);
                paths.add(path);
                paintDoodle = new Paint();
                path = new Path();
                restorePaintSettings();
                break;
        }

        invalidate();
        return true;
    }


}
