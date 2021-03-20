<h1 align="center"> Impression </h1> <br>

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Screenshot](#Screenshot)
- [Installation](#Installation)
- [Use](#Use)
- [Usage](#Usage)
- [Troubleshooting](#Troubleshooting)
- [Contact](#Contact)

## Introduction

Impression is an application of translation class to assist in learning Chinese or English,Built with Android Studio.

**Available for Android now.**


## Features

A few of the things you can do with Impression:

* translation of Chinese and English Texts
* chinese english phonetic translation
* translation history
* translation notebook
* review notes
* analysis and learning curve

Some features that may be supported in the future:

* support more languages
* cloud backup
* photo translation

## Screenshot 
<p float="left">
<img src="https://github.com/caiji200/impression/blob/master/screenshot/1.jpg" width="200" height="320" alt=""/>
<img src="https://github.com/caiji200/impression/blob/master/screenshot/2.jpg" width="200" height="320" alt=""/>
<img src="https://github.com/caiji200/impression/blob/master/screenshot/3.jpg" width="200" height="320" alt=""/>
<p>
  
<p float="left">
<img src="https://github.com/caiji200/impression/blob/master/screenshot/4.jpg" width="200" height="320" alt=""/>
<img src="https://github.com/caiji200/impression/blob/master/screenshot/5.jpg" width="200" height="320" alt=""/>
<img src="https://github.com/caiji200/impression/blob/master/screenshot/6.jpg" width="200" height="320" alt=""/>
<p>
  
<p float="left">
<img src="https://github.com/caiji200/impression/blob/master/screenshot/7.jpg" width="200" height="320" alt=""/>
<img src="https://github.com/caiji200/impression/blob/master/screenshot/8.jpg" width="200" height="320" alt=""/>
<p>

## Installation

### Tools
First of all, you should configure your own development environment, which requires the following tools
1. Java 1.8 
2. Android Studio 
3. Available Internet
4. Android Mobile Phone or simulator

### Build Process

- Clone or download the repo
- Import project thought Android Studio (File->Open->Choose directory of project)
- Waiting for Android Studio download of dependency Library,If your network is not in good condition,
and want to use your local gradle or build tools, just modify gradle/gradle-wrapper.properties files and build.gradle file of project.
But we recommend that you use the version of this project or a higher version.
- Sync Project with Gradle Files

### Install App

- Connect your Android Mobile Phone with Usb or launch a simulator,If the connection is successful, 
you will see the connected device in Android studio. 
- Run App ,If successful, you will see the first page of the launch.


### Use
When you install successfully, the first thing you will see is the welcome page.Click try Now to 
experience the function of App.App provides basic translation functions,Its home page consists of 
three parts: text translation, voice translation and overview page.

For example,If you want to learn Chinese,you need to switch the language at the top of the page to 
the form of English to Chinese,then input your translate text.The translation results will be saved 
automatically. You can also add the words or sentences you are interested in to your own notebook.

For the voice function, you only need to open the speech recognition page, and the result will 
automatically respond to the corresponding language.

In the overview page, you can see some statistical information of data, such as how much data there 
is in the notebook and the situation of your review. You can click the learn button to review the 
contents of the notebook. On the review page, you can follow up the new words. At the same time,
the review results will be automatically counted, and the statistical information will be displayed 
through the icon on the analysis page.


## Usage

Want to contribute? Great!

To fix a bug or enhance an existing module, follow these steps:

- Fork the repo
- Create a new branch (`git checkout -b improve-feature`)
- Make the appropriate changes in the files
- Add changes to reflect the changes made
- Commit your changes (`git commit -am 'improve feature'`)
- Push to the branch (`git push origin improve-feature`)
- Create a Pull Request

### Bug / Feature Request

If you find a bug (the website couldn't handle the query and / or gave undesired results), kindly open an issue [here](https://github.com/caiji200/impression/issues/new) by including your search query and the expected result.

If you'd like to request a new function, feel free to do so by opening an issue [here](https://github.com/caiji200/impression/issues/new). Please include sample queries and their corresponding results.

## Troubleshooting
build this app need minSdkVersion=21 and targetSdkVersion=29,If your Android studio has not downloaded 
this version of the SDK,please click the SDK Manager and download 

<img src="https://github.com/caiji200/impression/blob/master/screenshot/t_1.jpg" width="640" height="320" alt=""/>
<img src="https://github.com/caiji200/impression/blob/master/screenshot/t_2.jpg" width="640" height="320" alt=""/>

If you want to use the local gradle and build tools, please change them to your own version.

<img src="https://github.com/caiji200/impression/blob/master/screenshot/t_3.jpg" width="640" height="320" alt=""/>
<img src="https://github.com/caiji200/impression/blob/master/screenshot/t_4.jpg" width="640" height="320" alt=""/>


## Contact 
if you have any question ,please contact with me.
jasoncai1995@gmail.com


## License

MIT License

Copyright (c) 2021 caiji200

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
