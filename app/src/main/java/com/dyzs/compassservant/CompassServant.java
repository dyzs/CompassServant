package com.dyzs.compassservant;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * @author dyzs
 * Created on 2018/1/9.
 */

public class CompassServant extends View {
    private static final String TAG = CompassServant.class.getSimpleName();
    private Context mCtx;// god of the universal
    private float mWidth, mHeight;
    private float mPadding;// the strength of the triangle point
    private float mSpacing;// the abyss between tick mark and outer circle
    private float[] mCircleCenter = new float[2];// center of the universal
    private float mRadius, mPointerRadius, mTickRadius, mOxygenRadius;
    private float mGalaxyDegree, mAPieceOfDegree, mPointerDegree, mMinDegree, mMaxDegree;
    private RectF mPointerRectF, mTickRectF, mOxygenRectF;
    private float mCircleWidth;// outer circle width
    private float mTickLength;// tick mark pointer length
    private float mTickWidth;// tick mark pointer width
    private float mOxygenWidth;// color gradient width
    private float mMoriSummerWidth;// pointer width
    private Paint mDarkPaint;// outer circle paint
    private Paint mFlamePaint;// tick mark paint
    private Paint mMasterPaint;// color gradient paint
    private Paint mMoriSummerPaint;// pointer paint
    private float mStartAngle;
    private int mDecibel = 119;// tick mark total count
    private int[] mGalaxyColors;
    private float[] mGalaxyPositions;// could't be authorized
    private int mC1, mC2, mC3, mC4;
    private int mCCommander;// command display colors, value limits[2~4]

    public CompassServant(Context context) {
        this(context, null);
    }

