package com.mapview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * 自定义 地图 控件
 * Created by user on 2016/3/3.
 */
public class MapView extends RelativeLayout {

    private static final String TAG = "MyMapView";

    public static final String ICON_POINT_RED = "icon_point_red.png";

    // 显示 地图 底图和线 的 控件
    private BaseMapAndLines baseMapAndLines;

    // 地图 原点 左上角
    private View originalPointView;

    // 地图上 标记 的 点
    private ArrayList<MapPointWithTitleView> mapPoints = new ArrayList<>();

    // 首次 放大缩小的 倍数
    private float firstScale;

    private Handler mHandler = new Handler();

    private int mMapBitmapWith;
    private int mMapBitmapHeight;

    public MapView(Context context) {
        this(context, null);
    }

    public MapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // 初始化
    private void init() {
        baseMapAndLines = new BaseMapAndLines(getContext());
        LayoutParams mapLineViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mapLineViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        baseMapAndLines.setLayoutParams(mapLineViewParams);
        baseMapAndLines.setBackgroundColor(Color.parseColor("#ff0000"));
        baseMapAndLines.setScaleType(ImageView.ScaleType.MATRIX);
        addView(baseMapAndLines);

        originalPointView = new MapPointWithTitleView(getContext());
        Log.e(TAG, " originalPointView ------>"
                + " getX() =  " + originalPointView.getX()
                + " getY() = " + originalPointView.getY());

        // 设置 触摸 监听
        setOnTouchListener(new MyTouchListener());
    }

    /**
     * 加多个 点
     *
     * @param list
     */
    public void setMapPoints(ArrayList<MapPointWithTitleView> list) {
        clearMapPoints();
        this.mapPoints.addAll(list);
    }

    /**
     * 加 多条 线
     *
     * @param mapLineCoords
     */
    public void setMapLines(ArrayList<BaseMapAndLines.MapLineCoord> mapLineCoords) {
        this.baseMapAndLines.clearLines();
        this.baseMapAndLines.addLines(mapLineCoords);
    }

    /**
     * 清空 点
     */
    public void clearMapPoints() {
        for (int i = 0; i < mapPoints.size(); i++) {
            this.removeView(mapPoints.get(i));
        }
        this.mapPoints.clear();
    }

    /**
     * 清空 线
     */
    public void clearMapLines() {
        this.baseMapAndLines.clearLines();
    }

    /**
     * 添加一个点 并显示
     */
    public void addMapPoint(MapPointWithTitleView mapPoint) {
        // 需要 转化坐标点
        mapPoint.setFirstY(mMapBitmapHeight - mapPoint.getFirstY());

        float showX = (float) (mapPoint.getFirstX() * firstScale + originalPointView.getX());
        float showY = (float) (mapPoint.getFirstY() * firstScale + originalPointView.getY());
        Log.e(TAG, "addMapPoint------>"
                + "\n originalPointView.getX() = " + originalPointView.getX()
                + " originalPointView.getY() = " + originalPointView.getY()
                + "\n showX = " + showX
                + " showY = " + showY);

        mapPoint.setFirstXShow(showX);
        mapPoint.setFirstYShow(showY);
        addView(mapPoint);

        mapPoints.add(mapPoint);
    }


    /**
     * 添加 线 的 坐标
     * 至少 两个 ，且 这两个 坐标不重复 ，线才会显示
     *
     * @param x
     * @param y
     */
    public void addMapLine(float x, float y) {
        BaseMapAndLines.MapLineCoord mapLineCoord = baseMapAndLines.new MapLineCoord(x, y,
                x * firstScale + originalPointView.getX(), y * firstScale + originalPointView.getY());
        baseMapAndLines.addLine(mapLineCoord);
    }

