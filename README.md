

THREADING: firebase by default creats a separate thread for all of its operations,
therefore it is not necessary for me to create a separate thread for it (and doing so causes concurrency issues).
For further reading: https://www.firebase.com/docs/java-api/javadoc/index.html?com/firebase/client/Config.html
and although this is a sketchy website, it has useful information
http://newtips.co/st/questions/31922085/how-to-save-retrieved-data-to-variable-outside-the-ondatachange-method-in-fireba.html


Tests: I have created 2 JUnit tests which test some of the basic operations in Tweet and Login_Activity.
I have yet to implement any AndroidTests.
Testing to do: test that upon creating username u and password p it is saved in firebase, you can login as u with p, that u can post a tweet as u with author p,


Tweet Cache: I was able to implement GSON storage of tweets in shared preferences; however,
I was not able to integrate displaying the contents of this storage properly with the firebase backend.
Instead, I used the firebase library to create a cache of the tweets on the user's device. I verified that it works by running
Smitter on the emulator without wifi and it loads old tweets. There are two issues with this approach however:
First, the assignment asked for old tweets to be loaded onto the phone and then for only new tweets to be queried
currently my approach loads old tweets and then fetches remaining tweets. The advantage to my approach is that
a user would have access to all the tweets and I have set the cache's size to be capped at 5MB so if too many people
are tweeting, it isn't possible for tweet storage to swamp the user's device. The second issue, is that I have
not been able to verify that I am first loading the stored tweets and then querying the database, it is possible
that the cache is  only being utilized when the device is offline.


Questions for trov
Best practice for a constant like Firebase url?


Journal:
Friday 2/12
Interview with Jedidja
Research into data storage, decided to use firebase for storage
Sunday 2/14
-	Set up project, git and imported firebase lib
-	Download emulator b/c I currently do not have android device

Thursday 2/18
 - basic login

 Friday 2/19
- research in testing

Monday 2/22
-implementing creating new user account
-still need to improve UI for new user account, find place to display invalid email addr and account
creation error

Tuesday 2/23
implementing list view for tweets

Wednesday 2/24
git restructuring to include java files

Thursday 2/25
Meeting with android group

Friday: 2/26 research into threading, please see the note below

Thursday 3/11: implemented JUnit tests and tweet caching