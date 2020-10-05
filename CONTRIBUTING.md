# CONTRIBUTING

Contributions are welcome, and are accepted via pull requests.
Please review these guidelines before submitting any pull requests.

## Process

1. Fork the project.
1. Create a new branch.
1. Code, test, commit and push.
1. Open a pull request detailing your changes.
1. Describe what your pull request is (change, bugfix, new country implementation, etc.).

## Guidelines

* The build includes checkstyle rules for many of our code conventions. Run `./gradlew checkstyleMain checkstyleTest` if you want to check your changes are 
compliant.
* Add some Javadoc.
* If you have multiple commits, squashing some of them might help us have a better sense of what you did.
* You may need to [rebase](https://git-scm.com/book/en/v2/Git-Branching-Rebasing) to avoid merge conflicts.

## Build from Source

socrates-java source can be built from the command line using [Gradle](https://gradle.org) on JDK 1.8 or above. We include 
[Gradle's wrapper scripts](https://docs.gradle.org/current/userguide/gradle_wrapper.html) (`./gradlew` or `gradlew.bat`) that you can run rather than needing to
install Gradle locally.

The project can be built from the root directory using the standard Gradle command:
```bash
./gradlew build
```