    /**
     * 设置 地图 底图 并显示
     *
     * @param bitmap
     */
    public void setMapBitmap(Bitmap bitmap) {
        baseMapAndLines.setImageBitmap(bitmap);
        mMapBitmapWith = bitmap.getWidth();
        mMapBitmapHeight = bitmap.getHeight();

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                float widthScale = getWidth() * 1.0f / mMapBitmapWith;
                float heightScale = getHeight() * 1.0f / mMapBitmapHeight;
                firstScale = Math.min(widthScale, heightScale);
                Log.e(TAG, " firstScale = " + firstScale
                        + " widthScale = " + widthScale
                        + " heightScale = " + heightScale
                        + " getLeft() = " + getLeft()
                        + " getRight() = " + getRight()
                        + " getWidth() = " + getWidth());

                Matrix matrix = new Matrix();
                matrix.preScale(firstScale, firstScale);
                baseMapAndLines.setImageMatrix(matrix);

                originalPointView.setX(originalPointView.getX() * firstScale);
                originalPointView.setY(originalPointView.getY() * firstScale);
            }
        });
    }

    /**
     * 根据 当前 缩放 比例
     * 移动 点 的 位置
     * 用于 放大缩小 按钮
     */
    public void moveMapPoints() {
        for (int i = 0; i < mapPoints.size(); i++) {
            MapPointWithTitleView point = mapPoints.get(i);
            // 设置 点 的 初始位置
            point.setFirstXShow((float) (point.getFirstX() * firstScale));
            point.setFirstYShow((float) (point.getFirstY() * firstScale));
        }
    }

    /**
     * 根据 当前 缩放 比例
     * 移动 线 的 位置
     * 用于 放大缩小 按钮
     */
    public void moveMapLines() {
        for (int i = 0; i < baseMapAndLines.getLineSize(); i++) {
            BaseMapAndLines.MapLineCoord line = baseMapAndLines.getLine(i);
            line.setViewX(line.getFirstX() * firstScale);
            line.setViewY(line.getFirstY() * firstScale);
        }
        baseMapAndLines.invalidate();
    }

    /**
     * 得到 地图 点 的集合
     *
     * @return
     */
    public ArrayList<MapPointWithTitleView> getMapPoints() {
        return mapPoints;
    }

    /**
     * 得到 地图 线 的 集合
     *
     * @return
     */
    public ArrayList<BaseMapAndLines.MapLineCoord> getMapLines() {
        return baseMapAndLines.getMapLineCoords();
    }

    public void addCenterPoint() {
//        float fingerX = getWidth() / 2 - getX();
        Bitmap pointBimtap = ViewUtil.getBitmapFormAsset(getContext(), ICON_POINT_RED);
        float fingerX = (getLeft() + getRight()) / 2 - getX() - pointBimtap.getWidth() / 2;
        float fingerY = (getTop() + getBottom()) / 2 - getY() - pointBimtap.getHeight() / 2;
        float originalPointViewX = originalPointView.getX();
        float originalPointViewY = originalPointView.getY();

        Log.e(TAG, " addCenterPoint --------->"
                + "\n originalPointViewX = " + originalPointViewX
                + "\t originalPointViewY = " + originalPointViewY
                + "\n getX() = " + getX()
                + "\t getY() = " + getY()
                + "\n pointBimtap.getWidth() = " + pointBimtap.getWidth()
                + "\t pointBimtap.getHeight() = " + pointBimtap.getHeight());

        float x = (fingerX - originalPointViewX) / firstScale;
        float y = (fingerY - originalPointViewY) / firstScale;

        // 回调出去的点 坐标处理
        Log.e(TAG, " addCenterPoint ----------> x = " + x + " y = " + (mMapBitmapHeight - y));

        MapPointWithTitleView mapPoint = new MapPointWithTitleView(getContext(), x, y, pointBimtap);
        float showX = (float) (mapPoint.getFirstX() * firstScale + originalPointView.getX());
        float showY = (float) (mapPoint.getFirstY() * firstScale + originalPointView.getY());
        Log.e(TAG, "addMapPoint------>"
                + "\n originalPointView.getX() = " + originalPointView.getX()
                + " originalPointView.getY() = " + originalPointView.getY()
                + "\n showX = " + showX
                + " showY = " + showY);
        mapPoint.setFirstXShow(showX);
        mapPoint.setFirstYShow(showY);
        addView(mapPoint);

        mapPoints.add(mapPoint);
    }

    /**
     * 地图 移动 放大 监听
     */
    private class MyTouchListener implements OnTouchListener {
        /**
         * 记录是拖拉照片模式还是放大缩小照片模式
         */
        private int mode = 0;// 初始状态
        /**
         * 拖拉照片模式
         */
        private static final int MODE_DRAG = 1;
        /**
         * 放大缩小照片模式
         */
        private static final int MODE_ZOOM = 2;

        /**
         * 用于记录开始时候的坐标位置
         */
        private PointF startPoint = new PointF();
        /**
         * 用于记录拖拉图片移动的坐标位置
         */
        private Matrix matrix = new Matrix();
        /**
         * 用于记录图片要进行拖拉时候的坐标位置
         */
        private Matrix currentMatrix = new Matrix();

        /**
         * 两个手指的开始距离
         */
        private float startDis;
        /**
         * 两个手指的中间点
         */
        private PointF midPoint;


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            /** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                // 手指压下屏幕
                case MotionEvent.ACTION_DOWN: {
                    mode = MODE_DRAG;
                    // 记录ImageView当前的移动位置
                    currentMatrix.set(baseMapAndLines.getImageMatrix());
                    startPoint.set(event.getX(), event.getY());

                    Log.e("test", " onTouch ------>"
                            + " event.getX() = " + event.getX()
                            + " event.getY() = " + event.getY()
                            + " getX() = " + getX()
                            + " getY() = " + getY());
                    break;
                }
                // 手指在屏幕上移动，改事件会被不断触发
                case MotionEvent.ACTION_MOVE: {
                    // 拖拉图片
                    if (mode == MODE_DRAG) {
                        float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
                        float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
                        // 在没有移动之前的位置上进行移动
                        matrix.set(currentMatrix);
                        matrix.postTranslate(dx, dy);
                    } else if (mode == MODE_ZOOM) {
                        // 放大缩小图片
                        float endDis = distance(event);// 结束距离
                        if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                            float scale = endDis / startDis;// 得到缩放倍数
                            matrix.set(currentMatrix);
                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                        }
                    }
                    break;
                }
                // 手指离开屏幕
                case MotionEvent.ACTION_UP: {
                    //如果 按下 抬起 时间 大于 2s 则是 长按 事件
                    // 当触点离开屏幕，但是屏幕上还有触点(手指)
                }
                case MotionEvent.ACTION_POINTER_UP: {
                    mode = 0;
                    break;
                }
                // 当屏幕上已经有触点(手指)，再有一个触点压下屏幕
                case MotionEvent.ACTION_POINTER_DOWN: {
                    mode = MODE_ZOOM;
                    /** 计算两个手指间的距离 */
                    startDis = distance(event);
                    /** 计算两个手指间的中间点 */
                    if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                        midPoint = mid(event);
                        //记录当前ImageView的缩放倍数
                        currentMatrix.set(baseMapAndLines.getImageMatrix());
                    }
                    break;
                }
            }

            /**
             * 如果 此次 触摸事件  是  移动，放大事件
             * 则 改变 地图 和 坐标点的位置
             */
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE:
                    // 移动 地图
                    baseMapAndLines.setImageMatrix(matrix);

                    float[] matrixValues = new float[9];
                    matrix.getValues(matrixValues);

                    // 地图原点移动
                    originalPointView.setX((float) (0 * matrixValues[0] + matrixValues[2]));
                    originalPointView.setY((float) (0 * matrixValues[4] + matrixValues[5]));


                    Log.i(TAG, originalPointView.getX() + "===originalPointView====" + originalPointView.getY());

                    firstScale = matrixValues[0];

                    // 移动 点
                    for (int i = 0; i < mapPoints.size(); i++) {
                        double scaleX = mapPoints.get(i).getFirstX() * matrixValues[0];
                        double scaleY = mapPoints.get(i).getFirstY() * matrixValues[4];
                        mapPoints.get(i).setFirstXShow((float) (scaleX + matrixValues[2]));
                        mapPoints.get(i).setFirstYShow((float) (scaleY + matrixValues[5]));
                    }

                    // 移动 线
                    for (int i = 0; i < baseMapAndLines.getLineSize(); i++) {
                        float v1 = baseMapAndLines.getLine(i).getFirstX() * matrixValues[0] + matrixValues[2];
                        float v2 = baseMapAndLines.getLine(i).getFirstY() * matrixValues[4] + matrixValues[5];
                        baseMapAndLines.getLine(i).setViewX(v1);
                        baseMapAndLines.getLine(i).setViewY(v2);
                    }
                    baseMapAndLines.invalidate();


                    /**
                     *如果 外层为ScrollView 此句代码是解决
                     * 地图的移动 和 ScrollView 的滚动冲突的
                     * 当触摸事件在地图范围内时，ScrollView 滚动事件无法响应
                     * 当触摸事件在 地图范围外时，ScrollView可以滚动
                     */
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
            }
            // 如果 设置 了 长按 监听 则 传递 事件
            // 否则 自己 消费 该 事件
            return true;
        }


        /**
         * 计算两个手指间的距离
         */
        private float distance(MotionEvent event) {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            /** 使用勾股定理返回两点之间的距离 */
            return (float) Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * 计算两个手指间的中间点
         */
        private PointF mid(MotionEvent event) {
            float midX = (event.getX(1) + event.getX(0)) / 2;
            float midY = (event.getY(1) + event.getY(0)) / 2;
            return new PointF(midX, midY);
        }
    }

}
