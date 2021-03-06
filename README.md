[![Build Status](https://travis-ci.org/dnltsk/intellij-geocoding-plugin.svg?branch=master)](https://travis-ci.org/dnltsk/intellij-geocoding-plugin/) [![codebeat badge](https://codebeat.co/badges/ba834d38-32eb-4ab2-b396-10e36765f492)](https://codebeat.co/projects/github-com-dnltsk-intellij-geocoding-plugin) [![codecov](https://codecov.io/gh/dnltsk/intellij-geocoding-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/dnltsk/intellij-geocoding-plugin)

# IntelliJ Geocoding Plugin

https://plugins.jetbrains.com/plugin/9235

This plugin is made for engineers working with Location-Based-Services / -Applications 
Thus you can replace address names by its coordinates (geocoding) and vice versa (reverse geocoding).
It enables you to create test-locations quickly and resolve coordinates to its addresses within your IntelliJ Editor.

After you installed it you have a new action group **Geocode** inside your **Refactoring** context menu.

### Geocoding Shortcuts

* `⌥⌘G` for geocoding
* `⇧⌥⌘G` for reverse geocoding

### Geocoding Context Menu

[![Geocoding Context Menu](src/main/resources/META-INF/context-menu.png)](src/main/resources/META-INF/context-menu-large.png)

### Create Sample Data Demo

![Geocoding Sample](src/main/resources/META-INF/geocoding.gif)

### Reverse Geocoding Demo

![Geocoding Sample](src/main/resources/META-INF/reverse-geocoding.gif)

### Geocoding Plugin Preferences

![Geocoding Context Menu](src/main/resources/META-INF/preferences.png)

## Install Plugin into your IntelliJ

### via IntelliJ Plugin Repository

1. in your IntelliJ go to _Preferences_ `⌘,`
2. in _Plugins_ search for "Geocoding Plugin"
3. select _Search in repositories_
4. there you should find the _Geocoding Plugin_
5. restart IntelliJ

### via JAR file

1. clone this Repo
2. `gradle test buildPlugin`
3. in your IntelliJ go to _Preferences_ `⌘,` 
4. in _Plugins_ click on _Install plugin from disk_ 
5. select the generated jar file under _build/distributions/_
6. restart IntelliJ

## Run from scratch

1. clone this Repo
2. adjust code if you want to
3. `gradle test runIdea`

## Troubleshooting

If the shortcuts `⌥⌘G` or `⇧⌥⌘G` don't work in your IntelliJ, they might overlap with other plugins. 
This is how you can fix this issue: 

1. in your IntelliJ go to _Preferences_ `⌘,`
2. select _Keymap_
3. select the _Find Actions by Shortcut_ icon in the upper-right corner and fire the shortcut
4. modify the overlapping shortcuts to your personal solution

## License

* Source Code under [Apache 2.0](LICENSE)
* Geocoded data is provided under the [ODbL](http://opendatacommons.org/licenses/odbl/) via Nominatim / OpenStreetMap®
