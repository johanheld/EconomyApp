package com.example.johan.economyapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/** This is a customized TextView which can be used to make a text sound more friendly or more angry
 *
 * Created by johan on 2017-09-20.
 */

public class ExtendedTextView extends android.support.v7.widget.AppCompatTextView
{
    public ExtendedTextView(Context context)
    {
        super(context);
    }

    public ExtendedTextView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ExtendedTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type)
    {
        super.setText(text + "!", type);
    }
}
