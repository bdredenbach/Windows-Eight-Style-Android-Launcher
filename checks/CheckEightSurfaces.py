"""Guard actual construction routes; historical engine class names are not user-facing UI."""
from pathlib import Path
root=Path('app/src/main/java/com/nthexperience/desktop')
main=(root/'MainActivity.java').read_text()
for name in ('TenSettings','TenTaskView','TenActionCenter','TenNewsFlyout','TenCalculator'):
    assert 'new '+name+'(' not in main,'Eight constructs '+name
assert 'new UsageAccessExample(this)' in main
assert 'usage_access_app_info' not in main
assert not Path('app/src/main/res/drawable-nodpi/usage_access_app_info.webp').exists()
for name in ('WelcomeCenter','EightPCSettings','EightDesktopOptions','EightCharms'):
    text=(root/(name+'.java')).read_text()
    for old in ('Nth Desktop Seven','Nth Desktop Ten','News and interests','Choose Ten or','Windows 10 style Settings'):
        assert old not in text,name+' retains '+old
print('PASS: Eight owns settings, switcher, Charms, Calculator and live product guide; stale guide asset excluded')
