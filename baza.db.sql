BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "vozaci" (
	"ime"	TEXT,
	"prezime"	TEXT,
	"jmbg"	TEXT,
	"datum_rodjenja"	TEXT,
	"datum_zaposlenja"	TEXT,
	PRIMARY KEY("jmbg")
);
CREATE TABLE IF NOT EXISTS "autobusi" (
	"proizvodjac"	TEXT,
	"serija"	TEXT,
	"broj_sjedista"	INTEGER,
	"vozac1"	TEXT,
	"vozac2"	TEXT,
	FOREIGN KEY("vozac1") REFERENCES "vozaci"("jmbg"),
	FOREIGN KEY("vozac2") REFERENCES "vozaci"("jmbg"),
	PRIMARY KEY("proizvodjac")
);
DELETE FROM vozaci;
DELETE FROM autobusi;
COMMIT;