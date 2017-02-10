BEGIN;

DROP TABLE TopTrumpsData;

CREATE TABLE TopTrumpsData
	(game_number INT CONSTRAINT gameNumber_pk PRIMARY KEY, winning_Player INT, rounds_Played INT,
	draws INT, player_Zero_Rounds INT, player_One_Rounds INT, player_Two_Rounds INT, player_Three_Rounds INT,
	player_Four_Rounds INT);
	
COMMIT;

BEGIN;

INSERT INTO TopTrumpsData VALUES
(1, 3, 42, 3, 13, 3, 4, 21, 0),
(2, 2, 67, 4, 15, 22, 38, 3, 14),
(3, 1, 35, 1, 3, 13, 4, 21, 0),
(4, 0, 89, 9, 33, 26, 10, 8, 12);

COMMIT;
