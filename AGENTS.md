AGENTS.md — Quick-start for AI coding agents working on this repo

Purpose
- Provide the minimum, high-value information an automated coding agent needs to be productive in the NWMuseumProject Java codebase.
- Focus on concrete, discoverable patterns, integration points and commands specific to this project.

1) Big-picture architecture (where to look)
- UI layer
  - JavaFX app entry: `org.GUI.App` (src/main/java/org/GUI/App.java)
  - FXML views: `src/main/resources/GUI/*.fxml` and controllers: `org.GUI.*Controller`
  - Note: the code mixes JavaFX (UI) and a console UI (`org.UI`) — the app starts the JavaFX `App` from `org.Main`.
- Domain model
  - Artefact types: `src/main/java/org/museum/artefacts/*` (Artefact.java, Painting, Misc, artefacts3d/*)
  - Material/Style enums: `Material.java`, `Style.java`, etc.
- Data layer
  - Database access and mapping: `org.museum.data.DataBase` (single static JDBC Connection) and `Inventory` (in-memory singleton cache).
  - SQL schemas and test data: `src/main/resources/{schema.sql, CreationSchema.SQL, DataSchema.sql, museumprojecttesting.sql}`
- Helpers/Other
  - Loan / Room value objects: `org.museum.other.*`

2) Key design patterns and project-specific conventions
- testMode boolean flag: Many public methods accept a boolean `testMode` to switch between DB properties (production vs test). Example: `DataBase.getConnection(boolean testmode)` reads `db-testing.properties` when true.
- Environment switch for CI: `DataBase.getConnection()` checks `GITHUB_ACTIONS` env var and uses `db-ci.properties` when present.
- Single static JDBC Connection: `DataBase.connection` is a static field reused across calls — not thread-safe and can persist between tests. Treat carefully when writing concurrent changes.
- Inventory singleton cache: `Inventory.getInstance()` holds in-memory artefacts/loans/rooms. Call `Inventory.UpdateArtefactsFromDB(testMode)` to refresh.
- Type strings == runtime class names: artefact `type` DB column stores the Java class simple name (e.g. "Painting", "Pottery"). Lookup in `DataBase.PullArtefacts` switches on these exact names.
- Room validation by string containment: Artefact constructors validate rooms by calling `DataBase.getAllRooms(Testmode)` and using `.toString().toLowerCase().contains(currentRoom.toLowerCase())` — fuzzy and fragile. Use `DataBase.getRoomFromName` for reliable lookup.
- Images stored as BLOBs: `DataBase.addImageToArtefact` writes images as bytes to `images` table; the stored row `name` column is set to `name + " " + filePath` (so image naming contains the artefact name and original file path).
- Mixing UI toolkits: the project uses JavaFX for main UI, but the image viewer is Swing/AWT (`Inventory.ViewImagesOfArtefact`) — running in same process is allowed but be careful with threading (SwingUtilities vs Platform.runLater).

3) Integration points & external dependencies
- Database: MySQL (JDBC). Properties files live in `src/main/resources`:
  - `db.properties` (default / production)
  - `db-testing.properties` (used when testMode=true)
  - `db-ci.properties` (used in GitHub Actions via GITHUB_ACTIONS env var)
- JavaFX: dependencies declared in `pom.xml` (`org.openjfx:javafx-controls/javafx-fxml`). When running outside the IDE you may need to provide JavaFX on the module path.
- JUnit 5 for tests (`junit-jupiter` in pom.xml)

4) Build, run and debug (concrete commands)
- Build: `mvn -f C:\Users\nathan\IdeaProjects\NWMuseumProject\pom.xml clean package`
- Run from IDE: prefer running `org.GUI.App` (JavaFX) or `org.Main` (console entry) via your IDE run configuration. App startup early-checks DB connection.
- Run GUI from command line (minimal):
  - Ensure MySQL is running and DB configured (see section 5).
  - From project root (Windows PowerShell):
    - mvn package; then run with classpath including dependencies (example):
      - $cp = (mvn dependency:build-classpath -Dmdep.outputFilterFile=false -q -DincludeScope=runtime exec:exec -Dexec.executable=echo -Dexec.args="%classpath%") ;
      - java -cp "target/classes;target/dependency/*" org.GUI.App
    - Note: many environments prefer launching via the IDE because JavaFX modulepath handling varies.
- Tests: `mvn test` (uses surefire, project sets Java 24 in pom.xml)

5) Database / local environment setup (discovered files and how they're used)
- Schema and seed SQL in resources: `src/main/resources/schema.sql` and `museumprojecttesting.sql`. Use these to create `museumproject` and `museumprojecttesting` schemas.
- Example (using mysql client on developer machine):
  - mysql -u root < src/main/resources/schema.sql
  - mysql -u root museumproject < src/main/resources/museumprojecttesting.sql  (or adjust as needed)
- Properties files specify connection URLs and credentials. Default `db.properties` uses `jdbc:mysql://localhost:3306/museumproject` with empty password. `db-ci.properties` uses password `root`.
- App aborts early if DB connection fails: `org.GUI.App.start` calls `DataBase.getConnection(false)` and exits via Platform.exit() on failure.

6) Troubleshooting and important notes for agents
- When changing DB code: remember `DataBase.connection` is static — unit tests and long-running JVMs may need to reset connection state or run in testMode to avoid using production DB.
- When adding artefact types: ensure the exact class simple name is used in DB `type` column and in `DataBase.PullArtefacts` switch.
- Image handling: store/read via `BufferedImage` and BLOBs — tests that access images will need to mock or provide small image resources.
- Concurrency: multiple calls that assume `connection` is non-null could race in parallel test execution.
- GUI vs console: `org.UI` contains many console flows duplicated from GUI functionality; prefer editing controllers under `org.GUI` for GUI changes.

7) Files to inspect first when working on a feature
- `org.GUI.App` — startup, DB connection check
- `org.museum.data.DataBase` — DB access patterns and properties handling
- `org.museum.data.Inventory` — in-memory caching, image viewer, move logic
- `src/main/resources/GUI/*.fxml` and `org.GUI.*Controller` — where UI wiring and event handlers live
- `src/main/resources/*.sql` and `src/main/resources/db-*.properties` — DB setup and environment switching

If you need further automation (CI edits, add a maven plugin to run JavaFX from CLI, or generate unit test scaffolding against the test DB), I can create concrete patches and run test builds.

References (examples from repo)
- Startup & DB check: `org.GUI.App` (src/main/java/org/GUI/App.java)
- Static connection & property selection: `org.museum.data.DataBase.getConnection` (src/main/java/org/museum/data/DataBase.java)
- Inventory image viewer (Swing): `org.museum.data.Inventory.ViewImagesOfArtefact` (src/main/java/org/museum/data/Inventory.java)
- SQL and DB properties: `src/main/resources/{schema.sql,museumprojecttesting.sql,db.properties,db-testing.properties,db-ci.properties}`

---
Generated by an automated agent to help other agents jump-start work in this codebase. Keep this file small and to the point; update as architecture changes.

