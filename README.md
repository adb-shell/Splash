## Splash (work-in-progress)

Splash is an unofficial Unsplash Android client that showcases use of `Dagger 2` `Rxjava 2`  `Retrofit` and `MVP`.<br/>

## CI: [![Build Status](https://app.bitrise.io/app/ace9bafaf050bb3e/status.svg?token=OBEws1-W4WXbb1hZ4KPJgQ&branch=develop)](https://app.bitrise.io/app/ace9bafaf050bb3e)

## Setup
You can configure your API key,API secret and auth callback in `app/build.gradle`.<br/><br/>

<img src="https://github.com/NULLPointerGuy/Splash/blob/master/assets/screenshot-1.png" width="200"> <img src="https://github.com/NULLPointerGuy/Splash/blob/master/assets/screenshot-2.png" width="200"> <img src="https://github.com/NULLPointerGuy/Splash/blob/master/assets/screenshot-3.png" width="200"> <img src="https://github.com/NULLPointerGuy/Splash/blob/master/assets/screenshot-4.png" width="200"> <img src="https://github.com/NULLPointerGuy/Splash/blob/master/assets/screenshot-5.png" width="200"> <img src="https://github.com/NULLPointerGuy/Splash/blob/master/assets/screenshot-6.png" width="200">

## Todo:<br/>
~~1.Rewrite in kotlin.<br/>~~
2.Use following achitecture components:.<br/>
   &nbsp;&nbsp;&nbsp;(2a)Lifecycles.<br/>
   &nbsp;&nbsp;&nbsp;(2b)LiveData<br/>
   &nbsp;&nbsp;&nbsp;(2c)Paging<br/>
   &nbsp;&nbsp;&nbsp;(2d)Room<br/>
   &nbsp;&nbsp;&nbsp;(2e)ViewModel<br/>
   &nbsp;&nbsp;&nbsp;(2f)Navigation<br/>
3.Use Android KTX extensively.<br/>
4.Write Junit/Espresso tests.<br/>
5.Android Wear OS Support.<br/>
6.Android TV Support.<br/>
7.DownloadManager for downloading images.<br/>
8.Image Loading using Coil<br/>
9.Explore/Integrate Few libs during dev: Leak Canary, Chuck, Scalpel, debug drawer, Flipper, bug reporting<br/>
10.Use material components for the UI<br/>
11.Klint integration with basic CI.<br/>

## Technologies used so far:<br/>
1.Rxjava<br/>
2.RxDownloader<br/>
3.RxAndroid<br/>
4.Chuck for logging api calls.<br/>
5.Standard Square libs (okhttp,dagger,picasso)<br/>


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
