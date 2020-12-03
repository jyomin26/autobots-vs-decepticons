# autobots-vs-decepticons

Created an Application for Creating, Updating, Deleting Transformers and to sattle which team wins the battel.

In order to run the app simply download the code or take clone

Feautres:
1.Used Rx2AndroidNetworking library for the API calls
2.Developed app using MVVM architecture
3.Used Picaso Library to load image

Rules:
1.Sort teams according to their ranks.
2.During face-off the transformer 
  a) With courage less than or equal to 4 and strength less than equal to 3 will loose
  b) Skill greater then or equal to 3 will win
  c) If overall rating is heigher then the opponent will win
  d) If tie then both will not be destroyed
  e) The transformer who do not have a opponent to fight will be skipped
3.Any transformer with name Optimus Prime or Predaking wins his fight automatically regardless of
any other criteria
4.The event either of the above face each other (or a duplicate of each other), the game
immediately ends with all competitors destroyed


