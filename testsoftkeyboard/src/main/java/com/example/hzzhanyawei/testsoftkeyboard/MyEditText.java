package com.example.hzzhanyawei.testsoftkeyboard;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by hzzhanyawei on 2015/8/26.
 * Email hzzhanyawei@corp.netease.com
 */
public class MyEditText extends TextView {

    private String inputText = "";
    InputMethodManager inputMethodManager=null;


    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);

        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if(focused){
            Log.d("ZYW", "focused !!");
            String test = "2222222222222222";
            setText(test);
            inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_FORCED);
        }

        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }


    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new MyInputConnection(this, false);
    }

    class MyInputConnection extends BaseInputConnection {

        public MyInputConnection(View targetView, boolean fullEditor) {
            super(targetView, fullEditor);
        }

        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            inputText  = inputText + (String)text;
            return true;
        }
    }
}
