# CompassServant
这是一个仿魅族分贝仪的自定义 View，也可以叫 Compass 或者 Dashboard，可以自己设置颜色渐变，xml 支持4种颜色值，也可以使用 setGalaxyColors 方法设置更多种颜色渐变。   


# ScreenShot Video
![](https://github.com/dyzs/CompassServant/blob/master/video/compass_servant.gif)

# Features
This repo...

# Usage
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
also add listener and start tension when you reset pointer    
```java
	compass_servant.setServantListener(new CompassServant.ServantListener() {
            @Override
            public void startTension() {
                mLooper.sendEmptyMessage(MESSAGE);
            }
        });
```

# Attributes
```
|:---|:---|
| app:background | using sys attr{android.R.attr.background} The background must be set.
| app:padding | using sys attr{android.R.attr.padding} The padding means spacing between border and outer circle.  
| app:cs_color_commander | Value of commander is {2~4}, control the number of your gradient colors with xml. 
| app:cs_color1 | The start color.
| app:cs_color2 | The 2nd color.
| app:cs_color3 | The 3rd color.
| app:cs_color4 | The 4th color.
| app:cs_galaxy_degree | The degree of your set with xml.
| app:cs_outer_circle | Named clearly.
| app:cs_tick_mark_length | The compass tick length.
| app:cs_decibel | Number of tick mark.
| app:cs_text_size | The current tick mark text.
```
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
