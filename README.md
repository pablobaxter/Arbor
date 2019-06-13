# Arbor Logging Utility
[ ![Download](https://api.bintray.com/packages/soaboz/Arbor/com.frybits.arbor-android/images/download.svg) ](https://bintray.com/soaboz/Arbor/com.frybits.arbor-android/_latestVersion)

Arbor is an open-source logging library based on Jake Wharton's [Timber](https://github.com/JakeWharton/timber).

It is built using Kotlin Multiplatform, with a shared code base and variants for Android and Native (iOS).

Arbor allows for injecting multiple loggers (Branch) that can all receive the same log message, but perform different actions. Basically, a single log statement can be consumed by multiple Branch classes.

Supported Platforms:
- Android API 21+
- iOS (`ios_arm64` and `ios_x64` targets)

## Download
#### Android

```groovy
implementation 'com.frybits.arbor:android:LATEST_VERSION'
```

#### iOS

`//TODO Coming soon!`

## Usage

#### Android

Kotlin
```kotlin

//Creating a custom branch
object SingletonBranch : Branch(Level.Info) {

    private val logFile: File = File("logPath")
    private var logWriter: PrintWriter? = null
    
    override fun onAdd() {
        // Perform initializations here
        logWriter = logFile.printWriter()
    }

    override fun onRemove() {
        // Perform any necessary tear downs here
        logWriter?.let {
            it.flush()
            it.close()
        }
    }

    override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
        //Take care of logging!
        logWriter?.println("${level.name}, $tag, ${message ?: throwable}")
    }
}

//Showing how to add/remove/log
fun someFunction() {
    Arbor.w("SomeTag", "Warning!") //This will not be logged, as Arbor is currently empty
    Arbor.addBranch(SingletonBranch) //Adding SingletonBranch to Arbor. All previous logs are ignored by this branch.
    Arbor.i("SomeTag", "This is a log message") //This will be logged in SingletonBranch
    Arbor.v("SomeOtherTag", "This is also a log message", Exception("error")) //This is filtered and won't be logged due to Verbose log level being broader than Info
    Arbor.removeBranch(SingletonBranch) //This removes the branch from Arbor
    //All other log statements are ignored
}
```

Java

**Note:** I'm using singletons here for parity with the Kotlin code, and also to show how to handle adding/removing custom branches. Having a branch be a singloton is not required.
```java

//Creating a custom branch
public class SingletonBranch extends Branch {

    private static SingletonBranch INSTANCE;

    public static SingletonBranch getInstance() {
        if (INSTANCE == null) {
            synchronized (SingletonBranch.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SingletonBranch();
                }
            }
        }
        return INSTANCE;
    }
    
    private final File logFile;
    private PrintWriter logWriter;

    private SingletonBranch() {
        super(Level.Debug);
        logFile = new File("logPath");
    }

    @Override
    public void onAdd() {
        // Perform initializations here
        try {
            logWriter = new PrintWriter(logFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRemove() {
        // Perform any necessary tear downs here
        if (logWriter != null) {
            logWriter.flush();
            logWriter.close();
        }
    }

    @Override
    public void onLog(@NotNull Level level, @NotNull String tag, @Nullable String message, @Nullable Throwable throwable) {
        //Take care of logging!
        if (logWriter != null) {
            logWriter.println(level.name() + ", " + tag + ", " + (message != null ? message : throwable));
        }
    }
}

class MainActivity extends Activity {
    
    //Showing how to add/remove/log
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Arbor.w("SomeTag", "Warning!"); //This will not be logged, as Arbor is currently empty
        Arbor.addBranch(SingletonBranch); //Adding SingletonBranch to Arbor. All previous logs are ignored by this branch.
        Arbor.i("SomeTag", "This is a log message"); //This will be logged in SingletonBranch
        Arbor.v("SomeOtherTag", "This is also a log message", Exception("error")); //This is filtered and won't be logged due to Verbose log level being broader than Info
        Arbor.removeBranch(SingletonBranch); //This removes the branch from Arbor
        //All other log statements are ignored
    }
}
```

#### iOS

*COMING SOON!*

## About

Arbor is a logging utility for Android and iOS.

#### Android

In terms of functionality, adding/removing/logging can occur on any thread. This is done by the use of Kotlin's `Channel` library, which accepts Action events from Arbor and consumes them in the order received in a background thread. This helps guarantee that a Branch's `onAdd` function is always called before the first `onLog`, and that `onLog` will no longer be called once `onRemove` is called.

The callbacks `onAdd` and `onRemove` are made on the UI (Main) thread, but `onLog` is made in a background thread owned by the `Branch` class.

Due to the asynchronous nature of this type of logging, all calls to `Arbor` return immediately.

**Note:** Log ordering is dependent on when the log statement is received by Arbors `Channel`.

#### iOS

Like the Android version, adding/removing/logging can occur on any thread, however, all callbacks are made on the main queue. In the future, `onLog` will be called from another queue.

#### Future Plans

- Finish README to include iOS
- Find a way to distribute Arbor iOS Framework
- Make both iOS and Android logging work with Kotlin Coroutines
- Automate project build using CI

## License
MIT License

Copyright (c) 2019 Pablo Baxter

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

