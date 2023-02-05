# Info

### Initial setup for most projects used this semester

Many of the solutions to individual weeks, can be found in a branch for the given week

## How to get YOUR OWN code updated to a given week
If you wan't to jump in on a certain week in order to be able to complete "next weeks" exercises do the following in git bash
or your own mac-terminal if you are using a mac

````
## replace this you the location on your computer where the project must be created
cd /g/dataKea/spring2023/cars

# Clone the week1 branch from the remote repostiory
git clone -b week1 https://github.com/kea-spring2023/cars-r-us.git

#Verify that a cars-r-us folder was created
ls
# navigate into this folder
cd cars-r-us
# Verify that this folder has a git-repo folder (.git)
ls -a
# Delete this folder
rm -rf .git
# Verify that it's gone
ls -a
# Set the project under version control again, this time without reference to MY repository
git init
# Verify that you have a git-folder (.git) again
ls -a
# Set main branch name to "main"
git branch -m main
# Add everything in the project folder to the staging area
git add .
# Commit, wiht a message that makes sense
git commit -m "ready for week1"
# Now create your own cars repo on github, and do what your normally do to push this project up here
# Finally open the project in your IDE
```