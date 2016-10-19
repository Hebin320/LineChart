package com.hebin.linechart;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 自定义view，用canvas画出两条曲线、X轴坐标等等
 */

public class LineChartView extends View {

    /**
     * 第一条曲线的数字
     */
    private List<Integer> lineOneScore = new ArrayList<>();
    /**
     * 第二条曲线的数字
     */
    private List<Integer> lineTwoScore = new ArrayList<>();
    /**
     * x轴的文字
     */
    private List<String> xLineData = new ArrayList<>();
    /**
     * Y轴的数值
     */
    private List<Integer> yLineData = new ArrayList<>();
    /**
     * 坐标轴、坐标轴字体、线条的画笔
     */
    private Paint datePaint;
    /**
     * 第一条折线的画笔
     */
    private Paint lineOnePaint;
    /**
     * 第二条折线的画笔
     */
    private Paint lineTwoPaint;
    /**
     * 坐标轴、坐标轴字体、线条的颜色
     */
    private int dateColor = Color.parseColor("#636363");
    /**
     * 第一条折线的画笔
     */
    private int lineOneColor = Color.parseColor("#000000");
    /**
     * 第二条折线的画笔
     */
    private int lineTwoColor = Color.parseColor("#f9a13f");
    /**
     * 第一条折线的中间圆点
     */
    private Bitmap lineOneBitmap;
    /**
     * 第二条折线的中间圆点
     */
    private Bitmap lineTwoBitmap;


    private Context context;
    private float tenDP;
    private Path path;


    public LineChartView(Context context, List<Integer> lineOneScore, List<Integer> lineTwoScore, List<String> xLineData, List<Integer> yLineData) {
        super(context);
        init(context, lineOneScore, lineTwoScore, xLineData, yLineData);
    }

    public void init(Context context, List<Integer> lineOneScore, List<Integer> lineTwoScore, List<String> xLineData, List<Integer> yLineData) {
        if (lineOneScore.size() == 0 || lineTwoScore.size() == 0 || xLineData.size() == 0 || yLineData.size() == 0)
            return;
        this.context = context;
        this.xLineData = xLineData;
        this.yLineData = yLineData;
        this.lineOneScore = lineOneScore;
        this.lineTwoScore = lineTwoScore;

        Resources resources = getResources();
        tenDP = resources.getDimension(R.dimen.magin_10);

        datePaint = new Paint();
        datePaint.setStrokeWidth(tenDP * 0.05f);
        datePaint.setTextSize(tenDP * 1.2f);
        datePaint.setColor(dateColor);

        lineOnePaint = new Paint();
        lineOnePaint.setStrokeWidth(tenDP * 0.1f);
        lineOnePaint.setTextSize(tenDP * 1.2f);
        lineOnePaint.setColor(lineOneColor);

        lineTwoPaint = new Paint();
        lineTwoPaint.setStrokeWidth(tenDP * 0.1f);
        lineTwoPaint.setTextSize(tenDP * 1.2f);
        lineTwoPaint.setColor(lineTwoColor);


        path = new Path();
        lineOneBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_point_gray);
        lineTwoBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_point_yellow);
        setLayoutParams(new ViewGroup.LayoutParams((int) (this.xLineData.size() * 6f * tenDP + 3.5f * tenDP), ViewGroup.LayoutParams.MATCH_PARENT));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        drawLineOne(canvas);
        drawLineTwo(canvas);
        drawDate(canvas);
        drawLine(canvas);
    }


    /**
     * 绘制时间
     *
     * @param c
     */
    public void drawDate(Canvas c) {

        for (int i = 0; i < xLineData.size(); i++) {
            c.drawText(xLineData.get(i), 6f * i * tenDP + 2.5f * tenDP, getHeight() - tenDP * 0.5f, datePaint);
        }
    }


    /**
     * 绘制线
     *
     * @param c
     */
    public void drawLine(Canvas c) {

        for (int i = 0; i < xLineData.size(); i++) {
            for (int k = 0; k < 7; k++) {
                c.drawLine(0, getHeight() - ((getHeight() - tenDP * 8.5f) * k) / 5 - tenDP * 2.0f, 2f * tenDP * xLineData.size() * 3.5f + 1.5f * tenDP,
                        getHeight() - ((getHeight() - tenDP * 8.5f) * k) / 5 - tenDP * 2.0f, datePaint);
            }
        }
    }

    /**
     * 绘制第一条折线
     */
    public void drawLineOne(Canvas c) {
        float x1, x2, y1, y2;
        for (int i = 0; i < lineOneScore.size() - 1; i++) {
            x1 = 6f * i * tenDP + tenDP * 4;
            y1 = getHeight() - lineOneScore.get(i) * (getHeight() - tenDP * 8.5f) / Collections.max(yLineData) - tenDP * 2.0f;
            x2 = 6f * (i + 1) * tenDP + +tenDP * 4f;
            y2 = getHeight() - lineOneScore.get(i + 1) * (getHeight() - tenDP * 8.5f) / Collections.max(yLineData) - tenDP * 2.0f;
            c.drawLine(x1, y1, x2, y2, lineOnePaint);
            path.lineTo(x1, y1);
            c.drawBitmap(lineOneBitmap,
                    x1 - lineOneBitmap.getWidth() / 2,
                    y1 - lineOneBitmap.getHeight() / 2, null);
            path.lineTo(x2, y2);
            path.lineTo(x2, getHeight());
            path.lineTo(0, getHeight());
            path.close();
            c.drawBitmap(lineOneBitmap,
                    x2 - lineOneBitmap.getWidth() / 2,
                    y2 - lineOneBitmap.getHeight() / 2, null);
        }
    }
    /**
     * 绘制第二条折线
     */
    public void drawLineTwo(Canvas c) {
        float x1, x2, y1, y2;
        for (int i = 0; i < lineTwoScore.size() - 1; i++) {
            x1 = 6f * i * tenDP + tenDP * 4;
            y1 = getHeight() - lineTwoScore.get(i) * (getHeight() - tenDP * 8.5f) / Collections.max(yLineData) - tenDP * 2.0f;
            x2 = 6f * (i + 1) * tenDP + +tenDP * 4f;
            y2 = getHeight() - lineTwoScore.get(i + 1) * (getHeight() - tenDP * 8.5f) / Collections.max(yLineData) - tenDP * 2.0f;
            c.drawLine(x1, y1, x2, y2, lineTwoPaint);
            path.lineTo(x1, y1);
            c.drawBitmap(lineTwoBitmap,
                    x1 - lineTwoBitmap.getWidth() / 2,
                    y1 - lineTwoBitmap.getHeight() / 2, null);
            path.lineTo(x2, y2);
            path.lineTo(x2, getHeight());
            path.lineTo(0, getHeight());
            path.close();
            c.drawBitmap(lineTwoBitmap,
                    x2 - lineTwoBitmap.getWidth() / 2,
                    y2 - lineTwoBitmap.getHeight() / 2, null);
        }
    }


}
