package add.util;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.AttributeSet;
import android.content.res.TypedArray;
import com.example.administrator.myapplication2.R;//2015-10-21

/**
 * Created by Administrator on 2015/10/21.
 */
    public class ButtonTextImg extends LinearLayout {


        private ImageView mButtonImage = null;

        private TextView mButtonText = null;

        private int textSize = 18;

    public ButtonTextImg(Context context, AttributeSet attrs) {super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ButtonTextImg);

        try {
            mButtonImage = new ImageView(context);
            mButtonText = new TextView(context);


            int imageSrcId = a.getResourceId(R.styleable.ButtonTextImg_myImg,R.drawable.ic_collect);
            int textColor = a.getColor(R.styleable.ButtonTextImg_myColor, 000000);
           String  text_str=a.getString(R.styleable.ButtonTextImg_myText);
            String HorV_str=a.getString(R.styleable.ButtonTextImg_myHorV) ;
            textSize = (int)a.getDimension(R.styleable.ButtonTextImg_myTextSize, 28);
              setImageResource(imageSrcId);
            setText(text_str);

            setTextSize(textSize);
            setImgScaleType(ScaleType.CENTER_INSIDE);

            LayoutParams layoutParams =new LayoutParams(LayoutParams.MATCH_PARENT, 0, 0.5f);
             if(HorV_str.trim().equals("H")) {

                layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 0.5f);//
            }

             setImgLayoutParams(layoutParams);
              setTextLayoutParams(layoutParams);
            setTextGravity(Gravity.CENTER);

             setTextColor(textColor);
             setClickable(true);
            setFocusable(true);


            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT, 1);

            params.gravity = Gravity.CENTER;
              setFatherViewLayoutParams(params);
             //setFatherViewBgResource(R.drawable.my_img_btn_default);//2015-10-21



            if(HorV_str.trim().equals("H")) {
             setFatherViewOrientation(LinearLayout.HORIZONTAL);
             }
           else
             {
               setFatherViewOrientation(LinearLayout.VERTICAL);
            }



            addView(mButtonImage);

            addView(mButtonText);

        }
            finally {

                a.recycle();

            }



        }


        public ButtonTextImg(Context context)
        {
            super(context);
        }
        public ButtonTextImg(Context context, int... intArray) {

            super(context);
           // Init instance
            mButtonImage = new ImageView(context);
            mButtonText = new TextView(context);
            int len = intArray.length;
     if (len >= 1) {
 setImageResource(intArray[0]);
  } if (len >= 2) {
 setText(intArray[1]); }
  if (len >= 3) {
 textSize = intArray[2];
  }
  /** Set Child View(ImageView/TextView) properties */

          setTextSize(textSize);
            setImgScaleType(ScaleType.CENTER_INSIDE);
  LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 0.5f);
    setImgLayoutParams(layoutParams);
   setTextLayoutParams(layoutParams);
  setTextGravity(Gravity.CENTER);
   setTextColor(0xFFFFFFFF);



            /** Set Father View(LinearLayout) properties */
          setClickable(true);
          setFocusable(true);


            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
             LayoutParams.MATCH_PARENT, 1);

          params.gravity = Gravity.CENTER;
             setFatherViewLayoutParams(params);
           //setFatherViewBgResource(R.drawable.my_img_btn_default);//2015-10-21
          setFatherViewOrientation(LinearLayout.VERTICAL);

          addView(mButtonImage);

            addView(mButtonText);

        }

  public void setImageResource(int resId) {
  mButtonImage.setImageResource(resId);
  }
   public void setText(int resId) {
   mButtonText.setText(resId);
   }

  public void setText(CharSequence buttonText) {
  mButtonText.setText(buttonText);
  }

  public void setTextColor(int color) {

            mButtonText.setTextColor(color);

        }

        public void setTextSize(int textSize) {

            mButtonText.setTextSize(textSize);

        }



        public void setImgLayoutParams(LayoutParams layoutParams) {


            mButtonImage.setLayoutParams(layoutParams);

        }


        public void setImgScaleType(ScaleType scaleType) {


            mButtonImage.setScaleType(scaleType);

        }


        public void setTextLayoutParams(LayoutParams layoutParams) {


            mButtonText.setLayoutParams(layoutParams);

        }





        public void setTextGravity(int gravity) {


            mButtonText.setGravity(gravity);

        }





        public void setFatherViewLayoutParams(LayoutParams params) {


            super.setLayoutParams(params);

        }



        public void setFatherViewBgResource(int resId) {


            super.setBackgroundResource(resId);

        }





        public void setFatherViewOrientation(int orientation) {


            super.setOrientation(orientation);

        }


    }