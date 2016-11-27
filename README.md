# TwitterClient

A simple twitter client app which allows you to login and list your followers and their details.

# Architecture
The app is built using the MVP (Mode-View-Presenter) pattern, which allow us
to build loosely coupled modules which are easy to test and scale upon.

# Persistence
For storing the data locally I used Realm because it greatly simplifies the
effort required to deal with persistence with great performance.

# Networking
For api calls I mainly used the TwitterKit provided apis and extended the
TwitterApiClient class to provide the missing api class.