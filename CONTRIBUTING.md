# Introduction
Thank you for helping Feedify grow!

# Conventions ([article](https://dev.to/varbsan/a-simplified-convention-for-naming-branches-and-commits-in-git-il4))

## Branch Naming Conventions 
1. For a milestone branch, write it as `milestone{number}`
    - For example `milestone1` for milestone 1.
2. For each milestone feature, we checkout a branch from milestone{number} branch
    with name milestone{number}/feat-[JIRA_ID]-[DESCRIPTION]
    - JIRA_ID is the ID of the Jira story for this feature
    - DESCRIPTION is a brief 3-5 words description of the feature

## Commit Message Conventions
A commit message should start with a category of change. You can pretty much use the following 4 categories for everything: feat, fix, refactor, and chore.

- `feat` is for adding a new feature
- `fix` is for fixing a bug
- `refactor` is for changing code for peformance or convenience purpose (e.g. readibility)
- `chore` is for everything else (writing documentation, formatting, adding tests, cleaning useless code etc.)

    ```
    git commit -m "feat: finished login page"
    ```
    ```
    git commit -m "fix: fixed login page bug"
    ```

