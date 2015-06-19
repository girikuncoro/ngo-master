## NGO Play application:
=================================
This is an SBT Scala/Java based Play application. App will rely on configuration Twilio credentials.
This app uses JDBI and H2 in memory database. It will by default every 6 hours spit out current data into 3 csv files in
user home directory/NGORepo along logs.


# How to convert the project into intellij 
    We are using sbt-idea plugin so you may just run

        gen-idea

    to make project intellij ready
    
# How to start server
    sbt run 


You may also deploy the app on heroku. Just follow below steps that are taken from Play site:

How to run it on Heroku:

Store your application in git
Just create a git repository for your application:

$ git init
$ git add .
$ git commit -m "init"

Create a new application on Heroku
Note that you need an Heroku account, and to install the heroku gem.

$ heroku create --stack cedar
Creating warm-frost-1289... done, stack is cedar
http://warm-1289.herokuapp.com/ | git@heroku.com:warm-1289.git
Git remote heroku added
Deploy your application
To deploy your application on Heroku, just use git to push it into the heroku remote repository:

$ git push heroku master
Counting objects: 34, done.
Delta compression using up to 8 threads.
Compressing objects: 100% (20/20), done.
Writing objects: 100% (34/34), 35.45 KiB, done.
Total 34 (delta 0), reused 0 (delta 0)

-----> Heroku receiving push
-----> Scala app detected
-----> Building app with sbt v0.11.0
-----> Running: sbt clean compile stage
       ...
-----> Discovering process types
       Procfile declares types -> web
-----> Compiled slug size is 46.3MB
-----> Launching... done, v5
       http://8044.herokuapp.com deployed to Heroku

To git@heroku.com:floating-lightning-8044.git
* [new branch]      master -> master
Heroku will run sbt clean compile stage to prepare your application. On the first deployment, all dependencies will be downloaded, which takes a while to complete (but will be cached for future deployments).

Check that your application has been deployed
Now, let’s check the state of the application’s processes:

$ heroku ps
Process       State               Command
------------  ------------------  ----------------------
web.1         up for 10s          target/start
The web process is up. Review the logs for more information:

$ heroku logs
2011-08-18T00:13:41+00:00 heroku[web.1]: Starting process with command `target/start`
2011-08-18T00:14:18+00:00 app[web.1]: Starting on port:28328
2011-08-18T00:14:18+00:00 app[web.1]: Started.
2011-08-18T00:14:19+00:00 heroku[web.1]: State changed from starting to up
...
Looks good. We can now visit the app with heroku open.

Running play commands remotely
Cedar allows you to launch a REPL process attached to your local terminal for experimenting in your application’s environment:

$ heroku run sbt play
Running sbt play attached to terminal... up, run.2
[info] Loading global plugins from /app/.sbt_home/.sbt/plugins
[info] Updating {file:/app/.sbt_home/.sbt/plugins/}default-0f55ac...
[info] Set current project to My first application (in build file:/app/)
       _            _
 _ __ | | __ _ _  _| |
| '_ \| |/ _' | || |_|
|  __/|_|\____|\__ (_)
|_|            |__/

play! 2.0-beta, http://www.playframework.org

> Type "help" or "license" for more information.
> Type "exit" or use Ctrl+D to leave this console.

[My first application] $


SMS messages:
l       --> Will list the districts
rd      --> Creates a new district
rp:(districtName) -->Set default district for the phone
p:(districtName):(Item):(WeightInKg) -> Will plant some item P:myDistrict:Onion:20
r       --> Will list the items on your default District


