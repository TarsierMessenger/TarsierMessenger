# Building

The project build system is relying in [`Gradle`](http://www.gradle.org/). 

## Building from the command line

Assuming that your system has Gradle installed, the project can be built by running the Gradle Wrapper that’s at the root of the project.

```bash
./gradlew build
```

A complete list of tasks can be obtained by running

```bash
./gradlew tasks
```

If you have a device with ADB enabled plugged into your machine or the emulator running, you can install a build directly from the command line with the install command

```bash
./gradlew installDebug
```

## Building using an IDE 

[Android Studio](https://developer.android.com/sdk/installing/studio.html) is backed by Gradle.

Getting started to work on the project with Android Studio should be really straight forward. Download and install Android studio, make sure that you have the Android SDKs and an emulator set up and then click on `Open Project`, browse to the git repository folder and click on `Open`. You should be ready to go.

# Checkstyle

## Checkstyle from the command line

You can run Checkstyle from the command line by typing:

```bash
./gradlew check
```

## Checkstyle with Android Studio

1. Install the Checkstyle-IDEA plugin by going to `Android Studio > Preferences...`, then `Plugins`, and finally `Browse repositories...`. Here search for `Checkstyle-IDEA` and click `Install`.
2. Restart Android Studio
3. Re-open the preferences, and select `Checkstyle`.
4. Check `Scan test classes`.
5. Add a new configuration file by clicking `+`
6. Type a name for the configuration. Eg. `SwEng Checkstyle`.
7. Click `Browse` and select `app/config/checkstyle/checkstyle.xml`, then click `Next`.
8. Activate the newly created configuration, and click `OK`.

# Testing

## Testing from the command line

Assuming that your system has Gradle installed, and that you have a device with ADB enabled plugged into your machine or the emulator running, the tests can be run by running the Gradle Wrapper that’s at the root of the project.

```bash
./gradlew connectedCheck
```

## Testing from Android Studio

Before you can build the project with Android Studio, you need to add a testing configuration.

1. Start by clicking `Run -> Edit Configurations…`

    ![](http://cl.ly/image/1X2u430F1a0W/edit-configurations.png)

2. Now select `+ -> Android Tests` from the upper left hand corner and select `Android Tests` and name your new test configuration `test` or something equally relevant.

    ![](http://cl.ly/image/461Q0n1Z3j1x/Screen-Shot-2014-01-24-at-6.42.43-PM.png)

3. This will create a new test project configuration like this:

    ![](http://cl.ly/image/1B3Q2Z3r2r1O/Screen-Shot-2014-01-24-at-6.46.19-PM.png)

4. Select your current app module from the drop down menu.

    ![](http://cl.ly/image/401k3X1R072P/Screen-Shot-2014-01-24-at-6.35.01-PM.png)

5. Next, select the `All in Package` option and navigate to your `test` folder you just created. You can also select `All in Module` and Android Studio will automatically find any test inside your whole Module! You can also get more specific and select `Class` or even `Method` option to narrow the scope of your testing down further.

    ![](http://cl.ly/image/0V3E1R0t060E/settings.png)

6. Paste the following (or browse for it) in the `Specific instrumentation runner`:

        com.google.android.apps.common.testing.testrunner.GoogleInstrumentationTestRunner


7. I also like to set the run configuration to `Show Chooser` so I can decide how to run the tests when needed:

    ![](http://cl.ly/image/0J3c1c2s3D29/configurations.png)

8. Now click `Apply` and then `Close.` You should now see your test cases as being a runnable project configuration in the bar across the top of your Android Studio instance.

    ![](http://cl.ly/image/270w0v0F2u3J/test-config-1.png)

Adapted from http://rexstjohn.com/unit-testing-with-android-studio/.

