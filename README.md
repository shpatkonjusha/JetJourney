GIT commands !!!!

1. Cloning the Repository
To clone the repository, run the following command:
git clone https://github.com/shpatkonjusha/JetJourney.git

3. Creating a New Branch
Before you start working on a new feature or bugfix, create a new branch:
git checkout -b <branch-name>

3. Making Changes
Make your changes to the code, and once you're done, stage the changes:
git add .

Then commit your changes with a meaningful message:
git commit -m "Your commit message"

4. Syncing with Remote
Before pushing your changes, it's a good practice to make sure your branch is up-to-date with the latest changes from the remote repository. You can do this by pulling the latest updates:
git pull origin master

5. Pushing Changes
Once your changes are ready, push your branch to the remote repository:
git push origin <branch-name>
