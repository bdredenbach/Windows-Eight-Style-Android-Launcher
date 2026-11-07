"""Inspect packaged identity, provider isolation and native test registrations."""
import os
from pathlib import Path
import subprocess
aapt=Path(os.environ['ANDROID_HOME'])/'build-tools'/'35.0.0'/'aapt'
def manifest(path):
    return subprocess.check_output([str(aapt),'dump','xmltree',path,'AndroidManifest.xml'],text=True)
main=manifest('app/build/outputs/apk/debug/app-debug.apk')
tests=manifest('app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk')
assert 'com.nthexperience.desktop.eight' in main
assert 'Nth Desktop Eight' in subprocess.check_output([str(aapt),'dump','badging','app/build/outputs/apk/debug/app-debug.apk'],text=True)
assert 'com.nthexperience.desktop.eight.storage' in main
assert 'com.nthexperience.desktop.ten' not in main
assert 'com.nthexperience.desktop.eight.test' in tests
for runner in ('EightSmoke','EightGestureSmoke','EightAuditSmoke','EightImmersiveSmoke','EightResearchSmoke','BrowserSmoke','ExplorerSmoke','PhotoSmoke','MediaSmoke'):
    assert 'com.nthexperience.desktop.'+runner in tests,runner+' registration missing'
assert 'android.intent.category.HOME' in main
print('PASS: Eight app identity, isolated providers, Home intent and nine native runners')
