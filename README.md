
## Drones Service REST API

### How to build

#### Requirements

- Java 8
- Java IDE (Intellij or any other editor)
- I have used an in-memory database (H2)
- Can use any REST client for testing eg Postman

### Testing the API
Assumptions Made: 

- Medication cannot be loaded to 2 different drones at the same time.

Steps to be taken on testing:
1. Register Drone and status becomes IDLE
2. Check it's availability for loading medication: all with status idle 
3. Load the drone with medication and status changes to loading after succeeding
4. Check medication items for a given drone
5. Check Battery level: this is critical, cannot load if batter is less than 25%
6. Deliver the medication and status changes to delivered

----
- **Registering a drone** 
REQUEST URL -: localhost:8080/api/drones
METHOD -: POST
REQUEST BODY TYPE -: application/json
REQUEST BODY:

{
	"serialNumber":"AE851527899TCTM",
	"model":"Samsung",
	"weightLimit":"600",
	"battery":"0.7",
	"state":"IDLE"
	
}

RESPONSE:
{
    "result": "Success",
    "serialNumber": "AE851527899TCTM",
    "message": "Drone created successfully",
    "timestamp": "2022-12-07T16:38:49.3444148"
}

---
- **Checking available drones for loading**
This check is a must before loading the drone

REQUEST URL -: localhost:8080/api/drones
METHOD -: GET
REQUEST BODY TYPE -: N/A
REQUEST BODY: N/A

RESPONSE:
{
    "status": "success",
    "timestamp": "2022-12-07T16:39:16.8761616",
    "drones": [
        {
            "serialNumber": "AE851527899TCTM",
            "model": "Samsung",
            "weightLimit": 600.0,
            "battery": 0.70,
            "state": "IDLE"
        }
    ]
}

---
- **Loading a drone with medication items;**  
REQUEST URL -: localhost:8080/api/drones/{droneSerialNumber}/medications/{medicationCode}
METHOD -: POST
REQUEST BODY TYPE -: application/json
REQUEST BODY: 
{
	"source":"Chitown",
	"destination":"Waterfalls"	
}

RESPONSE:
{
    "result": "success",
    "serialNumber": "AE851527899TCTM",
    "message": "Drone was Loaded successfully",
    "timestamp": "2022-12-07T16:39:32.3237062"
}


The payload will have the following fields

- droneSerialNumber - is the serial number of any of the available drones eg AE851527899TCTM
- medicationCode - Sample Medication was populated on running the application: Available codes are: **[TM210291, MAC210291, Champ210291, Aus2102914]
- source is the point of loading
- destination is where the load is being taken to

---
- **Checking loaded medication items for a given drone;**

EQUEST URL -: localhost:8080/api/drones/{droneSerialNumber}/medications
METHOD -: GET
REQUEST BODY TYPE -: N/A
REQUEST BODY: N/A

RESPONSE:
{
    "result": "success",
    "serialNumber": "AE851527899TCTM",
    "timestamp": "2022-12-07T16:39:48.9611637",
    "medication": {
        "code": "Champ210291",
        "name": "Asian Herbs",
        "weight": 34.0,
        "image": "champ_image.png"
    }
}
 Example droneSerialNumber for testing : **AE851527899TCTM**



- **Check drone battery level for a given drone;**
EQUEST URL -: localhost:8080/api/drones/{droneSerialNumber}
METHOD -: GET
REQUEST BODY TYPE -: N/A
REQUEST BODY: N/A

RESPONSE:
{
    "status": "success",
    "serialNumber": "AE851527899TCTM",
    "battery": "70%",
    "timestamp": "2022-12-07T16:39:10.128909"
}

Example droneSerialNumber for testing : **AE851527899TCTM**

---
- **Delivery of medication item**

EQUEST URL -: localhost:8080/api/drones/{droneSerialNumber}/medications
METHOD -: POST
REQUEST BODY TYPE -: N/A
REQUEST BODY: N/A

RESPONSE:
{
    "status": "success",
    "serialNumber": "AE851527899TCTM",
    "battery": "70%",
    "timestamp": "2022-12-07T16:39:10.128909"
}

Example droneSerialNumber for testing : **AE851527899TCTM**



