Goals
=====

The goal of this project is to make using selenium web driver more convenient.
Also this project will provide useful features for many components like in jquery ui, primefaces etc.



First steps
===========


Before using this library you should prepare your system environment.
The main browsers that were tested are: Chrome/Chromium, Firefox, Internet explorer v8.0 and Opera v 12.15.
Make sure that you installed the correct version of your browser and proceed to Setup section.

Setup
-----

1.  Download the required artifact for your browser(For chrome/chromium and IE only)
    +   For chrome/chromium download 'chromedriver' for your platform from [here](http://chromedriver.storage.googleapis.com/index.html)
    +   For Internet Explorer download 'IEDriverServer' from [here](http://selenium-release.storage.googleapis.com/index.html).
It's better to download the version of driver similar to selenium-java version which is currently in use.
2.  Extract downloaded artifacts to any folder and add this folder to your 'PATH' variable in the system. (For chrome/chromium and IE only)
3.  Go to your web driver page on selenium ([for example](https://code.google.com/p/selenium/wiki/ChromeDriver)) and see the requirements for your browser executable location, troubleshooting etc.
4.  If you are using opera the best way to make it work is to create a system variable 'OPERA_PATH' that points at the opera executable.


        For example: OPERA_PATH='C:\Program Files\Opera\opera.exe'


5.  For latest Chrome versions on windows the default installation location is Program Files,
    but web driver requires it to be places in your user's home folder (see more details on ChromeDriver page).
    If this is the case uninstall chrome, enable UAC in windows (if it is disabled) and then install chrome again.
    When UAC will prompt your installation confirmation simply cancel it, and then chrome will be installed into you user's home folder.


The setup should be done by now you can run DepTest for your desired browser and see if all the tests passed.
For troubleshooting refer to Driver implementation of your choice on selenium forums.



Build
=====
For building purposes this project uses maven, so all you need to create a jar file is to run

        mvn clean package

That pretty much covers the environment setup and building the artifact. The user guide describing api and other stuff comming soon.

Refer to javadocs if you have any troubles. Javadocs can be build using:

        mvn javadoc:javadoc

Reference code with comments can be found in test folder of this project
