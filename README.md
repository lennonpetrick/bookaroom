# Book a room App

I decided to start the project from the bottom layer to the up layer. As I was creating the classes to build the layer I was also creating the tests to ensure every class would work perfectly. By checking the commit history, you can see how I created each piece of each layer.

Due to the short time and simplicity of the project, I tried to used the clean architecture approach, but missed the inversion of control and usecases for example.

For the DI setup I created an appcomponent to control all the singletons like repository, network and database objects and created a different component for the screen using a feature scope, this way every instance created by this component will live as long as the screen lives. It could be seen if the app had more than one screen for example.

To leverage the time I used some classes and parts of code from my previous projects that can be found in my GitHub.

If I had more time I would implement many things that is missing, for instance, a way to update the room that was booked by showing less spots remaing and changing the button to booked. I would have achieved it by creating a new room entity with the fields updated in the data source (simulating the api return) and returning all the way back to the view.
