"""Inspect packaged identity, provider isolation and native test registrations."""
import os
from pathlib import Path
import subprocess
aapt=Path(os.environ['ANDROID_HOME'])/'build-tools'/'35.0.0'/'aapt'
def manifest(path):
    return subprocess.check_output([str(aapt),'dump','xmltree',path,'AndroidManifest.xml'],text=True)
apk='app/build/outputs/apk/release/app-release.apk'
main=manifest(apk)
assert 'android:debuggable(0x0101000f)=(type 0x12)0xffffffff' not in main,'Release APK must not be debuggable'
tests=manifest('app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk')
assert 'com.nthexperience.desktop.eight' in main
assert 'Nth Desktop Eight' in subprocess.check_output([str(aapt),'dump','badging',apk],text=True)
assert 'com.nthexperience.desktop.eight.storage' in main
assert 'com.nthexperience.desktop.ten' not in main
assert 'com.nthexperience.desktop.eight.test' in tests
for runner in ('EightInsetsSmoke','EightConnectedSmoke','EightReleaseSmoke','EightFidelitySmoke','EightSmoke','EightGestureSmoke','EightAuditSmoke','EightImmersiveSmoke','EightResearchSmoke','BrowserSmoke','ExplorerSmoke','PhotoSmoke','MediaSmoke'):
    assert 'com.nthexperience.desktop.'+runner in tests,runner+' registration missing'
assert 'android.permission.WRITE_CONTACTS' not in main and 'android.permission.WRITE_CALENDAR' not in main
assert 'android.intent.category.HOME' in main
print('PASS: Eight app identity, isolated providers, non-debuggable release, Home intent and thirteen native runners')
