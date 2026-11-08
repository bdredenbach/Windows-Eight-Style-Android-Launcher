package com.nthexperience.desktop;
import android.content.*;import android.net.Uri;import android.provider.DocumentsContract;
/** A chosen document may inherit durable access from its already persisted tree. */
final class EightFileGrants {
    static boolean keep(Context c,Uri uri){if(uri==null||!"content".equals(uri.getScheme()))return false;try{c.getContentResolver().takePersistableUriPermission(uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);return true;}catch(SecurityException ignored){}if((c.getPackageName()+".storage").equals(uri.getAuthority())||(c.getPackageName()+".eight-files").equals(uri.getAuthority()))return true;for(UriPermission p:c.getContentResolver().getPersistedUriPermissions())if(p.isReadPermission()){if(p.getUri().equals(uri))return true;try{if(DocumentsContract.isTreeUri(p.getUri())&&DocumentsContract.isTreeUri(uri)&&p.getUri().getAuthority().equals(uri.getAuthority())&&DocumentsContract.getTreeDocumentId(p.getUri()).equals(DocumentsContract.getTreeDocumentId(uri)))return true;}catch(IllegalArgumentException ignored){}}return false;}
    private EightFileGrants(){}
}
