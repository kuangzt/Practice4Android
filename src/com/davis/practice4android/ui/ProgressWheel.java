package com.davis.practice4android.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.davis.practice4android.R;

public class ProgressWheel extends View{

    private int layout_height = 0;
    private int layout_width = 0;
	private int mProgressBarWidth = 20;
	private int mProgressBarColor = 0xAA000000;
	private int mRoundColor = 0xAA000000;
	private int mRingColor = 0xAA000000;
	private int mRingWidth = 20;
	private int textColor = 0xFF000000;
	private int textSize = 20;
	private String text;
	
	
    //Padding (with defaults)
    private int paddingTop = 5;
    private int paddingBottom = 5;
    private int paddingLeft = 5;
    private int paddingRight = 5;
    
    private RectF mRoundBounds;
    private RectF mRectBounds;
    private RectF mVirtualBounds;
    
    
    
    private Paint mCirclePaint = new Paint();
    private Paint mRingPaint = new Paint();
    private Paint mProgressBarPaint = new Paint();
    private Paint textPaint = new Paint();
    
    private int progress = 0;
    private int barAngle = 30;
    boolean isSpinning = false;
    private int spinSpeed = 2;
    private int delayMillis = 200;
    private static final int UPDATE = 1;
    private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int what = msg.what;
			if(UPDATE ==what){
				if(isSpinning){
	                progress += spinSpeed;
	                if (progress > 360) {
	                    progress = 0;
	                }
	                sendEmptyMessageDelayed(UPDATE, delayMillis);
				}
			}
			invalidate();
		}
    	
    };
	public ProgressWheel(Context context, AttributeSet attrs) {
		super(context, attrs);
		parseAttributes(context.obtainStyledAttributes(attrs,
                R.styleable.ProgressWheel));
	}

	
	private void parseAttributes(TypedArray a) {
        textSize = (int) a.getDimension(R.styleable.ProgressWheel_textSize,
                textSize);

        textColor = (int) a.getColor(R.styleable.ProgressWheel_textColor,
                textColor);

        //if the text is empty , so ignore it
        if (a.hasValue(R.styleable.ProgressWheel_text)) {
            setText(a.getString(R.styleable.ProgressWheel_text));
        }
		mProgressBarWidth = (int) a.getDimension(R.styleable.ProgressWheel_progressBarWidth,
				mProgressBarWidth);
		mProgressBarColor = (int) a.getColor(R.styleable.ProgressWheel_progressBarColor,
        		mProgressBarColor);
		mRoundColor = a.getColor(R.styleable.ProgressWheel_roundColor, mRoundColor);
		mRingColor = (int) a.getColor(R.styleable.ProgressWheel_ringColor,mRingColor);
		mRingWidth = (int)a.getDimension(R.styleable.ProgressWheel_ringWidth, mRingWidth);
        
        spinSpeed = (int) a.getDimension(R.styleable.ProgressWheel_spinSpeed,
                spinSpeed);

        delayMillis = a.getInteger(R.styleable.ProgressWheel_delayMillis,
                delayMillis);
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        
        barAngle = a.getInteger(R.styleable.ProgressWheel_barAngle, barAngle);
		a.recycle();
    }
	
    public void setText(String text) {
        this.text = text;
    }
	
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	// The first thing that happen is that we call the superclass 
    	// implementation of onMeasure. The reason for that is that measuring 
    	// can be quite a complex process and calling the super method is a 
    	// convenient way to get most of this complexity handled.
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    	// We can’t use getWidth() or getHight() here. During the measuring 
    	// pass the view has not gotten its final size yet (this happens first 
    	// at the start of the layout pass) so we have to use getMeasuredWidth() 
    	// and getMeasuredHeight().
        int size = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();
        
        // Finally we have some simple logic that calculates the size of the view 
        // and calls setMeasuredDimension() to set that size.
        // Before we compare the width and height of the view, we remove the padding, 
        // and when we set the dimension we add it back again. Now the actual content 
        // of the view will be square, but, depending on the padding, the total dimensions 
        // of the view might not be.
        if (widthWithoutPadding > heigthWithoutPadding) {
            size = heigthWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }
        
        // If you override onMeasure() you have to call setMeasuredDimension(). 
        // This is how you report back the measured size.  If you don’t call
        // setMeasuredDimension() the parent will throw an exception and your 
        // application will crash.        
        // We are calling the onMeasure() method of the superclass so we don’t 
        // actually need to call setMeasuredDimension() since that takes care 
        // of that. However, the purpose with overriding onMeasure() was to 
        // change the default behaviour and to do that we need to call 
        // setMeasuredDimension() with our own values.
        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    /**
     * Use onSizeChanged instead of onAttachedToWindow to get the dimensions of the view,
     * because this method is called after measuring the dimensions of MATCH_PARENT & WRAP_CONTENT.
     * Use this dimensions to setup the bounds and paints.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Share the dimensions
        layout_width = w;
        layout_height = h;

        setupBounds();
        setupPaints();
        invalidate();
    }
    
    /**
     * Set the bounds of the component
     */
    private void setupBounds() {
        // Width should equal to Height, find the min value to steup the circle
        int minValue = Math.min(layout_width, layout_height);

        // Calc the Offset if needed
        int xOffset = layout_width - minValue;
        int yOffset = layout_height - minValue;

        // Add the offset
        paddingTop = this.getPaddingTop() + (yOffset / 2);
        paddingBottom = this.getPaddingBottom() + (yOffset / 2);
        paddingLeft = this.getPaddingLeft() + (xOffset / 2);
        paddingRight = this.getPaddingRight() + (xOffset / 2);

        int width = getWidth(); //this.getLayoutParams().width;
        int height = getHeight(); //this.getLayoutParams().height;

        mRectBounds = new RectF(paddingLeft,
                paddingTop,
                width - paddingRight,
                height - paddingBottom);
        mRoundBounds = new RectF(paddingLeft + mProgressBarWidth,
                paddingTop + mProgressBarWidth,
                width - paddingRight - mProgressBarWidth,
                height - paddingBottom - mProgressBarWidth);
        
        mVirtualBounds = new RectF(paddingLeft + mProgressBarWidth/2,
                paddingTop + mProgressBarWidth/2,
                width - paddingRight - mProgressBarWidth/2,
                height - paddingBottom - mProgressBarWidth/2);
    }
    
    private void setupPaints() {
        mCirclePaint.setColor(mRoundColor);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Style.FILL);
        mCirclePaint.setStrokeWidth(0);
        
        mRingPaint.setColor(mRingColor);
        mRingPaint.setAntiAlias(true);
        mRingPaint.setStyle(Style.FILL);
        mCirclePaint.setStrokeWidth(0);
        
        mProgressBarPaint.setColor(mProgressBarColor);
        mProgressBarPaint.setAntiAlias(true);
        mProgressBarPaint.setStyle(Style.STROKE);
        mProgressBarPaint.setStrokeWidth(mProgressBarWidth);
        
        textPaint.setColor(textColor);
        textPaint.setStyle(Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
    }


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawArc(mRectBounds, 360, 360, false, mRingPaint);
		canvas.drawArc(mRoundBounds, 360, 360, false, mCirclePaint);
		if(isSpinning){
			canvas.drawArc(mVirtualBounds, progress - 90, barAngle, false, mProgressBarPaint);
		}else{
			canvas.drawArc(mVirtualBounds, -90, progress, false, mProgressBarPaint);
		}
		
        float textHeight = textPaint.descent() - textPaint.ascent();
        float verticalTextOffset = (textHeight / 2) - textPaint.descent();

        float horizontalTextOffset = textPaint.measureText(text) / 2;
        canvas.drawText(text, this.getWidth() / 2 - horizontalTextOffset,
                this.getHeight() / 2 + verticalTextOffset, textPaint);
	}


	public int getProgress() {
		return progress;
	}


	public void setProgress(int progress) {
		isSpinning = false;
		this.progress = progress;
		handler.sendEmptyMessage(UPDATE);
	}


    /**
     * Turn off spin mode
     */
    public void stopSpinning() {
        isSpinning = false;
        progress = 0;
        handler.removeMessages(UPDATE);
    }


    /**
     * Puts the view on spin mode
     */
    public void spin() {
        isSpinning = true;
        handler.sendEmptyMessage(UPDATE);
    }


	public int getSpinSpeed() {
		return spinSpeed;
	}


	public void setSpinSpeed(int spinSpeed) {
		this.spinSpeed = spinSpeed;
	}


	public int getDelayMillis() {
		return delayMillis;
	}


	public void setDelayMillis(int delayMillis) {
		this.delayMillis = delayMillis;
	}
    
}
