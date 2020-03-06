## Splash (work-in-progress)

Splash is an unofficial Unsplash Android client that showcases use of `Dagger 2` `Rxjava 2`  `Retrofit` and `Android Architecture components`.<br/>

## CI [![Build Status](https://app.bitrise.io/app/ace9bafaf050bb3e/status.svg?token=OBEws1-W4WXbb1hZ4KPJgQ&branch=develop)](https://app.bitrise.io/app/ace9bafaf050bb3e)

## Setup
You can configure your API key,API secret and auth callback in `app/build.gradle`.<br/><br/>

<img src="https://github.com/NULLPointerGuy/Splash/blob/master/assets/screenshot-1.png" width="200"> <img src="https://github.com/NULLPointerGuy/Splash/blob/master/assets/screenshot-2.png" width="200"> <img src="https://github.com/NULLPointerGuy/Splash/blob/master/assets/screenshot-3.png" width="200"> <img src="https://github.com/NULLPointerGuy/Splash/blob/master/assets/screenshot-4.png" width="200"> <img src="https://github.com/NULLPointerGuy/Splash/blob/master/assets/screenshot-5.png" width="200"> <img src="https://github.com/NULLPointerGuy/Splash/blob/master/assets/screenshot-6.png" width="200">

## Todo:<br/>
~~1.Rewrite in kotlin.<br/>~~
2.Use following achitecture components:.<br/>
   &nbsp;&nbsp;&nbsp;~~(2a)Lifecycles.<br/>~~
   &nbsp;&nbsp;&nbsp;~~(2b)LiveData<br/>~~
   &nbsp;&nbsp;&nbsp;~~(2c)Paging<br/>~~
   &nbsp;&nbsp;&nbsp;~~(2d)Room<br/>~~
   &nbsp;&nbsp;&nbsp;~~(2e)ViewModel<br/>~~
   &nbsp;&nbsp;&nbsp;(2f)Navigation<br/>
3.Write every layout using constraint.<br/>
4.Write Junit and Espresso tests.<br/>
5.Use UberAuto dispose for rxjava<br/>
5.Migration of gradle scripts from groovy to kotlin.<br/>
6.DownloadManager for downloading images.<br/>
7.Measure the perf using leak canray and other available tools.<br/>
8.Use material components for the UI<br/>
9.Klint integration with basic CI.<br/>

## Technologies used so far:<br/>
1.Rx Java,Downloader,Android<br/>
2.Android arch components<br/>
3.Chuck for logging api calls.<br/>
4.Standard Square libs (okhttp,dagger,picasso)<br/>


## License
```
MIT License

Copyright (c) 2017 Karthik

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
