INSERT INTO Conference(ID) VALUES(1);
INSERT INTO Meeting(ID, version, name, CONFERENCE_ID) VALUES(1,1,'meeting',1);
INSERT INTO User(ID, version, firstname,lastname,MEETING_ID) VALUES(1,1,'ruddy','palvair',1);