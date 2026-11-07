package com.nthexperience.desktop;
import android.content.*;
import android.graphics.Color;
import android.view.*;
import android.widget.*;
import org.json.*;
import java.util.function.Consumer;

/** Eight's separate panoramic News app, backed by the real RSS cache. */
final class EightNews extends LinearLayout {
    final DesktopStore store;final Consumer<String> open;final FeedClient feed;final TextView status;final LinearLayout stories;boolean closed;
    EightNews(Context c,DesktopStore store,Consumer<String> open){super(c);this.store=store;this.open=open;feed=new FeedClient(store,()->post(this::render));setOrientation(VERTICAL);setBackgroundColor(0xfffafafa);setContentDescription("Eight News app");TenAppearance.preserve(this);
        TextView title=Aero.text(c,"News",36,0xff222222);title.setPadding(dp(24),dp(15),dp(20),dp(12));addView(title);View line=new View(c);line.setBackgroundColor(0xffb5272b);addView(line,new LayoutParams(-1,dp(5)));
        status=Aero.text(c,"",12,0xff555555);status.setPadding(dp(24),dp(10),dp(24),dp(12));status.setContentDescription("News connection status");status.setAccessibilityLiveRegion(ACCESSIBILITY_LIVE_REGION_POLITE);addView(status);
        HorizontalScrollView panorama=new HorizontalScrollView(c);panorama.setContentDescription("News panorama");stories=new LinearLayout(c);stories.setPadding(dp(24),dp(12),dp(24),dp(20));panorama.addView(stories);addView(panorama,new LayoutParams(-1,0,1));LinearLayout commands=new LinearLayout(c);commands.setBackgroundColor(0xffb5272b);for(String s:new String[]{"Choose feed","Refresh"}){TextView b=Aero.text(c,s,14,Color.WHITE);b.setGravity(Gravity.CENTER);b.setMinHeight(dp(52));b.setContentDescription(s);b.setFocusable(true);b.setOnClickListener(v->{if(s.equals("Choose feed"))configure();else{feed.active(true);feed.refresh(true);}});commands.addView(b,new LayoutParams(0,dp(52),1));}addView(commands);
        render();
    }
    int dp(int n){return Aero.dp(getContext(),n);}
    void render(){if(closed||feed==null)return;status.setText(feed.status());stories.removeAllViews();JSONArray rows=FeedClient.items(store);if(rows.length()==0){TextView empty=Aero.text(getContext(),"Top stories\n\nChoose an HTTPS RSS or Atom feed to read real headlines here.",22,0xff333333);empty.setPadding(0,dp(12),dp(18),0);stories.addView(empty,new LayoutParams(dp(280),-1));return;}
        for(int i=0;i<rows.length();i++){JSONObject row=rows.optJSONObject(i);if(row==null||!BrowserUrls.web(row.optString("url")))continue;LinearLayout tile=Aero.column(getContext());tile.setPadding(dp(16),dp(16),dp(16),dp(16));tile.setBackgroundColor(i==0?0xffb5272b:0xffeeeeee);int ink=i==0?Color.WHITE:0xff222222;TextView headline=Aero.text(getContext(),row.optString("title"),i==0?27:22,ink);headline.setMaxLines(8);headline.setContentDescription("Read story: "+row.optString("title"));tile.addView(headline);TextView summary=Aero.text(getContext(),row.optString("summary"),14,ink);summary.setPadding(0,dp(20),0,0);summary.setMaxLines(10);tile.addView(summary);tile.setFocusable(true);tile.setContentDescription("News story: "+row.optString("title"));tile.setOnClickListener(v->open.accept("favorite-web:"+row.optString("url")));tile.setOnLongClickListener(v->{boolean saved=EightReadingList.add(store,row.optString("url"),row.optString("title"));Toast.makeText(getContext(),saved?"Saved to Reading List":"This story cannot be saved",Toast.LENGTH_SHORT).show();return true;});LayoutParams p=new LayoutParams(dp(i==0?340:280),-1);p.rightMargin=dp(16);stories.addView(tile,p);}
    }
    void configure(){EditText address=new EditText(getContext());address.setSingleLine();address.setText(feed.attemptedUrl());address.setHint("https://…/feed.xml");address.setContentDescription("News feed address");android.app.AlertDialog dialog=Aero.dialog(getContext()).setTitle("News source").setView(address).setNeutralButton("BBC News",(d,w)->feed.configure(FeedClient.BBC)).setNegativeButton("Cancel",null).setPositiveButton("Connect",null).create();dialog.setOnShowListener(d->dialog.getButton(-1).setOnClickListener(v->{String url=address.getText().toString().trim();if(!FeedClient.validUrl(url)){address.setError("Use an HTTPS RSS or Atom feed address");return;}feed.configure(url);dialog.dismiss();}));dialog.show();}
    @Override public void onVisibilityAggregated(boolean visible){super.onVisibilityAggregated(visible);if(feed!=null&&!closed)feed.active(visible);}
    void close(){if(!closed){closed=true;feed.close();}}
}
