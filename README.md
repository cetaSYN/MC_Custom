# MC_Custom

**Uploading here just as a show of large projects I have participated in.**

**This is an old collection of Bukkit plugins from several years ago for a Minecraft server. This is very old and will not be supported. Last compatible MC version: 1.8 (2 Sept 14).**

**Additionally, most of the files were run through sed to remove their previous names. It was not recompiled afterwards and very likely is broken.**

## Old README.md below:

MC_Custom

This is the main repository for all the custom plugins found at -removed-
The source code and plugins may not be used for other servers/projects/plugins/etc without express consent.
**Again, you may NOT use these plugins on your own server without permission. Ask us first.**

  
You may submit patches and pull-requests to this repository if you wish to assist in improving MC_Custom.
You are authorized to set up a private server for the sole purpose of testing code prior to submission.
Upon-submission, you grant MC_Custom full license to use, modify, re-distribute and sub-license the code provided.
Please provide your own attribution in the submission source; our devs will make a best-effort attempt to keep all inline attribution intact, but make no guarantees of such.
  
  
**NOTE:** Not all plugins are currently used or in a working state.
Furthermore, they are all built specifically for our usage.
Attempting to use the plugins on another server will likely result in conflicts, to include potential data loss.
We will not be held liable or provide support for anyone using the plugins, and use of the plugins in a public or commercial manner is a violation of our copyright.
  
  
Master is the currently-deployed branch at all times. If you have permission to commit to master, ensure that the server is running this version.
Master is currently built toward Spigot 1.8.
  
  
##Basic Code Style##
* variable_names
* functionNames
* ClassNames
* CONSTANT_NAMES
* Use tabs instead of spaces
* Braces start on the same line as the function: `if (true) {`
  
  
##To Install:##


####Required Dependencies####
* [Prism](http://discover-prism.com/)
* [LibDisguise](http://ci.md-5.net/job/LibsDisguises/)
    * Requires [Protocol Lib](http://assets.comphenix.net/job/ProtocolLib/)
* Optional Dependencies: Vanish No Packet, World Edit, World Guard (just google them)

####MySQL####
Windows instructions:
1. [Install MySQL](http://dev.mysql.com/downloads/mysql/). Make sure its the Community Server version.
2. Import the "schema_blank.sql". On Windows, Navigate to the install directory bin. Should be something like `C:\Program Files\MySQL\MySQL Server 5.6\bin`
Copy the "schema_blank.sql" to that directory temporarily. From there, you can run `mysql -u [user] -p -e "CREATE DATABASE minecraft"` 
followed by `mysql -u [user] -p minecraft < schema_blank.sql` insert password when prompted. Your default user should be "root"
3. Now that is imported, you are going to want to set up some default permission nodes. Open up the mysql command prompt, (you can use `mysql -u [user] -p` again) and run the following command:
    * ``INSERT INTO `group_perms` (`group_id`,`perm_node` VALUES (1,"[perm node]");`` Where [perm node] can be replaced with the permission node, ie. "class.*", "core.*", "punishments.*" etc. Repeat for each permission you need.

Contact for Linux assistance.

####IntelliJ IDEA####
* Create a new java project, and point it towards this repositories directory.
* You now need to add the modules (individual plugins) to this project.
    1. File > Import Module
    2. Navigate to where the plugins are and choose the one you want to add (ie mc_custom_server/MC_Custom_Core/)
    3. Select "Import modules from existing model" Choose maven
    4. Next > Finish
* Repeat for all the plugins you are working on (Core, Classes and Permissions and PDS are the main ones)
* Next, Set up all the required dependencies, Maven should import most of them, but there may be some it doesn't catch. Everything requires Core as a dependency
* Go to File > Project Structure > Modules > MC_Custom_Classes > dependencies tab.
    * click the + and add a new module dependency for MC_Custom_Core (Note: MC_Custom_PDS requires Punishments as a dependency as well)
    * repeat with all plugins other than Core.
    * Add Prism and Libs Disguise for MC_Custom_Classes if they are not already
* Now setup your Artifacts, these are the JARs you are using for the plugins. Just click the Artifacts tab in Project Structure.
    1. Click the + add a jar > from module and dependencies > Select the module (plugin) you wish to create a JAR for.
        * In most cases you only need the Module's compiled output, but Maven likes to add extra stuff, you can keep those imports, or remove the extra ones to reduce the JAR size.
    2. MC_Custom_Core needs some extra maven dependencies added to the jar.
        * To add these, Expand the MC_Custom_Core folder in the "Available Elements" section on the right.
        * Right click on the module you need to add and select "Extract into output root"
        * You are going to need to add the following if they are not already there:
            * Gson (for json parsing)
            * HikariCP (for database connections)
            * Javassist (required by Hikari)
            * Reflections (for reflection actions such as autoloading listeners and commands)
            * SLF4J-api (required by Hikari)
            * SLF4J-nop (required by SLF4J-api)
    3. Set the output directory to wherever the plugin directory of your server is located. (ie Minecraft/server/plugins/)
    4. Check the box to "Build on Make"
* If there are any errors, makes sure you have all the dependencies, IntelliJ is usually helpful about which ones you are missing and how to add them.
