package cc.a5156.xdkp.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.a5156.xdkp.R;


/**
 * Created by Administrator on 2017/6/6.
 */

public class BaseItem extends LinearLayout {
    private Context context;
    private TextView tvContent;

    public BaseItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View.inflate(context, R.layout.base_item, this);
        tvContent = findViewById(R.id.tvContent);
        initAttrs(attrs);

    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BaseItem);
        String text = ta.getString(R.styleable.BaseItem_text);
        if (text != null) {
            tvContent.setText(text);
        }
        ta.recycle();
    }
}
