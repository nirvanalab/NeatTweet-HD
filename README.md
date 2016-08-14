# Project 4 - *NeatTweet HD*

**NeatTweet HD** is an android app that allows a user to view home and mentions timelines, view user profiles with user timelines, as well as compose and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **25** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] The app includes **all required user stories** from Week 3 Twitter Client
* [x] User can **switch between Timeline and Mention views using tabs**
  * [x] User can view their home timeline tweets.
  * [x] User can view the recent mentions of their username.
* [x] User can navigate to **view their own profile**
  * [x] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [x] User can **click on the profile image** in any tweet to see **another user's** profile.
 * [x] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
 * [x] Profile view includes that user's timeline
* [x] User can [infinitely paginate](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView) any of these timelines (home, mentions, user) by scrolling to the bottom

The following **optional** features are implemented:

* [x] User can view following / followers list through the profile
* [x] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [x] When a network request is sent, user sees an [indeterminate progress indicator](http://guides.codepath.com/android/Handling-ProgressBars#progress-within-actionbar)
* [x] User can **"reply" to any tweet on their home timeline**
  * [x] The user that wrote the original tweet is automatically "@" replied in compose
* [ ] User can click on a tweet to be **taken to a "detail view"** of that tweet
 * [x] User can take favorite (and unfavorite) or retweet actions on a tweet
* [x] Improve the user interface and theme the app to feel twitter branded
* [x] User can **search for tweets matching a particular query** and see results
* [x] Usernames and hashtags are styled and clickable within tweets [using clickable spans](http://guides.codepath.com/android/Working-with-the-TextView#creating-clickable-styled-spans)

The following **bonus** features are implemented:

* [x] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [ ] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [x] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate.
* [x] User can view their direct messages 

The following **additional** features are implemented:

* [x] Customized the page sliding tabar with icons, and custom color. The icon dynamically changes based on the current selected tab.
* [x] Added ability to compose a tweet or reply to a tweet from any tab
* [x] Added Usertimeline and Direct Messages part as tabs
* [x] Integrated [Navigation drawer](http://guides.codepath.com/android/Fragment-Navigation-Drawer), when a user clicks his own profile pic, it brings out a custom designed drawer .
* [x] Search functionality replaces the tab content dynamically with search results and the corresponsding header and on cancelling search it resets back to home timeline with corresponding icon
* [x] Shows list of Favorites and Retweets (only for the current user) in the user detail page
* [x] Integrated [collapsable toolbar with coordinator layout](http://guides.codepath.com/android/Handling-Scrolls-with-CoordinatorLayout) and scroll effects
* [x] Dynamically changes the text color for the currently selected tab




## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [Android ScalableView](https://github.com/yqritc/Android-ScalableVideoView) - Play video in recycler view
- [Pager Sliding Tab strip](https://github.com/astuetz/PagerSlidingTabStrip) - Adds a tab strip which slides
- [Butterknife](http://jakewharton.github.io/butterknife/) - Reduces view boilerplate code
- [Glide](https://github.com/bumptech/glide)
- [Parceler](http://guides.codepath.com/android/Using-Parceler)
- [View Pager Transforms](https://github.com/ToxicBakery/ViewPagerTransforms)

## License

    Copyright [2016] [Vidhur Voora]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
