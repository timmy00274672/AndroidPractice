This README file includes three part:

- Service
- Thread
- Broadcast

## Contents

- Preface
	- The way we study framework
	- Comparison
- Service
	- Overview 
	- Lifecycle
	- Permission 
	- Started Service

## Preface

### The way we study framework

When we study each compenent(like activity, service, ...), we should understand the mechanism. For example, we need to understand the lifecyle of activity, and thus we will know how to use the lifecycle callback function, and further know the occassion of each function.

### Comparison

**Broadcast** is used to notify other app or component that something change, including the configuration, system information, and so on. We should **not** use `BroadcastReceiver` to do time-consuming work, like downloading or refresh database. Note: any asynchronous code should **not** in `BroadcastReceiver#onReceive` because after this fucntion returns, this receiver will die(lifecycle issue).

If we need to do time-comsuming work or something background, we can use **Service**. Service comes in :

- Started : The component calling the service and the service are independent in term of lifecycle.
- Bound : As the component unbindes the service or is destroyed, the service bound will die. The kind of service provides a interface `IBind` for client and server.   

We should notice that the service is running in the *main thread* of the app's process. Therefore, if we use service to run some CPU-intensive work, it may cause ANR(application not responding). In this case, we consider useing anthoer **thread**, to fulfill, which comes in:

- Normal thread: created when `onCreate()`, started when `onStart()`, stopped when `onStop()`
- AsyncThread
- HandlerThread

## Service

#### Overview

A Service is an application component representing either an application's desire to perform a **longer-running** operation while not interacting with the user or to supply functionality for other applications to use. Each service class must have a corresponding `<service>` declaration in its package's *AndroidManifest.xml*. Services can be started with

- `Context.startService()`
- `Context.bindService()`.

Note that services, like other application objects, run in the **main thread** of their hosting process. This means that, if your service is going to do any CPU intensive (such as MP3 playback) or blocking (such as networking) operations, it should *spawn its own thread* in which to do that work. The [IntentService][IS] class is available as a standard implementation of Service that has **its own thread** where it schedules its work to be done.

[IS]: http://developer.android.com/reference/android/app/IntentService.html

#### Lifecycle:

From API:

The Android system will attempt to keep the process hosting a service around as long as the service has been started or has clients bound to it. When running low on memory and needing to *kill existing processes*, the priority of a process hosting the service will be the higher of the following possibilities:

- If the service is currently executing code in its `onCreate()`, `onStartCommand()`, or `onDestroy()` methods, then the hosting process will be a foreground process to ensure this code can execute **without** being killed.
- If the service has been started, then its hosting process is considered to be less important than any processes that are currently visible to the user on-screen, but more important than any process **not** visible. Because only a few processes are generally visible to the user, this means that the service should not be killed except in extreme low memory conditions.
- If there are clients **bound** to the service, then the service's hosting process is never less important than the most important client. That is, if one of its clients is visible to the user, then the service itself is considered to be **visible**.
- A started service can use the `startForeground(int, Notification)` API to put the service in a *foreground state*, where the system considers it to be something the user is actively aware of and thus not a candidate for killing when low on memory. (It is still theoretically possible for the service to be killed under extreme memory pressure from the current foreground application, but in practice this should not be a concern.) 

Note this means that most of the time your service is running, it may be killed by the system if it is under heavy memory pressure. If this happens, the system will later **try to restart** the service. An important consequence of this is that if you implement onStartCommand() to schedule work to be done asynchronously or in another thread, then you may want to use **START_FLAG_REDELIVERY** to have the system re-deliver an Intent for you so that it does not get lost if your service is killed while processing it.

Other application components running in the same process as the service (such as an Activity) can, of course, increase the importance of the overall process beyond just the importance of the service itself. 

![Service lifecycle](http://www.tutorialspoint.com/android/images/android_service_lifecycle.jpg)

#### Permission

From API:

**Global** access to a service can be enforced when it is declared in its manifest's `<service>` tag. By doing so, other applications will need to declare a corresponding `<uses-permission>` element in their own manifest to be able to start, stop, or bind to the service.

As of GINGERBREAD, when using Context.startService(Intent), you can also set Intent.FLAG_GRANT_READ_URI_PERMISSION and/or Intent.FLAG_GRANT_WRITE_URI_PERMISSION on the Intent. This will grant the Service *temporary* access to the specific URIs in the Intent. Access will remain until the Service has called stopSelf(int) for that start command or a later one, or until the Service has been completely stopped. This works for granting access to the other apps that have not requested the permission protecting the Service, or even when the Service is not exported at all.

In addition, a service can protect individual **IPC** calls into it with permissions, by calling the `checkCallingPermission(String)` method before executing the implementation of that call.

See the Security and Permissions document for more information on permissions and security in general. 

### Started Service

#### Lifecycle

Notice that we should stop the service if the process is finished **ourself**, or it will live forever if we don't explicit stop it.

Therefore, we should provide ways to stop the servie explicitly and stop the service itself.

From the API:

If someone calls `Context.startService()` then the system will retrieve the service (creating it and calling its `onCreate()` method if needed) and then call its `onStartCommand(Intent, int, int)`. The service will at this point **continue running** until `Context.stopService()` or `stopSelf()` is called. Note that multiple calls to Context.startService() do **not** nest; however, services can use their stopSelf(int) method to ensure the service is not stopped until started intents have been processed.

For started services, there are two additional major **modes** of operation they can decide to run in, depending on the value they *return* from `onStartCommand()`: 

- `START_STICKY` is used for services that are explicitly started and stopped as needed
- `START_NOT_STICKY` or `START_REDELIVER_INTENT` are used for services that should only remain running while processing any commands sent to them. 

	> Schedule work to be done asynchronously or in another thread, then you may want to use START_FLAG_REDELIVERY to have the system re-deliver an Intent for you so that it does not get lost if your service is killed while processing it.

#### Override method



