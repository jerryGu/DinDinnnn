# DinDinnnn

## Main functions
Overall it is a data-driven app, The principle is to make UI reactive due to data change.

### Order list
  * each order has a count down timer.
  * When it reaches alert time, play a notification sound & show a toast.
  * Then 1 min after alerting, it'll be expired.
  * Timer will not stop until it's expired or you quit the app.
      * Why stopping the timer when quit? because of the mock data the app will get the new data when you open it again.
      * The real scenario would be something like that the elapsed time could be persistent locally or remotely.
      
### Ingredient List
  * Mocked 3 tabs with 3 different dataset. (The code structure is flexible to add tabs based on API calls)
  * Text search function
    * Still mocking data from API calls.
    * Each time text changed, it'll trigger a LiveData so that each tab as observer could be triggered to do re-fetching from API.

## Architecture 
* Technologies
    * Hilt + Retrofit + Flow + LiveData
* Layers
    * Repository
    * API service (Retrofit2)
    * ViewModel
    * Fragment + databinding


### Others
* MVVM architecture. Here I tried Hilt as DI tool and looks it's a bit easier than Dagger.
* I use Flow + LiveData instead of RxJava in order to just try the new technology.
* Sorry that I wasn't able to spare time on unit test.
      
  