    public CompassServant(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CompassServant(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public CompassServant(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
        // startPointerAnim();
    }

    private void init(Context context, AttributeSet attrs) {
        mCtx = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CompassServant);
        mCCommander = ta.getInt(R.styleable.CompassServant_cs_color_commander, 3);
        mC1 = ta.getColor(R.styleable.CompassServant_cs_color1, ContextCompat.getColor(context, R.color.white));
        mC2 = ta.getColor(R.styleable.CompassServant_cs_color2, ContextCompat.getColor(context, R.color.oxygen_green));
        mC3 = ta.getColor(R.styleable.CompassServant_cs_color3, ContextCompat.getColor(context, R.color.cinnabar_red));
        mC4 = ta.getColor(R.styleable.CompassServant_cs_color4, ContextCompat.getColor(context, R.color.pale_blue));
        ta.recycle();
        mPadding = 10f;
        mSpacing = 15f;
        mGalaxyDegree = 280f;
        mAPieceOfDegree = mGalaxyDegree / mDecibel;
        mStartAngle = (360f - mGalaxyDegree) / 2 + 90f;
        mPointerDegree = 280f; mMinDegree = 60f; mMaxDegree = 180f; // def degree value
        mCircleWidth = 20f;
        mTickLength = 80f;
        mTickWidth = 3f;
        mOxygenWidth = 30f;
        mMoriSummerWidth = 10f;

        setGalaxyColors(calcInitColors());

        mDarkPaint = new Paint();
        mDarkPaint.setAntiAlias(true);
        mDarkPaint.setStyle(Paint.Style.STROKE);
        mDarkPaint.setStrokeWidth(mCircleWidth);
        mDarkPaint.setColor(ContextCompat.getColor(context, R.color.tension_grey));

        mFlamePaint = new Paint();
        mFlamePaint.setAntiAlias(true);
        mFlamePaint.setStyle(Paint.Style.STROKE);
        mFlamePaint.setStrokeWidth(mTickWidth);
        mFlamePaint.setColor(ContextCompat.getColor(context, R.color.tension_grey));

        mMasterPaint = new Paint();
        mMasterPaint.setAntiAlias(true);
        mMasterPaint.setStyle(Paint.Style.STROKE);
        mMasterPaint.setStrokeWidth(mOxygenWidth);
        mMasterPaint.setColor(ContextCompat.getColor(context, R.color.girl_pink));

        mMoriSummerPaint = new Paint();
        mMoriSummerPaint.setAntiAlias(true);
        mMoriSummerPaint.setStyle(Paint.Style.STROKE);
        mMoriSummerPaint.setStrokeWidth(mMoriSummerWidth);
        mMoriSummerPaint.setColor(ContextCompat.getColor(context, R.color.alice_blue));
    }

    private int[] calcInitColors() {
        mCCommander = mCCommander % 5;
        if (mCCommander < 2) {mCCommander = 2;}
        int[] retColors = new int[mCCommander], colors = new int[]{mC1, mC2, mC3, mC4};
        for (int i = 0; i < mCCommander; i++) {
            retColors[i] = colors[i];
        }
        return retColors;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();mHeight = getMeasuredHeight();
        if (mWidth >= mHeight) {
            mRadius = mHeight / 2 - mPadding;
        } else {
            mRadius = mWidth / 2 - mPadding;
        }
        mCircleCenter[0] = mWidth / 2; mCircleCenter[1] = mHeight / 2;

        mPointerRadius = mRadius - mCircleWidth / 2;
        float l, t, r, b;
        l = mCircleCenter[0] - mPointerRadius;
        t = mCircleCenter[1] - mPointerRadius;
        r = mCircleCenter[0] + mPointerRadius;
        b = mCircleCenter[1] + mPointerRadius;
        mPointerRectF = new RectF(l, t, r, b);

        mTickRadius = mRadius - mCircleWidth - mTickLength / 2 - mSpacing;
        l = mCircleCenter[0] - mTickRadius;
        t = mCircleCenter[1] - mTickRadius;
        r = mCircleCenter[0] + mTickRadius;
        b = mCircleCenter[1] + mTickRadius;
        mTickRectF = new RectF(l, t, r, b);

        mOxygenRadius = mRadius - mCircleWidth - mTickLength - mOxygenWidth / 2 - mSpacing * 2;
        l = mCircleCenter[0] - mOxygenRadius;
        t = mCircleCenter[1] - mOxygenRadius;
        r = mCircleCenter[0] + mOxygenRadius;
        b = mCircleCenter[1] + mOxygenRadius;
        mOxygenRectF = new RectF(l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDarkFlameMaster(canvas);
        drawGalaxy(canvas);
    }

    private void drawDarkFlameMaster(Canvas canvas) {
        /* draw circle */
        canvas.drawCircle(mCircleCenter[0], mCircleCenter[1], mPointerRadius, mDarkPaint);

        /* draw tick mark, triangle mark and color pointer */
        int dBPointer = (int) (mPointerDegree * mDecibel / mGalaxyDegree);
        for (int i = 0; i <= mDecibel; i++) {
            canvas.save();
            float rotateDegree;
            rotateDegree = mStartAngle + 90 + mAPieceOfDegree * i;
            canvas.rotate(rotateDegree, mCircleCenter[0], mCircleCenter[1]);
            if (i <= dBPointer) {
                mFlamePaint.setColor(getPointerColor(i));//ContextCompat.getColor(mCtx, R.color.blair_grey));
                canvas.drawLine(
                        mCircleCenter[0],
                        mTickRectF.top - mTickLength / 2,
                        mCircleCenter[0],
                        mTickRectF.top + mTickLength / 2,
                        mFlamePaint);
                if (i == dBPointer) {
                    mMoriSummerPaint.setColor(getPointerColor(i));
                    canvas.drawLine(
                            mCircleCenter[0],
                            mPadding,
                            mCircleCenter[0],
                            mTickRectF.top + mTickLength / 2,
                            mMoriSummerPaint);
                }
            } else {
                mFlamePaint.setColor(ContextCompat.getColor(mCtx, R.color.tension_grey));
                canvas.drawLine(
                        mCircleCenter[0],
                        mTickRectF.top - mTickLength / 2,
                        mCircleCenter[0],
                        mTickRectF.top + mTickLength / 2,
                        mFlamePaint);
            }
            canvas.restore();
        }
    }

    private void drawGalaxy(Canvas canvas) {
        /* draw colorful gradient */
        SweepGradient sweepGradient = new SweepGradient(
                mCircleCenter[0],
                mCircleCenter[1],
                mGalaxyColors,
                mGalaxyPositions);
        mMasterPaint.setShader(sweepGradient);
        Path path = new Path();
        canvas.save();
        canvas.rotate(mStartAngle, mCircleCenter[0], mCircleCenter[1]);
        path.addArc(mOxygenRectF, 0, mGalaxyDegree);
        canvas.drawPath(path, mMasterPaint);
        canvas.restore();
    }

    public void setPointerDecibel(int value) {
        value = value % mDecibel;
        float degree = mGalaxyDegree * value / mDecibel;
        this.mLastValue = mPointerDegree;
        this.mPointerDegree = degree;
        System.out.println("======= mLastValue:" + value);
        System.out.println("======= mPointerDegree:" + value);
        startPointerAnim();
        // invalidate();
    }

    /**
     * SweepGradient 颜色值对应的1f：360f(圆的角度)
     * pointerDegreeRate 计算解释：pointerTick/totalPointer对应了颜色值的渐变，
     * 因为当前圆可以设置为存在缺口的圆弧，所以按照比例同成于圆弧 galaxyDegree/360f（圆的角度）
     * 颜色区间计算：通过上述得到的float值，去校验当前指针在 positions 的哪个区间，然后换算当前颜色值RGB
     */
    public int getPointerColor(int pointerTick) {
        if (mGalaxyColors.length == 1) {
            return mGalaxyColors[0];
        }
        float degreeRate = mGalaxyDegree / 360f;
        float pointerDegreeRate = pointerTick * 1f / (mDecibel + 1) * degreeRate;
        int resSColor = ContextCompat.getColor(mCtx, R.color.white);
        int resEColor = ContextCompat.getColor(mCtx, R.color.oxygen_green);
        float rangeColorRate = 0f;
        for (int i = 0 ; i < mGalaxyPositions.length; i++) {
            if (i == 0) {
                resSColor = mGalaxyColors[0];
                resEColor = mGalaxyColors[1];
                continue;
            }
            if (pointerDegreeRate < mGalaxyPositions[i]) {
                float s = mGalaxyPositions[i-1];
                float e = mGalaxyPositions[i];
                rangeColorRate = (pointerDegreeRate - s) / (e - s);
                resSColor = mGalaxyColors[i-1];
                resEColor = mGalaxyColors[i];
                break;
            }
        }
        int sc = resSColor;
        int ec = resEColor;
        int rS = Color.red(sc);
        int gS = Color.green(sc);
        int bS = Color.blue(sc);
        int rE = Color.red(ec);
        int gE = Color.green(ec);
        int bE = Color.blue(ec);
        int r = (int) (rS + (rE - rS) * 1f * rangeColorRate);
        int g = (int) (gS + (gE - gS) * 1f * rangeColorRate);
        int b = (int) (bS + (bE - bS) * 1f * rangeColorRate);
        return Color.argb(255, r, g, b);
    }

    private ValueAnimator mAnimator;
    private float mLastValue = 0f;
    private long mDuration;
    private void startPointerAnim() {
        mDuration = (long) (10 * Math.abs(mPointerDegree - mLastValue));
        mAnimator = ValueAnimator.ofFloat(mLastValue, mPointerDegree);
        mAnimator.setDuration(mDuration);
        mAnimator.setInterpolator(new DecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mPointerDegree = value;
                postInvalidate();
            }
        });
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null) {
                    listener.startTension();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.start();
    }

