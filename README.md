# ArcTouch

See upcoming movies, search for your favorite one and see its description.

## Architecture
- MVVM
  - This pattern was chosen because it isolates business logic on view model from view, so it's easier to create separated tests reducing dependence of each other.

## Requirements

- Android 16+

## Used libraries

- [Fresco](http://frescolib.org/)
  - Fancy image downloader. I prefer it over others libraries, like Picasso, because it has more functions and it's easier to use in xml (data binding++). I'm using it to display the movie image.
- [Rx](https://github.com/ReactiveX/RxJava)
  - Architecture requirement for reactive apps with MVVM
- [Retrofit](http://square.github.io/retrofit/)
  - Easier restful APIs implementations
- [Dagger2](https://google.github.io/dagger/)
  - The most loved dependency injection for Android
- [Android Arch Lifecycle](https://developer.android.com/topic/libraries/architecture/index.html)
  - Helper for View Model architecture
- [Mockito](http://site.mockito.org/), [PowerMock](https://github.com/powermock/powermock), [JUnit](http://junit.org/junit4/)
  - Mock and unit testing

### Thank you for your time and I would love some code feedback! o/