
## Development setup

You need JDK 25 and Maven 3.9+. The Maven Wrapper is included.

```bash
./mvnw clean verify
```

On Windows:

```powershell
.\mvnw.cmd clean verify
```

Run both deterministic console demos before opening a pull request:

```powershell
.\mvnw.cmd -pl loop-engine -am spring-boot:run
.\mvnw.cmd -pl graph-engine -am spring-boot:run
```

## Pull requests

- Keep `game-core` free of AI and provider dependencies.
- Preserve identical gameplay inputs across the two demos.
- Add focused tests for domain rules and decision policy changes.
- Keep agents single-purpose and prompts under `src/main/resources/prompts`.

