# SyRadarView

[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-16%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=16)  

雷达图(蜘蛛网图)可以将各个成员的比重直观的展示出来，本项目实现了这个控件并进行封装，可以供有需要的同学直接拿来使用。  
	
## 效果展示：   
demo下载地址：  [https://github.com/talonerain/SyRadarView/tree/master/demo/demo-release.apk](https://github.com/talonerain/SyRadarView/tree/master/demo/demo-release.apk)

<img width="300" height="500" src="https://raw.githubusercontent.com/talonerain/SyRadarView/master/screenshots/demo.png"/>

## 如何使用：
### 1. 在project的gradle中添加：


```java
	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
``` 
### 2. 在moudle的gradle中添加：

```java
	dependencies {
	        compile 'com.github.talonerain:SyRadarView:1.0.2'
	}
```
### 3. 在布局中引用：

```xml
	<com.lsy.radarview.SyRadarView
	        android:id="@+id/radarView"
	        android:layout_marginTop="30dp"
	        android:layout_width="match_parent"
	        android:layout_height="300dp"
	        app:textColor = "@color/colorAccent"
	        app:shadowAlpha = "200"/>
```
### 4. 设置数据源：

```java
	String[] texts = {};	//各成员名字
	Double[] percents = {};	//各成员比重，用小数表示
	radarView.setTextList(texts);  
	radarView.setPercentList(percents);
```

## 自定义样式
控件的样式都有默认值，如需自定义，可以在代码中设置，也可以通过自定义属性设置，后者需要在xml布局文件中引入名字空间：

```
xmlns:syview="http://schemas.android.com/apk/res-auto"
``` 

- 多边形边线颜色：
	- syRadarView.setNgonColor(int color);
	- syview:ngonColor=""
- 多边形边线宽度：
	- syRadarView.setStrokeWidth(float size);
	- syview:ngonSize=""
- 连线颜色：
	- syRadarView.setLineColor(int color);
	- syview:connLineColor=""
- 连线宽度：
	- syRadarView.setLineSize(float size);
	- syview:connLineSize=""
- 阴影区域颜色：
	- syRadarView.setShadowColor(int color);
	- syview:shadowColor=""
- 阴影区域透明度：
	- syRadarView.setShadowAlpha(int alpha);
	- syview:shadowAlpha=""
- 文字颜色：
	- syRadarView.setTextColor(int color);
	- syview:textColor=""
- 文字大小
	- syRadarView.setTextSize(float size);
	- syview:textSize=""


License
-------
Copyright 2018 Siyu Liu  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.