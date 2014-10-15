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

Getting started to work on the project with Android Studio should be really straight forward. Download and install Android studio, make sure that you have the Android SDKs and an emulator set up and then click on “Open Project”, browse to the git repository folder and click on “Open”. You should be ready to go. 