    private ServantListener listener;
    public void setServantListener(ServantListener listener) {
        this.listener = listener;
    }
    public interface ServantListener {
        void startTension();
    }

    public int[] getGalaxyColors() {
        return mGalaxyColors;
    }

    /**
     * 设置颜色的时候同时计算 positions
     * ！不处理任何颜色值设置异常
     */
    public void setGalaxyColors(@Nullable int[] colors) {
        if (colors == null) {
            colors = new int[] {
                    ContextCompat.getColor(mCtx, R.color.white),
                    ContextCompat.getColor(mCtx, R.color.oxygen_green),
                    ContextCompat.getColor(mCtx, R.color.cinnabar_red)
            };
        }
        this.mGalaxyColors = colors;
        setPositions(null);
        invalidate();
    }

    /* not allow use */
    private float[] getGalaxyPositions() {
        return mGalaxyPositions;
    }

    /* not allow use */
    private void setPositions(@Nullable float[] positions) {
        float degreeRate = mGalaxyDegree / 360f;
        if (positions == null) {// set positions average allocation
            positions = new float[mGalaxyColors.length];
            for (int i = 0; i < positions.length; i++) {
                positions[i] = i * degreeRate / (positions.length - 1);
            }
        } else {// use degree rate while reset positions
            for (int i = 0; i < positions.length; i++) {
                positions[i] = positions[i] * degreeRate;
            }
        }
        this.mGalaxyPositions = positions;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
