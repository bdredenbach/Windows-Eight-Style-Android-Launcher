"""Confirm that Gradle packaged the dedicated setup instrumentation entry."""
import os
from pathlib import Path
import subprocess

aapt = Path(os.environ['ANDROID_HOME']) / 'build-tools/35.0.0/aapt'
manifest = subprocess.check_output([str(aapt), 'dump', 'xmltree',
    'app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk',
    'AndroidManifest.xml'], text=True)
assert 'com.nthexperience.desktop.EightInitializationSmoke' in manifest
assert 'com.nthexperience.desktop.eight' in manifest
print('PASS: Eight setup runner is registered in the packaged instrumentation APK')
