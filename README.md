#SlackIntegration
SlackIntegration is a plugin created by Chris Bitler for RITCraft to allow people in our server slack chat to talk to the users online and vice versa.

##Compilation:
All you need to do to compile this plugin is to have maven installed and run mvn package, assuming you don't have a firewall blocking any of the dependencies
Alternatively, you can download it from our jenkins: http://vwserver.student.rit.edu:8080/job/SlackIntegration/lastSuccessfulBuild/
##Configuration:
There are only two configuration values, that you will be asked to change once you start the plugin the first time, these are the bot api token and the ID of the channel you want to use as a relay.
To get the bot api token, you have to create a slack bot and it will give you the api token for the bot.
To get the channel ID, you should visit https://www.slack.com/api/rtm.start?token=[bottoken] in your browser, replacing [bot token] with your bot's token, and look for the channel name that you want to use as the relay channel, it should have the ID along with the name.

##Credits:
Spigot project for making plugins like this possible
Tyrus project from glassfish for the websocket client
Slack for making the amazing team management tool that they have.
Json-simple for making a tool that allows us to easily parse the JSON returned from Slack
