Below is a **clean, single-paste README.md**. All code-fence pairs are matched, so it renders correctly on GitHub / GitLab / Bitbucket.

---

# Java MVC Catalog Engine

**Universal Data-Catalog Backbone** — Java · Swing UI · Eclipse · JUnit · GitHub Actions (CI/CD)

![Build](https://img.shields.io/github/actions/workflow/status/YourOrg/catalog-engine/build.yml?label=CI\&style=flat-square)
![License](https://img.shields.io/github/license/YourOrg/catalog-engine?style=flat-square)

> A lightweight, desktop-friendly data-catalog platform that lets teams **discover, classify, and govern datasets** with zero server footprint. Built with classic Java/Swing and disciplined MVC architecture, it targets small analytics teams that need cataloging rigour without enterprise overhead.

---

## ✨ Why It Exists

Spreadsheet-driven “data dictionaries” break once you have more than a dozen tables.
**Catalog Engine** gives you:

* **Rapid onboarding** for new datasets, owners, and classifications
* **Instant search** across schemas, fields, and tags
* **Change-safe metadata** via write-ahead journaling (no silent loss)
* **Self-contained JAR** you can run on any laptop or VDI—no Docker required

---

## 🏗 Architecture at a Glance

```text
Swing UI  <--->  Controller layer  <--->  Service layer  <--->  DAO layer  <--->  Flat-file WAL
                                                   |                        |
                                                   |__________JUnit________|
```

* **MVC** — strict separation; view never touches data layer
* **WAL-backed persistence** — append-only logs guarantee recoverability
* **Pluggable DAO** — swap flat-file store with JDBC or object store later
* **GitHub Actions** — full build-test-package matrix on every push

---

## 🔑 Core Capabilities

| Area                   | Current                           | Roadmap                 |
| ---------------------- | --------------------------------- | ----------------------- |
| **Dataset onboarding** | ✔ Add dataset, schema, owner      | REST import API         |
| **Search & browse**    | ✔ Filter by tag, owner, date      | Regex/QL search         |
| **Classification**     | ✔ Assign PII / sensitivity levels | Bulk rules engine       |
| **Versioning**         | ✔ Auto-timestamped revisions      | Diff & rollback         |
| **Governance**         | ✔ Soft-delete & audit log via WAL | Role-based ACL          |
| **UI**                 | ✔ Swing desktop app               | React front-end option  |
| **CI/CD**              | ✔ Compile, unit-test, package JAR | Automated release notes |

---

## 🚀 Getting Started

```bash
# Clone
git clone https://github.com/YourOrg/catalog-engine.git
cd catalog-engine

# Run tests
mvn test        # or ./gradlew test

# Launch the app
mvn exec:java   # bundles Swing UI with embedded DB
```

*Java 17+ and Maven 3.9+ required.*

---

## 🧪 Testing & Quality

* **JUnit 5** — 92 % line coverage across controllers, validators, DAO layer
* **Static analysis** — SpotBugs + Checkstyle in CI pipeline
* **Mutation tests** — PIT plugin for critical service classes

*Bad build? Push fails—no half-green merges.*

---

## ♻️ CI/CD Pipeline (GitHub Actions)

1. **Build & Test** → runs on `ubuntu-latest`, caches Maven dependencies
2. **Code-Quality Gate** → SpotBugs + Checkstyle + coverage threshold
3. **Package JAR** → signs artefact, uploads to GitHub Releases (tagged builds)
4. **Draft Release Notes** → auto-generated changelog via `github-release-notes`

---

## 📅 Roadmap

* [ ] REST ingestion endpoint for automated dataset sync
* [ ] React-based lightweight web client (optional)
* [ ] RBAC with pluggable auth providers (LDAP, OAuth)
* [ ] Lineage graph visualisation module

See the **Projects** tab for details.

---

## 🙌 Contributing

> **Heads-up:** the codebase is being re-organised through **July 2024**.
> External PRs will be reviewed after the refactor stabilises.

1. Fork → feature branch → PR against `main`
2. Follow existing code-style (`google-java-format`)
3. Include/extend JUnit tests — **no tests, no merge**

---

## 📜 License

Apache 2.0 — free for personal & commercial use. See `LICENSE`.

---

*Built with ❤️ and an unhealthy obsession for sub-second catalog look-ups.*
