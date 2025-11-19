# Entities im Domain Driven Design

## 1. Game (Aggregate Root)

Die `Game`-Entity wird durch eine eindeutige `UUID` identifiziert, die über den gesamten Lebenszyklus der Partie konstant bleibt.

Eine Schachpartie behält ihre Identität auch wenn sich ihre Attribute ändern:
- Der Spielstatus wechselt von `READY` zu `IN_PROGRESS` zu `CHECK` zu`CHECKMATE` oder zu `RESIGNED`
- Die `activeColor` wechselt zwischen `WHITE` und `BLACK` bei jedem Zug
- Das `Board` wird durch Züge modifiziert
- Die `MoveHistory` wächst mit jedem Zug

Eine `Game` wird durch die `GameFactory` erstellt, durch den `GameService` verwaltet und im `GameRepository` persistiert. Es existiert von der Erstellung bis zum Spielende oder bis sie gelöscht wird.

- Verwaltung des Spielzustands
- Kapselung des `Board` und der `MoveHistory`
- Bereitstellung von Zugriffsmethoden für Application Services
- Koordination zwischen `Player`-Entities (weis/schwarz)

## 2. Player

Jeder `Player` besitzt eine eindeutige `UUID`, die ihn über verschiedene Spiele hinweg identifiziert.

Ein Spieler bleibt derselbe, auch wenn:
- Sein Name sich ändert
- mehrere Farben gespielt werden

Ein `Player` wird erstellt, wenn ein neues Spiel gestartet wird, oder geladen werden mittels `PlayerRepositroy`.

- Repräsentation eines menschlichen Spielers
- Zuordnung zu einer Farbe (`PieceColor`) innerhalb eines Spiels

## 3. Piece

Ein `Piece`-Entity besitzt Identität durch ihre Position auf dem `Board` und ihren Zustand (`hasMoved`).

Eine Figur behält ihre Identität, auch wenn sie sich bewegt.

Pieces werden beim Spielstart durch die `GameFactory` erstellt und auf dem `Board` platziert. Sie existieren während der gesamten Partie, bis sie geschlagen werden oder das Spiel endet. Bei Promotion wird eine neue Piece-Entity erstellt, die die alte ersetzt.

- Repräsentation einer Schachfigur mit Farbe und Typ
- Verwaltung des Bewegungszustands (`hasMoved`) für Regeln wie Rochade
- Bereitstellung von Kopier- und Transformationsmethoden (`copy()`, `moved()`)

## 4. Board (Aggregate Component Entity)


Das `Board` besitzt Identität als Teil des `Game`-Aggregats. Es wird identifiziert, weil es zu einem bestimmten `Game` gehört.

Ein Board behält seine Identität, auch wenn:

- Pieces sich bewegen oder geschlagen werden
- Die Anzahl der Pieces sich reduziert

Ein `Board` wird zusammen mit einem `Game` erstellt und existiert während der gesamten Lebensdauer der Partie. 

- Verwaltung der 64 `Square`-Objekte
- Bereitstellung von Methoden zum Platzieren und Bewegen von Pieces

## Zusammenfassung

- **Game**: Identifiziert durch UUID, Status und Inhalt ändern sich
- **Player**: Identifiziert durch UUID, kann in verschiedenen Kontexten auftreten
- **Piece**: Identifiziert durch Position und Zustand, bewegt sich und transformiert sich
- **Board**: Identifiziert durch Zugehörigkeit zu Game, Inhalt ändert sich kontinuierlich
