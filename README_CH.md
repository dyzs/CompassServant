# 自定义罗盘
这是一个仿魅族分贝仪的自定义控件，继承 view，可以叫它 Compass 或者 Dashboard，可以自己设置颜色渐变。

# gif 录屏
![](https://github.com/dyzs/CompassServant/blob/master/video/compass_servant.gif)

# 使用方式
xml 方式
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
通过设置指针的刻度，开启绘制动画
```java
compass_servant.setPointerDecibel(iRandom);
```
同时添加动画监听方法，该方法在当前指针动画结束时调用
```java
compass_servant.setServantListener(new CompassServant.ServantListener() {
        @Override
        public void startTension() {
            mLooper.sendEmptyMessage(MESSAGE);
        }
    });
```

# 是用参数
## 覆盖了两个系统参数 {android.R.attr.background, android.R.attr.padding}
| **attr** ||
|:---|:---|
| android:background | 背景颜色 {android.R.attr.background}.
| android:padding | 罗盘指针的间隙{android.R.attr.padding}.
| app:cs_color_commander | {2~4},在 xml 中设置控制显示的颜色渐变个数
| app:cs_color1 | 第一个颜色.
| app:cs_color2 |
| app:cs_color3 |
| app:cs_color4 |
| app:cs_galaxy_degree | 整个罗盘的刻度的总角度，小于360
| app:cs_outer_circle | 外圆环宽带
| app:cs_tick_mark_length | 刻度长度
| app:cs_decibel | 罗盘的总刻度
| app:cs_text_size | 中间显示的刻度值的文字大小

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
