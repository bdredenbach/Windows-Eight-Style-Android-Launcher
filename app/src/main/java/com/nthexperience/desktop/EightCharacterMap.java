package com.nthexperience.desktop;

import android.content.*;
import android.graphics.Color;
import android.text.*;
import android.view.Gravity;
import android.widget.*;
import java.util.*;

final class EightCharacterMap extends LinearLayout {
    final GridLayout grid;final EditText selection;final TextView detail;String range="Latin";
    EightCharacterMap(Context c){super(c);setOrientation(VERTICAL);setBackgroundColor(0xfff2f2f2);setContentDescription("Character Map");TenAppearance.preserve(this);Spinner ranges=new Spinner(c);ranges.setAdapter(new ArrayAdapter<>(c,android.R.layout.simple_spinner_dropdown_item,new String[]{"Latin","Greek","Currency","Symbols"}));addView(ranges);ScrollView scroll=new ScrollView(c);grid=new GridLayout(c);grid.setColumnCount(6);scroll.addView(grid);addView(scroll,new LayoutParams(-1,0,1));detail=Aero.text(c,"Tap a character to select it.",13,0xff333333);addView(detail);selection=new EditText(c);selection.setSingleLine();selection.setHint("Characters to copy");selection.setContentDescription("Characters to copy");addView(selection);addView(Aero.button(c,"Copy",()->{((android.content.ClipboardManager)c.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Characters",selection.getText()));Toast.makeText(c,"Characters copied",Toast.LENGTH_SHORT).show();}));ranges.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener(){public void onNothingSelected(android.widget.AdapterView<?> p){}public void onItemSelected(android.widget.AdapterView<?> p,android.view.View v,int n,long id){range=new String[]{"Latin","Greek","Currency","Symbols"}[n];render();}});render();addOnLayoutChangeListener((v,l,t,r,b,ol,ot,or,ob)->{int columns=Math.max(3,(r-l)/Aero.dp(c,48));if(grid.getColumnCount()!=columns){grid.setColumnCount(columns);render();}});}
    void render(){grid.removeAllViews();int start=range.equals("Greek")?0x391:range.equals("Currency")?0x20a0:range.equals("Symbols")?0x2600:0xa1;int end=start+(range.equals("Currency")?32:96);for(int cp=start;cp<end;cp++){final int code=cp;String s=new String(Character.toChars(cp));TextView b=Aero.text(getContext(),s,24,Color.BLACK);if(!b.getPaint().hasGlyph(s))continue;b.setGravity(Gravity.CENTER);b.setMinWidth(Aero.dp(getContext(),44));b.setMinHeight(Aero.dp(getContext(),48));b.setContentDescription(s+" U+"+Integer.toHexString(cp).toUpperCase(Locale.ROOT));b.setFocusable(true);b.setBackground(Aero.selection(getContext()));b.setOnClickListener(v->{selection.append(s);detail.setText("U+"+Integer.toHexString(code).toUpperCase(Locale.ROOT)+" · "+Character.getName(code));});grid.addView(b);}}
}
