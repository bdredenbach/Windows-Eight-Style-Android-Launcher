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
        case "browser":return R.drawable.win8_browser;
        case "control":return R.drawable.win8_control_panel;
        case "drive":return R.drawable.win8_drive;
        case "security":return R.drawable.win8_security;
        case "programs":return R.drawable.win8_programs;
        case "apps":return R.drawable.win8_application;
        case "documentfile":return R.drawable.win8_document_file;
        case "picturefile":return R.drawable.win8_picture_file;
        case "musicfile":return R.drawable.win8_music_file;
        case "videofile":return R.drawable.win8_video_file;
        case "archivefile":return R.drawable.win8_archive;
        case "appearance":return R.drawable.win8_appearance;
        case "sound":return R.drawable.win8_sound;
        case "fonts":return R.drawable.win8_fonts;
        case "taskmanager":return R.drawable.win8_taskmanager;
        case "paint":return R.drawable.win8_paint;
        case "wordpad":return R.drawable.win8_wordpad;
        case "power":case "battery":return R.drawable.win8_power;
        case "accessibility":return R.drawable.win8_accessibility;
        case "delete":return R.drawable.win8_delete;
        default:return 0;
    }}
    static Drawable drawable(Context context,String kind){int resource=resource(kind);return resource==0?null:context.getDrawable(resource);}
    private EightIconAssets(){}
}
