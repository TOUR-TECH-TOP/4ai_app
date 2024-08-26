# 4ai Android ChatSdk 
## Description

4ai Android Sdk Project provides an intuitive interface to integrate the 4ai chat in your app

### Prerequisites

Before you begin, ensure you have met the following requirements:
- [Android Studio](https://developer.android.com/studio) (for Android projects)

### Gradle

For gradle older than 7, 

Add this in project level build.gradle,

repositories {

    mavenCentral()

    maven { url 'https://jitpack.io' }
}

For gradle above 8, add the following code in settings.gradle

dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }

    }
    
}

Add this dependency in app level build.gradle


dependencies {
    
    implementation 'com.github.TOUR-TECH-TOP:4ai_app:v1.0.11'
}

### Usage



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
    
        super.onCreate(savedInstanceState)

        setContent {

        ChatActivity.start(this,"YOUR_SITE_ID")

        }
    }
}



