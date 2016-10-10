# Retter
Analyses the text messages to search for "to retter:" command, If found it interprets the command and executes the operation specified, It can be used to Toggle silent mode, Call back automatically as well as Toggle automatic silent mode etc, 
<p>Retter uses a *Message Recieving service* which runs in background whenever the app is started. This service analyses the incoming text messages
to check for the presence of "To Retter:" pattern, if the pattern is found the service checks for the valid command, the commands are listed at the end,
if the valid command is found, Command parser is invoked, which performs the operations according to the command.</p>
<p> The messages which does not contain "To Retter:" Command are not carried further for analysis, This commands can be included in
your text messages (SMS) either at the end, or in the middle or at the end, without dedicating a separate text message for the app.</p>
<p> The app is just for the demo purpose, hence it does not contain any UI elements, Just 2 simple buttons to start and stop the service</p>

<p>Some commands which can be included are as follows::</p>
<p> 1. on silent mode :: Turns on the silent mode of your phone, off silent mode:: Turns off the silent mode </p>
<p>2. automatic silent mode:: Toggles silent mode based on the optical density of the surroundings </p>
<p>3. call me:: Makes a fake phone call from the phone to which the message has been sent </p>

<p> Usage Example: Add the word "To Retter:" in your normal text message anywhere you want, and send it to a valid recipent using this app, followed by the command mentioned above , Example:: Hi, How are you?? To Retter: call me . When the recipent receives this message the app will make fake call to your phone </p>

<p> For more queries:: Email me @:: yogaimpression@gmail.com or narasimhaprasannahn@gmail.com </p>
