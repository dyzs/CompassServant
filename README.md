CompassServant|[中文文档](https://github.com/dyzs/CompassServant/blob/master/README_CH.md)
--------
this is a practice view like compass or dashboard, you can set color gradient wish what you want.

ScreenShot Video
--------

![](https://github.com/dyzs/CompassServant/blob/master/video/compass_servant.gif)

Download
--------
maven
```xml
<dependency>
  <groupId>com.dyzs.compassservant</groupId>
  <artifactId>compassservant</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

gradle
```xml
compile 'com.dyzs.compassservant:compassservant:1.0.0'
```

Usage
--------

xml sample
```xml
<com.dyzs.compassservant.CompassServant
    android:layout_width="match_parent"
    android:layout_height="300dp"
    android:background="@color/black"
    android:padding="5dp"
    app:cs_color_commander="4"
    app:cs_color1="@color/alice_blue"
    app:cs_color2="@color/cinnabar_red"
    app:cs_color3="@color/green"
    app:cs_color4="@color/white"
    app:cs_galaxy_degree="280"
    app:cs_outer_circle="7dp"
    app:cs_tick_mark_length="30dp"
    app:cs_decibel="119"
    app:cs_text_size="30dp"
    />
```
and reset tick value and start animation
```java
compass_servant.setPointerDecibel(iRandom);
```
also add listener and start tension when you reset pointer    
```java
compass_servant.setServantListener(new CompassServant.ServantListener() {
        @Override
        public void startTension() {
            mLooper.sendEmptyMessage(MESSAGE);
        }
    });
```

Attributes
--------

## using sys attrs {android.R.attr.background, android.R.attr.padding}
| **attr** ||
|:---|:---|
| android:background | The background color {android.R.attr.background}.
| android:padding | The  spacing between border and outer circle{android.R.attr.padding}.
| app:cs_color_commander | {2~4},the number of your gradient colors in xml.
| app:cs_color1 | The 1st color.
| app:cs_color2 | The 2nd color.
| app:cs_color3 | The 3rd color.
| app:cs_color4 | The 4th color.
| app:cs_galaxy_degree | The degree of your set in xml.
| app:cs_outer_circle | Outer circle width.
| app:cs_tick_mark_length | The compass tick length.
| app:cs_decibel | Number of tick mark.
| app:cs_text_size | The current tick mark text size.

# License

    Copyright (C) 2018 Misaka Mikoto(dyzs)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
