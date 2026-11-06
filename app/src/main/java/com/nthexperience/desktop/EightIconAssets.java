package com.nthexperience.desktop;
import android.content.Context;
import android.graphics.drawable.Drawable;

/** Exact owner-supplied ICO artwork; unsupported kinds use the existing Nth vector fallback. */
final class EightIconAssets {
    static int resource(String kind){switch(kind){
        case "computer":case "system":return R.drawable.win8_computer;
        case "desktop":return R.drawable.win8_desktop;
        case "files":case "folderapps":case "file-explorer":return R.drawable.win8_folder;
        case "documents":return R.drawable.win8_documents;
        case "downloads":return R.drawable.win8_downloads;
        case "pictures":return R.drawable.win8_pictures;
        case "music":return R.drawable.win8_music;
        case "videos":return R.drawable.win8_videos;
        case "network":return R.drawable.win8_network;
        case "recycle":return R.drawable.win8_recycleempty;
        case "recycle-full":return R.drawable.win8_recyclefull;
        case "favorites":case "star":return R.drawable.win8_favorites;
        case "contacts":return R.drawable.win8_contacts;
        case "accounts":case "userfolder":return R.drawable.win8_userfolder;
        case "links":return R.drawable.win8_links;
        case "search":return R.drawable.win8_searches;
        case "puzzle":case "games":return R.drawable.win8_games;
        default:return 0;
    }}
    static Drawable drawable(Context context,String kind){int resource=resource(kind);return resource==0?null:context.getDrawable(resource);}
    private EightIconAssets(){}
}
