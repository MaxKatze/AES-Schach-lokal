# ConsoleChess Architektur- und Prinzipiendokumentation

## Überblick
ConsoleChess ist eine konsolenbasierte Java-Anwendung für ein vollständiges 1-vs-1-Schachspiel. Die Implementierung umfasst über 2.000 Zeilen produktiven Code in mehr als 30 Klassen und ist vollständig mit Gradle baubar. Der Fokus liegt auf einer klar getrennten Schichtenarchitektur, einer domänengetriebenen Modellierung sowie der bewussten Anwendung von SOLID, GRASP, DRY und Entwurfsmustern.

## Schichtenarchitektur
- **CLI-Schicht (`org.chess.console.cli`)**: Präsentationslogik (I/O, Rendering, Command-Parsen, Hilfetexte). Keine Geschäftslogik.
- **Application-Schicht (`org.chess.console.application`)**: Orchestriert Use-Cases (`GameService`, `MoveCoordinator`, `HintService`, `TurnManager`). Koordiniert Domain-Objekte und Repositories.
- **Domain-Schicht (`org.chess.console.domain`)**: Kernmodell mit Entities (`Game`, `Player`, `Piece`), Value Objects (`Position`, `Move`), Aggregaten (`Game` samt `Board`, `MoveHistory`), Regeln und Fachlogik (z.B. `RuleBook`, `CheckInspector`, `LegalMoveExplorer`).
- **Infrastructure-Schicht (`org.chess.console.infrastructure`)**: Persistenzadapter (`GameRepository`, `InMemoryGameRepository`).

Die CLI hängt ausschließlich von Application-Interfaces; Application kennt Domain und Repositories; Domain ist unabhängig von äußeren Schichten.

## Domain Driven Design
- **Ubiquitous Language**: Begriffe wie *Game, Board, Move, Piece, RuleBook, Hint* sind konsequent in Code und Dokumentation identisch.
- **Entities**: `Game`, `Player`, `Piece` (mit Unterklassen) besitzen Identität während des gesamten Lebenszyklus.
- **Value Objects**: `Position`, `Move`, `MoveRecord` sind unveränderlich und gleichheitsbasiert.
- **Aggregates**: `Game` ist Aggregate Root und kapselt `Board`, `MoveHistory`, Status & Zugrecht; externe Zugriffe erfolgen über Application-Services.
- **Repositories**: `GameRepository` abstrahiert Persistenz. `InMemoryGameRepository` implementiert die Infrastruktur für einen In-Memory-Speicher, lässt sich aber gegen andere Persistenzen tauschen.
- **Domain Services**: `RuleBook`, `CheckInspector`, `LegalMoveExplorer` enthalten fachliche Regeln, die nicht eindeutig einer Entität zuordenbar sind.
- **Context & Ubiquitous Language**: Konsistente Algebraische Notation (`e2 e4`), Begriffe wie *Check*, *Hint*, *Resign* wurden in Parser, UI und Domain wiederverwendet.

## Analyse der Prinzipien
### SOLID
- **Single Responsibility**: `ConsoleRunner` moderiert nur UI-Fluss; `MoveCoordinator` validiert/exekutiert Züge; `RuleBook` kapselt Bewegungsregeln.
- **Open/Closed**: Neue Figuren oder Befehle lassen sich durch zusätzliche `MoveRule`- oder `CliCommand`-Implementierungen ergänzen, ohne bestehende Klassen anzupassen.
- **Liskov Substitution**: Alle `Piece`-Unterklassen erfüllen das `Piece`-Vertrag (z.B. `copy()`, `moved()`), wodurch sie austauschbar sind.
- **Interface Segregation**: Kleine Interfaces wie `MoveRule`, `BoardRenderer`, `CliCommand` vermeiden Zwangsabhängigkeiten.
- **Dependency Inversion**: `GameService` hängt von `GameRepository`-Interface statt konkreter Implementierung; `App` injiziert Implementierungen.

### GRASP
- **Controller**: `ConsoleRunner` als zentraler UI-Controller; `GameService` als Anwendungscontroller.
- **Creator**: `GameFactory` erzeugt Games inklusive Board-Setup; `PieceFactory` erstellt Figuren für Promotionen.
- **Low Coupling / High Cohesion**: Klassen fokussieren auf eng umrissene Aufgaben (z.B. `LegalMoveExplorer` nur für Zugermittlung) und kommunizieren über klar definierte Interfaces.
- **Information Expert**: `Board` verwaltet Squares, `MoveHistory` verwaltet Züge; `RuleBook` kennt Bewegungslogik.

### DRY
- Wiederholte Bewegungslogik steckt in `AbstractLineRule`, `BoardNavigator`, `LegalMoveExplorer`. Promotion-Handling erfolgt zentral in `MoveCoordinator`/`LegalMoveExplorer`.
- Konsistente Kommandoverarbeitung über `CommandParser` mit `CliCommand`-Typen.

## Entwurfsmuster
- **Strategy**: `MoveRule` + Implementierungen (`KingMoveRule`, `PawnMoveRule` …) werden vom `RuleBook` ausgewählt.
- **Factory**: `GameFactory`, `PieceFactory` kapseln komplexe Erzeugungslogik.
- **Repository**: `GameRepository` entkoppelt Domain von Speichertechnik.
- **Template Method / Hook**: `AbstractLineRule` implementiert Schablone für gleitende Figuren.

## Weitere fachliche Aspekte
- **Hint-Funktion**: `HintService` verwendet `LegalMoveExplorer`, um legale Züge zu ermitteln und einen zufälligen Vorschlag zu liefern.
- **Validierung**: Jede Eingabe wird über `CommandParser` geprüft; `MoveCoordinator` stellt sicher, dass kein Zug den eigenen König entblößt.
- **Promotion & Schachlogik**: Bauern werden automatisch (oder auf Wunsch) umgewandelt. `CheckInspector` erkennt Schach, `LegalMoveExplorer` ihre Entschärfung.

## Build & Ausführung
```bash
./gradlew run        # Startet die Konsole
./gradlew test       # (aktuell keine Tests, Hook für künftige Erweiterungen)
```

Beim Start fragt die Anwendung nach Spielernamen, zeigt das Brett in ASCII und akzeptiert Befehle (`e2 e4`, `hint`, `history`, `resign`, `restart`, `exit`). Die Anwendung benötigt nur eine aktuelle JDK-Installation; zusätzliche Abhängigkeiten werden über Gradle verwaltet.

## Ausblick
- Persistente Speicherung (Datei/DB) kann über weitere `GameRepository`-Adapter ergänzt werden.
- Netzwerkschach oder KI-Spieler lassen sich als zusätzliche UI-/Application-Schichten hinzufügen, ohne die Domain zu verändern.

