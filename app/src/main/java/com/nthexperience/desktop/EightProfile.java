package com.nthexperience.desktop;

/** Behavior profiles are independent of the desktop's flat/Aero appearance. */
enum EightProfile {
    EIGHT("8", "Windows 8", false), EIGHT_POINT_ONE("81", "Windows 8.1", true);
    final String id, label;
    final boolean startButton;
    EightProfile(String id, String label, boolean startButton) {
        this.id=id; this.label=label; this.startButton=startButton;
    }
    static EightProfile from(String id) { return "81".equals(id)?EIGHT_POINT_ONE:EIGHT; }
    String[] sizes() { return this==EIGHT?new String[]{"Medium","Wide"}:new String[]{"Small","Medium","Wide","Large"}; }
    boolean supports(String size) { for(String value:sizes())if(value.equals(size))return true; return false; }
}
