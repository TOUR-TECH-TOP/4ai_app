# 4ai Android ChatSdk 
## Description

4ai Android Sdk Project provides an intuitive interface to integrate the 4ai chat in your app

### Prerequisites

Before you begin, ensure you have met the following requirements:
- [Android Studio](https://developer.android.com/studio) (for Android projects)

### Gradle

Add this in project level build.gradle,


repositories {

    mavenCentral()

    maven { url 'https://jitpack.io' }
}


Add this dependency in app level build.gradle


dependencies {
    
    implementation 'com.github.TOUR-TECH-TOP:4ai_app:v1.0.0'
}

### Usage

        ChatActivity.start(context, "YOUR_SITE_ID")




