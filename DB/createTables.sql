CREATE TABLE users (
  id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  Name TEXT, Email TEXT, Password TEXT, 
  ProfilePic BLOB, Biblio TEXT
);

CREATE TABLE subjects (
  id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  Name TEXT, 
  Image BLOB
);

CREATE TABLE Questions_PT (
  id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  IDSubject INTEGER, 
  Question TEXT, 
  Difficulty INTEGER, 
  RightAnswer TEXT, 
  Wrong1 TEXT,
  Wrong2 TEXT, 
  Wrong3 TEXT, 
  CONSTRAINT fk_questSubj FOREIGN KEY (IDSubject) REFERENCES subjects (id)
);

CREATE TABLE Questions_ENG (
  id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  IDSubject INTEGER, 
  Question TEXT, 
  Difficulty INTEGER, 
  RightAnswer TEXT, 
  Wrong1 TEXT, 
  Wrong2 TEXT, 
  Wrong3 TEXT, 
  CONSTRAINT fk_questSubjEng FOREIGN KEY (IDSubject) REFERENCES subjects (id)
);

CREATE TABLE UserResults (
  id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  IDSubject INTEGER, 
  IDUser INTEGER, 
  Score INTEGER, 
  NumCorrectAns INTEGER, 
  NumWrongAns INTEGER, 
  TimeToSolve TIME NULL, 
  Difficulty INTEGER,
  CONSTRAINT fk_resultSubj FOREIGN KEY (IDSubject) REFERENCES subjects (id), CONSTRAINT fk_resultUser FOREIGN KEY (IDUser) REFERENCES users (id)
);


CREATE TABLE UserFriends (
  id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  IDUser INTEGER, 
  IDFriend INTEGER, 
  CONSTRAINT fk_frindUser FOREIGN KEY (IDUser) REFERENCES users (id), 
  CONSTRAINT fk_userfriend FOREIGN KEY (IDFriend) REFERENCES users (id)
);