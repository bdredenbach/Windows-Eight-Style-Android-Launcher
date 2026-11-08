package com.nthexperience.desktop;
import android.app.*;import android.content.*;import android.graphics.Color;import android.view.*;import android.widget.*;import java.util.*;
/** Shared touch-sized flat controls for the Eight local apps. */
final class EightAppUi {
    static int dp(android.content.Context c,int n){return Aero.dp(c,n);}
    static TextView text(Context c,String value,int size){return Aero.text(c,value,size,0xff282828);}
    static TextView button(Context c,String value,Runnable action){TextView v=text(c,value,15);v.setMinHeight(dp(c,46));v.setGravity(Gravity.CENTER_VERTICAL);v.setPadding(dp(c,12),dp(c,6),dp(c,12),dp(c,6));v.setContentDescription(value);v.setFocusable(true);v.setBackground(TenStyle.hover(c,Color.TRANSPARENT));v.setOnClickListener(w->action.run());return v;}
    static LinearLayout bar(Context c,LinearLayout parent,String title,int color){LinearLayout bar=new LinearLayout(c);bar.setGravity(Gravity.CENTER_VERTICAL);bar.setPadding(dp(c,18),dp(c,8),dp(c,12),dp(c,8));bar.setBackgroundColor(color);TextView h=Aero.text(c,title,30,Color.WHITE);h.setMaxLines(1);h.setEllipsize(android.text.TextUtils.TruncateAt.END);bar.addView(h,new LinearLayout.LayoutParams(0,dp(c,52),1));parent.addView(bar,new LinearLayout.LayoutParams(-1,-2));return bar;}
    static LinearLayout commands(Context c,LinearLayout parent){HorizontalScrollView scroll=new HorizontalScrollView(c);scroll.setHorizontalScrollBarEnabled(false);LinearLayout row=new LinearLayout(c);scroll.addView(row);parent.addView(scroll,new LinearLayout.LayoutParams(-1,dp(c,48)));return row;}
    static EditText input(Context c,LinearLayout parent,String hint,boolean single){EditText v=new EditText(c);v.setTextSize(16);v.setTextColor(0xff222222);v.setHintTextColor(0xff666666);v.setHint(hint);v.setContentDescription(hint);v.setSingleLine(single);v.setMaxLines(single?1:6);parent.addView(v,new LinearLayout.LayoutParams(-1,-2));return v;}
    static LinearLayout dialogBody(Context c){LinearLayout body=Aero.column(c);body.setPadding(dp(c,20),dp(c,8),dp(c,20),dp(c,8));return body;}
    static ScrollView scroll(Context c,View child){ScrollView s=new ScrollView(c);s.setFillViewport(true);s.addView(child);return s;}
    static void note(Context c,LinearLayout parent,String text){TextView v=text(c,text,14);v.setPadding(dp(c,12),dp(c,8),dp(c,12),dp(c,8));parent.addView(v);}
    static void error(Context c,Exception e){Toast.makeText(c,e.getMessage()==null?"This action could not complete.":e.getMessage(),Toast.LENGTH_LONG).show();}
    static boolean launch(Activity c,Intent i){try{c.startActivity(i);return true;}catch(ActivityNotFoundException|SecurityException e){Toast.makeText(c,"No app is available for this action. Your local data is kept.",Toast.LENGTH_LONG).show();return false;}}
    static ListView list(Context c,List<String> labels,java.util.function.IntConsumer click){ListView v=new ListView(c);v.setAdapter(new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,labels){public View getView(int at,View old,ViewGroup parent){TextView row=(TextView)super.getView(at,old,parent);row.setTextColor(0xff222222);row.setTextSize(16);row.setMinHeight(dp(c,58));row.setMaxLines(3);row.setBackgroundColor(at%2==0?0xfffafafa:Color.WHITE);return row;}});v.setOnItemClickListener((p,row,at,id)->click.accept(at));return v;}
    private EightAppUi(){}
}
