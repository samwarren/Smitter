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

Thursday
Meeting with android group

Friday: research into threading, please see the note below

IMPORTANT NOTE ON THREADING: firebase by default creats a separate thread for all of its operations,
therefore it is not necessary (I think) for me to create a separate thread for it.
For further reading: https://www.firebase.com/docs/java-api/javadoc/index.html?com/firebase/client/Config.html
and although this is a sketchy website, it has useful information
http://newtips.co/st/questions/31922085/how-to-save-retrieved-data-to-variable-outside-the-ondatachange-method-in-fireba.html


Testing to do: test that upon creating username u and password p it is saved in firebase, you can login as u with p, that u can post a tweet as u and have it connected
test multiple active users

Stretch goals: make it so that a user has a username rather than just their email, query for a specific tweet hashtag

Questions for trov
Best practice for a constant like Firebase url?