# SyRadarView

[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-16%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=16)  
雷达图(蜘蛛网图)可以将各个成员的比重直观的展示出来，本项目实现了这个控件并进行封装，可以供有需要的同学直接拿来使用。  
	
## 效果展示：   
<img width="250" height="500" src="https://raw.githubusercontent.com/talonerain/SyRadarView/master/screenshots/demo.png"/>

## 如何使用：
- 在project的gradle中添加：


```java
	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
``` 
- 在moudle的gradle中添加：

```java
	dependencies {
	        compile 'com.github.talonerain:SyRadarView:1.0.1'
	}
```
- 在布局中引用：

```xml
	<com.lsy.radarview.SyRadarView
        android:id="@+id/radarView"
        android:layout_marginTop="30dp"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        app:textColor = "@color/colorAccent"
        app:shadowAlpha = "200"/>
```