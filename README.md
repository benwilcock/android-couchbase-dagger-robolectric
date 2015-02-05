#Android: Couchbase TDD Sample#

This sample project integrates Couchbase, Robolectric and Dagger into an Android / Gradle project so that unit testing can occur without the need for a device or emulator.

##Background##

I need a database for my [TripComputer app](https://play.google.com/store/apps/details?id=com.soagrowers.android.tripcomputer) so that users can keep a log of their Journeys. I could use SQL Lite, but I prefer not to use SQL if possible because you're forced to maintain a fixed schema and because SQL Lite doesn't have any out of the box data replication capabilities.

[Couchbase for android](http://developer.couchbase.com/mobile/get-started/get-started-mobile/android/index.html) is a great option, but because Couchbase lite's Database manager is *Final* and requires native code, it's not easy mock or to integrate with Robolectric. 

Therefore, in order to support off-device Java VM based testing with [Robolectric](http://robolectric.org) it is necessary to write custom interfaces and use a dependency injection framework that will allow the injection of mock objects when testing. To achieve this 'dependency injection', I've introduced the [Dagger](https://github.com/square/dagger) framework into the code.

##Software Versions##

1. [Couchbase-lite 1.0.3.1](http://developer.couchbase.com/mobile/get-started/get-started-mobile/android/index.html)
2. [Robolectric 2.4](http://robolectric.org/quick-start/)
3. [Dagger 1.2.2](https://github.com/square/dagger)
4. Mockito 1.10.19
5. Android Studio 1.1 Beta 3

##About The Sample App##

The App here is super simple. When the user clicks the **Save** button on the screen, in the background a new document (technically a `java.util.Map`) is created and saved to the embedded Couchbase NoSQL database. While saving the document, Couchbase automatically assigns it an ID and it is this ID that is displayed to the user on the screen after they click the Save button. The document id's in Couchbase take the form of GUID's.

###The App Code###
Roughly speaking, in the `app` codebase you'll see the following...

1. `MyActivity.java` is a simple Android action bar activity that extends a `BaseActivity` and requires a `PersistanceManager` to be injected at runtime so it can talk to the database.

2. `PersisitanceManager.java` is a class that acts as a DAO object to `MyActivity`, managing the persistence of 'Map' objects. It offers only INSERT and GET operations in this sample and requires a `PersistanceAdapter` implementation to be injected into it.

3. `PersistanceAdapter.java` is an interface that defines INSERT and GET operations on `Map` objects. This interface is required later when mocking & testing.

4. `CouchbasePersistanceAdapter.java` is a concrete implementation of the `PersistanceAdapter` interface. It utilises Couchbase and depends on a couchbase `Database` object which must be constructed by Dagger and injected into it.

5. The injectable objects that require non-trivial instantiation (like the Couchbase `Database` object for example) are defined by `@Provides` methods in a Dagger `@Module` in the `MyActivityModule` class.

At runtime, Dagger, `MyActivity`, `BaseActivity` and the `App` application classes take care of constructing an `ObjectGraph` for the application and inserting the required dependencies so that all the various `@Inject` requirements can be met. The "Instrumentation (integration) Tests" in the Android App gradle project test that this integration and dependency injection is working as expected.

###The Robolectric Tests###

However, because its often desirable to perform testing without a device or emulator, there is also a set of Robolectric tests for the App. 

In the `app-test` gradle project you'll see the following...

1. `MyTestActivity.java` extends the MyActivity class and `@Overrides` the `getModules()` method. This method constructs and returns a `TestMyActivityModule` instance. `TestMyActivityModule` is an inner class which defines an alternative (overriding) Dagger `@Module` that can also provide a `PersistanceManager` for injection into the `MyTestActivity` when testing. This module `@Provides` a fake, programmable `PersistenceManager` _mock_, not a real persistance manager as is expected under normal conditions.

2. `MyActivityRobolectricTest.java` is a standard Robolectric test, but it's Robolectric controller builds a new `MyTestActivity`. The method `testClickingSaveButtonSavesMapAndDisplaysId()` tests that clicking the _Save_ button has the required affect by pre-programming the `PersistenceManager` mock with behaviours and then verifying that this mock has indeed been called by the Activity as expected.

##Running the Sample##

To run the sample just clone or download [this repository](https://github.com/benwilcock/android-couchbase-dagger-robolectric) and then execute the following commands. For completeness, i've included some Instrumentation Tests also (you can run them with `gradlew connectedCheck` assuming an emulator or device is present).

```
gradlew clean
gradlew assemble
gradlew check
gradlew connectedCheck (this is optional and assumes a device is present)
```

##Notes##
Dagger is about to be replaced by Dagger 2.0, but this timeline for release is unclear and has slipped several times last year already, hence the use of Dagger 1.2.2.

##Acknowledgements##

Many thanks to Andy Dennie for his [Dagger examples on GitHub](https://github.com/adennie/fb-android-dagger). These were really helpful to this Dagger noob when trying to understand how to integrate Dagger with Android.

##About the Author##

Ben Wilcock is the developer of [TripComputer](https://play.google.com/store/apps/details?id=com.soagrowers.android.tripcomputer) , the only distance tracking app for Android with a battery-saving LOW POWER mode. It’s perfect for cyclists, runners, walkers, hand-gliders, pilots and drivers. It’s free! Download it from the Google Play Store now:-

[Get the App on Google Play](https://play.google.com/store/apps/details?id=com.soagrowers.android.tripcomputer)

You can connect with Ben via his [Blog](http://benwilcock.wordpress.com), [Website](http://www.soagrowers.com), [Twitter](https://twitter.com/benbravo73) or [LinkedIn](http://uk.linkedin.com/in/benwilcock).