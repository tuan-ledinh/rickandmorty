# Rick and Morty

## Project characteristics and tech-stack

* 100% [Kotlin](https://kotlinlang.org/)
* Modern architecture: Clean Architecture, Model-View-ViewModel
* A single-activity architecture ([Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started))
* Dependency Injection using Hilt
* [Retrofit](https://square.github.io/retrofit/) - networking
* [Jetpack](https://developer.android.com/jetpack)
    * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - deal with whole in-app navigation
    * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - notify views about database change
    * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an action when lifecycle state changes
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way

## Future improvement

* Adding unit tests
* Support offline mode with Room and Repository pattern (https://developer.android.com/training/data-storage/room)