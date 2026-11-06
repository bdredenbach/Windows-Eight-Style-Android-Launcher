package com.nthexperience.desktop;

import android.content.*;
import android.graphics.Color;
import android.view.*;
import android.widget.*;
import java.util.*;

/** Content-first Eight forecast, with usable Places controls even before the first forecast. */
final class WeatherPanel extends ScrollView {
    interface Host {void locate();void settings();void follow(boolean enabled);void refresh();}
    private final DesktopStore store;private final WeatherClient weather;private final WeatherLocation location;private final Host host;
    private final LinearLayout column,forecast,places,conditions,actions;private boolean immersive,showPlaces;
    private final CitySearch cities=new CitySearch();private final TextView title,city,temperature,summary,status,locationStatus,searchButton;private final LinearLayout results;private final CheckBox follow;private final EditText input;private final EightWeatherIcon icon;
    WeatherPanel(Context c,DesktopStore store,WeatherClient weather,WeatherLocation location,Host host){
        super(c);this.store=store;this.weather=weather;this.location=location;this.host=host;setFillViewport(true);TenAppearance.preserve(this);
        column=Aero.column(c);Aero.pad(column,16);addView(column,new ScrollView.LayoutParams(-1,-2));
        title=Aero.text(c,"Weather",22,0xff173a65);column.addView(title);
        city=Aero.text(c,"",24,Color.WHITE);city.setPadding(0,dp(12),0,dp(8));city.setContentDescription("Weather selected location");column.addView(city);
        conditions=new LinearLayout(c);conditions.setGravity(Gravity.CENTER_VERTICAL);icon=new EightWeatherIcon(c,"unknown",Color.WHITE);conditions.addView(icon,new LinearLayout.LayoutParams(dp(88),dp(88)));temperature=Aero.text(c,"—",64,Color.WHITE);temperature.setContentDescription("Weather current temperature");conditions.addView(temperature,new LinearLayout.LayoutParams(0,-2,1));column.addView(conditions);
        summary=Aero.text(c,"",14,Aero.INK);summary.setPadding(0,dp(8),0,dp(16));summary.setContentDescription("Weather summary");column.addView(summary);
        forecast=new LinearLayout(c);forecast.setContentDescription("Weather hourly forecast");column.addView(forecast);
        actions=new LinearLayout(c);actions.setGravity(Gravity.CENTER_VERTICAL);column.addView(actions,new LinearLayout.LayoutParams(-1,dp(52)));
        action("Places",this::togglePlaces);
        action("My location",host::locate);action("Refresh",host::refresh);
        places=Aero.column(c);places.setPadding(dp(12),dp(12),dp(12),dp(16));places.setBackgroundColor(0xfff7f8f9);places.setContentDescription("Weather Places settings");column.addView(places,new LinearLayout.LayoutParams(-1,-2));
        TextView placesTitle=Aero.text(c,"Add or change a location",22,0xff173a65);places.addView(placesTitle);
        input=new EditText(c);input.setSingleLine(true);input.setTextSize(14);input.setTextColor(Aero.INK);input.setHintTextColor(0xff586976);input.setTypeface(Aero.face(c));input.setHint("City and state, or city and country");input.setContentDescription("Weather city search");places.addView(input,new LinearLayout.LayoutParams(-1,dp(48)));
        status=Aero.text(c,"",12,0xff596e7a);status.setContentDescription("Weather city search status");places.addView(status);results=Aero.column(c);
        searchButton=button(places,"Search for a city",this::search);input.setImeOptions(android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH);input.setOnEditorActionListener((v,action,event)->{if(action==android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH){search();return true;}return false;});places.addView(results);
        button(places,"Use my approximate location",host::locate);
        follow=Aero.check(c,"Follow my location",store.prefs.getBoolean("weather_follow",false));follow.setContentDescription("Follow my location");places.addView(follow);follow.setOnCheckedChangeListener((v,checked)->host.follow(checked));
        TextView detail=Aero.text(c,"Updates your approximate area while Nth Desktop is visible, and when you return home.",12,0xff586976);detail.setTypeface(android.graphics.Typeface.create(Aero.face(c),android.graphics.Typeface.ITALIC));places.addView(detail);
        locationStatus=Aero.text(c,"",12,0xff3d657d);locationStatus.setPadding(0,dp(8),0,dp(10));locationStatus.setContentDescription("Weather location status");places.addView(locationStatus);button(places,"Device location settings",host::settings);
        CheckBox units=Aero.check(c,"Fahrenheit (°F)",store.prefs.getBoolean("weather_f",Locale.getDefault().getCountry().equals("US")));units.setContentDescription("Fahrenheit (°F)");units.setOnCheckedChangeListener((v,value)->{store.prefs.edit().putBoolean("weather_f",value).apply();host.refresh();refresh();});places.addView(units);button(places,"Refresh forecast",host::refresh);
        TextView hint=Aero.text(c,"Saved forecasts are available offline for their selected area. A new area needs an internet connection.",12,0xff586976);hint.setPadding(0,dp(12),0,dp(10));places.addView(hint);
        TextView attribution=Aero.text(c,"Weather: MET Norway · CC BY 4.0\nCity search: Photon / OpenStreetMap",11,0xff235e92);places.addView(attribution);showPlaces=!weather.configured();refresh();
    }
    private void togglePlaces(){showPlaces=!showPlaces;updatePlaces();if(showPlaces){input.requestFocus();post(()->smoothScrollTo(0,places.getTop()));}}
    private int dp(int n){return Aero.dp(getContext(),n);}
    private void action(String name,Runnable run){TextView v=Aero.text(getContext(),name,14,Color.WHITE);v.setGravity(Gravity.CENTER);v.setMinHeight(dp(48));v.setBackground(Aero.selection(getContext()));v.setFocusable(true);v.setContentDescription("Weather "+name);v.setOnClickListener(w->run.run());actions.addView(v,new LinearLayout.LayoutParams(0,-1,1));}
    private void search(){String query=input.getText().toString().trim();if(query.length()<2){input.setError("Enter a city");return;}if(query.length()>160){input.setError("Use a shorter city name");return;}searchButton.setEnabled(false);status.setText("Searching…");results.removeAllViews();cities.search(query,(values,error)->{searchButton.setEnabled(true);status.setText(error.isEmpty()?values.isEmpty()?"No matching locations. Try a city and country.":"Choose your location:":error);for(CitySearch.Place place:values)button(results,place.label,()->{location.fixed();weather.location(place.label,place.latitude,place.longitude);results.removeAllViews();status.setText("Location saved.");showPlaces=false;updatePlaces();((android.view.inputmethod.InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(input.getWindowToken(),0);input.clearFocus();smoothScrollTo(0,0);refresh();});});}
    private TextView button(LinearLayout parent,String name,Runnable run){TextView v=Aero.button(getContext(),name,run);v.setContentDescription(name);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.topMargin=dp(6);v.setMinHeight(dp(42));parent.addView(v,p);return v;}
    private void updatePlaces(){places.setVisibility(!immersive||showPlaces||!weather.configured()?VISIBLE:GONE);}
    void immersive(boolean value,boolean commands){immersive=value;if(commands)showPlaces=true;setContentDescription(value?"Eight immersive Weather":"Weather settings");column.setBackgroundColor(value?0xff14688d:0xfff7f8f9);setBackgroundColor(value?0xff14688d:0xfff7f8f9);title.setTextSize(value?40:22);title.setTextColor(value?Color.WHITE:0xff173a65);city.setTextColor(value?Color.WHITE:Aero.INK);summary.setTextColor(value?Color.WHITE:Aero.INK);for(int i=0;i<actions.getChildCount();i++)((TextView)actions.getChildAt(i)).setTextColor(value?Color.WHITE:0xff155b91);conditions.setVisibility(value?VISIBLE:GONE);refresh();}
    void refresh(){WeatherClient.Snapshot snapshot=weather.snapshot();city.setText(weather.city());boolean f=store.prefs.getBoolean("weather_f",Locale.getDefault().getCountry().equals("US"));temperature.setText(snapshot==null?"—":snapshot.temperature(f)+(f?"°F":"°C"));icon.symbol(snapshot==null?"unknown":snapshot.symbol);
        String text;if(snapshot!=null)text=snapshot.condition()+"\n"+(weather.stale()?"Cached forecast · ":"Updated · ")+android.text.format.DateFormat.getTimeFormat(getContext()).format(new Date(weather.updated()));else text=weather.loading()?"Loading forecast…":weather.configured()?"Forecast unavailable. Your selected area is kept. Try Refresh or change your location.":"Choose a city or use your approximate location below.";summary.setText(!immersive&&snapshot!=null?snapshot.temperature(f)+(f?"°F":"°C")+" · "+text:text);
        forecast.removeAllViews();if(immersive&&snapshot!=null)try{org.json.JSONArray hours=new org.json.JSONObject(store.prefs.getString("weather_json","{}")).getJSONObject("properties").getJSONArray("timeseries");for(int i=0;i<Math.min(24,hours.length());i+=6){org.json.JSONObject hour=hours.getJSONObject(i);double value=hour.getJSONObject("data").getJSONObject("instant").getJSONObject("details").getDouble("air_temperature");String time=android.text.format.DateFormat.getTimeFormat(getContext()).format(java.util.Date.from(java.time.Instant.parse(hour.getString("time"))));TextView label=Aero.text(getContext(),time+"\n"+Math.round(f?value*9/5+32:value)+"°",18,Color.WHITE);label.setGravity(Gravity.CENTER);label.setPadding(0,dp(16),0,dp(16));forecast.addView(label,new LinearLayout.LayoutParams(0,-2,1));}}catch(Exception ignored){}
        forecast.setVisibility(immersive&&forecast.getChildCount()>0?VISIBLE:GONE);updatePlaces();locationStatus.setText(location.status());boolean enabled=store.prefs.getBoolean("weather_follow",false);if(follow.isChecked()!=enabled){follow.setOnCheckedChangeListener(null);follow.setChecked(enabled);follow.setOnCheckedChangeListener((v,value)->host.follow(value));}}
    void close(){cities.close();}
}
