CREATE TABLE users (
  id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  Name NVARCHAR(255),
  Email NVARCHAR(255),
  Password NVARCHAR(255),
  ProfilePic BLOB,
  Biblio NVARCHAR(255)
);

CREATE TABLE subjectspt (
  id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  Name NVARCHAR(255),
  Image BLOB
);

CREATE TABLE subjectseng (
  id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  Name NVARCHAR(255),
  Image BLOB
);

CREATE TABLE questions_pt (
  id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  IDSubject INTEGER, 
  Question NVARCHAR(255),
  Difficulty INTEGER, 
  RightAnswer NVARCHAR(255),
  Wrong1 NVARCHAR(255),
  Wrong2 NVARCHAR(255),
  Wrong3 NVARCHAR(255),
  CONSTRAINT fk_questSubj FOREIGN KEY (IDSubject) REFERENCES subjectspt (id)
);

CREATE TABLE questions_eng (
  id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  IDSubject INTEGER, 
  Question NVARCHAR(255),
  Difficulty INTEGER, 
  RightAnswer NVARCHAR(255),
  Wrong1 NVARCHAR(255),
  Wrong2 NVARCHAR(255),
  Wrong3 NVARCHAR(255),
  CONSTRAINT fk_questSubjEng FOREIGN KEY (IDSubject) REFERENCES subjectseng (id)
);

CREATE TABLE userresults (
  id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  IDSubject INTEGER, 
  IDUser INTEGER, 
  Score INTEGER, 
  NumCorrectAns INTEGER, 
  NumWrongAns INTEGER, 
  TimeToSolve TEXT, 
  Difficulty INTEGER,
  CONSTRAINT fk_resultSubj FOREIGN KEY (IDSubject) REFERENCES subjectspt (id), CONSTRAINT fk_resultUser FOREIGN KEY (IDUser) REFERENCES users (id)
);


CREATE TABLE userfriends (
  id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  IDUser INTEGER, 
  IDFriend INTEGER, 
  CONSTRAINT fk_frindUser FOREIGN KEY (IDUser) REFERENCES users (id), 
  CONSTRAINT fk_userfriend FOREIGN KEY (IDFriend) REFERENCES users (id)
);