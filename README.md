## Splash  
Build Status: [![Build Status](https://app.bitrise.io/app/148bcaee35a29293/status.svg?token=GfxLmo6-yd14y4FlSrLM-Q&branch=develop)](https://app.bitrise.io/app/148bcaee35a29293)


## Motivation
Splash was an attempt to use the latest cutting edge libraries and tools using the [Unsplash](https://unsplash.com/) API's.

## Technologies
- UI is 100% written in Jetpack compose
- Coroutines
- Android architecture components like paging,room, lifecycle extension etc.
- Dagger 2
- Coil
- Retrofit for n/w
- Different product flavours appstore and debug to turn on / off few devtools and loggers
- Chucker to log n/w requests in notification bar
- Detekt
- Bitrise CI integrated

One interesting thing about Splash app is that, Splash has a separate module for the n/w operations so that in future it can re-used for the KMP.<br/><br/>
 
 
 ## Screenshots
 Dark theme: <br/>
 <img src="https://github.com/adb-shell/Splash/blob/develop/assets/dark-1.png" width="200">
 <img src="https://github.com/adb-shell/Splash/blob/develop/assets/dark-2.png" width="200">
 <img src="https://github.com/adb-shell/Splash/blob/develop/assets/dark-3.png" width="200">
 <img src="https://github.com/adb-shell/Splash/blob/develop/assets/dark-4.png" width="200"><br/><br/>
 
 Light theme: <br/>
 <img src="https://github.com/adb-shell/Splash/blob/develop/assets/light-1.png" width="200">
 <img src="https://github.com/adb-shell/Splash/blob/develop/assets/light-2.png" width="200">
 <img src="https://github.com/adb-shell/Splash/blob/develop/assets/light-3.png" width="200">
 <img src="https://github.com/adb-shell/Splash/blob/develop/assets/light-4.png" width="200"><br/><br/> 
 
 
 ## Setup
You can configure your API key, API secret and auth callback in `buildSrc/config.kt` and `constants.kt`.<br/>


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
