# Contributing	

## Coding Style

This project follows the [Android Code Style Guidelines](http://source.android.com/source/code-style.html).

## Commenting

This project uses the [JavaDoc](http://www.oracle.com/technetwork/java/javase/tech/index-137868.html) tool to compile comments. Please respect the syntax.

## Git workflow

### Principles

- Features are developed on `feature/[issue number]-[feature name]` branches. Eg. `feature/23-peer-discovery`.
- Small changes that can go whenever are pushed to the `develop` branch.
- When features are finished, a pull request is opened to `develop` for any ad hoc review before merging.
- A `feature/*` will only be merged if all the tests pass, as reported inline in the pull request by the Jenkins plugin.
- `develop` branch is merged to `master` via pull request in preparation for release.

Please take a look at the [Atlassian Git Tutorial](https://www.atlassian.com/git/tutorials/) if you are not already comfortable with Git. Especially the first two sections: Getting Started and Collaborating.

### Example

1. Clone the repository if you haven't already.

        $ git clone https://github.com/sweng-epfl-2014/sweng-team-meshenger.git
    
2. Checkout the `develop` branch

        $ git checkout develop
    
3. Create and switch to a new branch.
 
        $ git checkout -b feature/23-peer-discovery

4. Implement the feature, and commit it.  
   Look into [`git commit --amend`](https://www.atlassian.com/git/tutorials/rewriting-history/git-commit--amend) and [`git rebase -i'](https://www.atlassian.com/git/tutorials/rewriting-history/git-rebase-i) to clean up your history, if needed.

5. [Rebase](https://www.atlassian.com/git/tutorials/rewriting-history/git-rebase) your branch on the `develop` branch, and fix the conflicts, if any.  
    This will ensure that your pull request can be easily merged into `develop`.

        $ git rebase develop
        
6. Push your branch to GitHub.

        $ git push origin feature/23-peer-discover

7. Go to [GitHub](https://github.com/sweng-epfl-2014/sweng-team-meshenger/), select your branch in the dropdown, and create a pull request.

## Licence

By committing code to this repository, your code will be licensed under GPLv3
