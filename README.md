#Couchbase-lite, Dagger and Robolectric Integration#

This project is designed to illustrate the integration of Couchbase-lite into an Android / Gradle project in such a way that allows Java VM unit testing with Robolectric.

##Software Versions##

Tested with:-

1. Android Studio 1.1 Beta 3
2. Robolectric 2.4
3. Couchbase-lite 1.0.3.1
4. Dagger 1.2.2
5. Mockito 1.10.19

##About##

Because Couchbase lite's Database manager is *Final* and requires native code, its not easy mock or to integrate with Robolectric. Therefore, in order to allow Java VM testing with Robolectric it is necessary to use custom interfaces and a dependency injection framework that will allow the injection of test classes when testing. To achieve this, Dagger 1.2.2 has been used in this example.

##App Use Case##

The App here is super simple. When the user clicks the **Save** button on the screen, a new record (a Map) is created and saved to Couchbase's built in database. In doing so, Couchbase automatically assigns the new record an ID and it is this ID that is displayed to the user after they click the button. The ID's take the form of GUID's.

##Approach Taken##

###The App Code###
Roughly speaking, in the `app` codebase you'll see the following...

1. `MyActivity.java` is a simple Android activity that requires a `PersistanceManager` to be injected at runtime.
2. `PersisitanceManager.java` is a class that acts as a DAO object to my activity, managing the persistence of 'Map' objects. It offers INSET and GET operations and requires a `PersistanceAdapter` implementation to be injected into it.
3. `PersistanceAdapter.java` is a custom interface that defines INSERT and GET operations on `Map` objects. This interface is required later when mocking & testing.
4. `CouchbasePersistanceAdapter.java` is a concrete implementation of the `PersistanceAdapter` interface that utilises Couchbase and requires a couchbase `Database` object to be constructed and injected into it.
5. The injectable classes that require non-trivial custom setup (like the Couchbase `Database` class etc.) are defined by `@Provides` methods in a Dagger `@Module` in the `MyActivityModule` class.

At runtime, Dagger, the `AbstractBaseActivity` class and the `App` application class take care of constructing an `ObjectGraph` and inserting the required `@Module` classes so that `@Inject` requirements can be met. The integration tests in the App project test that this integration is working as expected.

###The Robolectric Tests###
In the `app-test` codebase you'll see the following...

1. `MyTestActivity.java` extends the MyActivity class and `@Overrides` the `getModules()` method. This method constructs and returns a `MyTestActivityModule` instance.
2. `MyTestActivityModule.java` defines a Dagger `@Module` that can construct a `PersistanceManager` class intended for injection into the `MyTestActivity`. It `@Provides` a fake, programmable `PersistenceManager` _mock_ (not a real persistance manager as is expected under normal conditions).
3. `MyActivityRobolectricTest.java` is a standard Robolectric test, but it's Robolectric controller builds a new `MyTestActivity`. The method `testClickingSaveButtonSavesMapAndDisplaysId()` tests the _Save_ button has the required affect by pre-programming the `PersistenceManager` mock and verifying that this mock has indeed been called by the activity as expected.

##Running the Sample##

To run it just clone or download and extract the ZIP then execute the following commands (assuming an emulator or a device is present)...

```
gradlew clean
gradlew assemble
gradlew check
gradlew connectedCheck
```

##Known Issues##

For some reason the injection mechanism isn't injecting the 'PersistanceManager' implementation into the Robolectric test at runtime despite the presence of the @Inject annotation and a declaration in the MyTestActivityModule that specified that this class should be injected.

However, it does inject the 'Activity's' PersistanceManager with the mock successfully, so a workaround is to use the Activities 'manager' field instead even though this is sub-optimal.

##Notes##
Dagger is about to be replaced by Dagger 2.0, but this timeline for release is unclear and has slipped several times last year already, hence the use of Dagger 1.2.2.