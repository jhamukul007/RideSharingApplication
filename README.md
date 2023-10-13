# RideSharingApplication
Design a ride-sharing application.

## Requirements
The application allows users to share rides on a route. <br/>

Users can either offer a shared ride (Driver) or consume a shared ride (Passenger).<br/>
Users can search and select one from multiple available rides on a route with the same source and destination.<br/>

Use cases:<br/>

The application should allow registered users<br/>
create_user(name:"John",90900900, gender:M,age:26)<br/>
Users should be able to add vehicles (a user can have multiple vehicles)<br/>
create_vehicle(name: "John", vehicle: "swift", regNo: "KA-09-32321")<br/>

Users should be able to offer rides<br/>
offer_ride(name: "John", vehicle:"swift",seats_available:3, origin:Bangalore, destination:Mysore, start:Jan 25, 2021, 8AM, duration:3h)<br/>
Users should be able to select rides based on origin, destination, and selection preference.<br/>
user can have a preference like earliest ending ride or lowes duration ride<br/>

select_ride(name:"smith",origin:bangalore,destination:mysore,seats_requierd:2,earliest_ending_ride)<br/>

Users should be able to get all the rides offered and taken by the user.<br/>
Bonus: should be able fetch rides if direct rides are not available, but possible through multiple transit rides, should get multiple rides.<br/>

