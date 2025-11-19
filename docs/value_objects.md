# Value Objects im Domain Driven Design

## 1. Position

`Position` repräsentiert eine Koordinate auf dem Schachbrett (z.b. "e4" oder "a1").

Eine Position "e4" ist immer dieselbe, unabhängig davon, wann oder wie sie erstellt wurde
Nach der Erstellung können `file` und `rank` nicht mehr geändert werden
Zwei `Position`-Objekte mit `file=4, rank=3` sind vollständig austauschbar
Der Konstruktor validiert, dass `file` und `rank` im gültigen Bereich (0-7) liegen

`Position` bietet Methoden für domänenspezifische Operationen:
- `deltaFile()`, `deltaRank()`: Berechnung von Bewegungsvektoren
- `manhattanDistance()`: Berechnung von Distanzen für Validierungen
- `isAdjacent()`: Prüfung von Nachbarschaftsbeziehungen

## 2. Move

`Move` repräsentiert eine Zugabsicht (von einer Position zu einer anderen).

Ein Zug "e2→e4" ist immer derselbe Zug, unabhängig davon, wann er erstellt wurde
Alle Felder (`from`, `to`, `promotion`) sind `final`
Zwei `Move`-Objekte mit denselben `from`/`to`/`promotion` sind gleichwertig
Repräsentiert eine Absicht, nicht eine ausgeführt Aktion

`Move` kapselt die Zuginfos und bietet eine string Repräsentation für z.b. das Logging

## 3. MoveRecord

`MoveRecord` ist eine unveränderlicher vergagnener, ausgeführter Zug.

Ein Record für "Zug 5: Bauer e2→e4" ist immer derselbe Record mit denselben Daten
Als Java `record` sind alle Felder automatisch `final`
Zwei Records mit denselben Werten sind gleichwertig
Repräsentiert einen vergangenen Zustand, der sich nie ändert

`MoveRecord` bietet eine `notation()` Methode für die Schachnotation (P-e4).

## 4. PieceColor

`PieceColor` ist ein Enum, der die Farbe einer Schachfigur repräsentiert.
Enums sind per Definition unveränderlich
Nur zwei gültige Werte existieren

**Domänenlogik:** `PieceColor` bietet Methoden wie `opposite()` für Turn-Management.
