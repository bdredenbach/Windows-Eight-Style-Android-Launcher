package com.nthexperience.desktop;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;
/** A flat, resizable month surface backed by the existing locale/date state. */
final class EightCalendar extends LinearLayout {
    final MonthCalendarView calendar;final TextView month,date;
    EightCalendar(Context c){super(c);setOrientation(VERTICAL);setBackgroundColor(Color.WHITE);setContentDescription("Eight Calendar month view");TenAppearance.preserve(this);setPadding(dp(20),dp(16),dp(20),dp(12));
        LinearLayout top=new LinearLayout(c);top.setGravity(Gravity.CENTER_VERTICAL);month=Aero.text(c,"",29,0xff222222);top.addView(month,new LayoutParams(0,dp(62),1));TextView previous=command("‹","Previous month",()->shift(-1));TextView next=command("›","Next month",()->shift(1));top.addView(previous,new LayoutParams(dp(46),dp(48)));top.addView(next,new LayoutParams(dp(46),dp(48)));addView(top);
        calendar=new MonthCalendarView(c,this::update);calendar.shellStyle();calendar.setMinimumHeight(dp(200));addView(calendar,new LayoutParams(-1,0,1));date=Aero.text(c,"",14,0xff444444);date.setMinHeight(dp(40));addView(date);addView(command("Today","Calendar Today",()->calendar.today()),new LayoutParams(-1,dp(46)));update();
    }
    int dp(int n){return Aero.dp(getContext(),n);}void shift(int amount){calendar.offset(amount);}void update(){if(calendar==null||date==null)return;month.setText(calendar.title());date.setText(java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(calendar.selected()));}
    TextView command(String s,String description,Runnable action){TextView v=Aero.text(getContext(),s,17,0xff4e3890);v.setContentDescription(description);v.setGravity(Gravity.CENTER);v.setFocusable(true);v.setBackground(Aero.selection(getContext()));v.setOnClickListener(w->action.run());return v;}
}
