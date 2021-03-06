- Data stored for every user
  - username (string) (primary key)
  - userdisplayname (string)
  - userpassword (string)
  - usermaxtraveldistance (double) (miles)
  - userlatitude (double)
  - userlongitude (double)
  - aboutMe/Bio (string)
  - Hobbies (15 bit binary string representing the following preset hobbies)
    - Swimming 
    - Reading  
    - Bicycling 
    - Hiking 
    - Camping  
    - Dancing  
    - Running  
    - Bowling 
    - Video Games   
    - Programming  
    - Reading  
    - Watching TV   
    - Going to the Movies       
    - Basketball 
    - Football
   - photopath (string with filename of profile photo)
   - enemies list (comma seperated string of rejected matches)

- messages table
  - id (int) (primary key) (used so the messages display in order)
  - sender (string)
  - receiver (string)
  - message (string)

### Methods

- createSchema
  - connects to mysql, creates the db and table if it doesn't exist
- userLogin
  - Takes in strings name and password
  - Outputs Boolean (true if login success, false if failure)
- getDisplayName, getPassword, getAboutMe, getHobbies, getMaxTravelDistance, getLatitude, getLongitude, getPhoto, getEnemies
  - Takes in strings username
  - Outputs a String or double with the corresponding property of that user in the db
- getUserList
  - Outputs an ArrayList of all the username strings in the db
- uploadPhoto
  - Takes username and photopath, updates field
  - returns boolean
- addEnemy
  - Takes username and rejected match username, adds rejected user to the field
  - returns boolean
- createUser
  - Input
    - strings
      - username
      - password
      - displayName
      - aboutMe
      - hobbies
    - doubles
      - maxTravelDistance
      - latitude
      - longitude
  - Output boolean
- getMessages
  - Takes two usernames
  - returns arraylist of [sender,message] string[] between them in order
- addMessage
  - Takes two usernames and a message, adds to table
  - returns boolean
