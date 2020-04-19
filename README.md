# WTest

## About
Mobile developer test for Wingman

## Test about
- For README of the test you can check [here](Enunciado-Mobile-Dev.pdf)
- There are 4 bottombar screens. Each one for one exercise. Which means (first, second, third, fourth)
When open the app it checks if you already have all the postal codes in our database(ROOM). If it isn't will download all the data

## Exercises
### Exercise 1
- You can check on tab 1 (First)
- We have a loading state to know if it's **downloading**, **fetching**, **initial**
- You need to type at least 3 characters to be able to search (As we have more thank 300k, we need to do some more specific)
- The search is limited for 10k per each search

### Exercise 2
- You can check on tab 2 (Second)
- If the build was in flavor (reactnative) show an image of react native on top.
- If the build was in flavor (flutter) show an image of flutter on top.
- When scroll down recyclerview it'll show an white topbar with black title.

### Exercise 3
- You can check on tab 3 (Third)
- You have a sequencial edittext (Normal, Number, AllUpperCase)
- The keyboard is not overriding the inputs on recyclerview

### Exercise 4
- You can check on tab 4 (Fourth)
- Contains a webview with a dynamic link customized on `build.gradle`
- For you can generate builds to test each one
  - ./gradlew assembleReactNativeDebug (For React Native Flavor)
  - ./gradlew assembleFlutterDebug (For Flutter Flavor)
- You can also download the app 
  - [Flavor - Flutter](app-flutter-debug.apk)
  - [Flavor - React Native](app-reactnative-debug.apk)

## Structure
- data
  - Contains ROOM framework and repositories DAO's
  - Entities
- network
  - Contains Retrofit layer
- ui
  - Activities
  - Fragments
  - ViewModels
- utils
  - Kotlin extensions
- MainApplication / MainActivity

