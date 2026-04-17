## ADDED Requirements

### Requirement: Project-level .gitignore
The project SHALL have a .gitignore file that prevents build artifacts and IDE configuration from being committed.

#### Scenario: Backend build artifacts excluded
- **WHEN** developer runs `git status` after Maven build
- **THEN** `backend/target/` directory is not shown as untracked

#### Scenario: Frontend build artifacts excluded
- **WHEN** developer runs `git status` after npm build
- **THEN** `frontend/dist/` and `frontend/node_modules/` are not shown as untracked

#### Scenario: IDE configuration excluded
- **WHEN** developer runs `git status`
- **THEN** `.idea/`, `.vscode/`, `.DS_Store` are not shown as untracked
