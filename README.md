# NewAir
## Goal
NewAir is an Android app that displays air data from Newcastle upon
Tyne, England. Using real-time sensors from Newcastle University's
Urban Observatory (see 2023 update below), it displays the air Pollution
(PM10 particles), Temperature, and Humidity from across the city.

For user convenience, the User Interface changes colour and provides a health
recommendation according to the pollution, signifying the severity
of pollution levels. The colours and messages are selected according to the 
Daily Air Quality Index from the Department for Environment Food & 
Rural Affairs, United Kingdom.

Data is displayed in text form for:
* Overall city, the average of all available sensors.
* Custom locations of interest, which users could add via interacting with 
a map. Data is displayed using the nearest sensor to the added location.

Data is also displayed in graphic form using a map. Sensor 
locations and their readings are displayed via coloured markers.

Additionally, history data is available for the averages of the last 7 days.
Displayed on a line graph.

_**All media available at /media/**_

![Screenshot of Home](/media/screenshots/home_default.png) 
![Screenshot of Map](/media/screenshots/map_default.png) 
![Screenshot of Graph](/media/screenshots/graph.png) 

![Video of Home available at /media/demos/](/media/demos/home.mp4)

![Video of Map available at /media/demos/](/media/demos/map.mp4)

Users could enable a colourblind pallette from Settings.

![Screenshot of colourblind Home](/media/screenshots/home_colourblind.png) 
![Screenshot of colourblind Map](/media/screenshots/map_colourblind.png)

## 2023 Update
* Migrated to Kotlin
* Data flow remade, now using ViewModel and LiveData
* Navigation remade, now using Navigation Component. Bottom navigation bar
now only visible in Home, deemed unnecessary elsewhere
* Downloads now via Kotlin Coroutines
* UI elements revisited, assets migrated to Vector graphics
* GPS and Distance to nearest sensor deemed unnecessary and removed
* Sensor data now **local and randomized**, making the app a Proof of Work.
Downloading data from Newcastle Urban Observatory too slow to use worldwide

## Authors
### Initial
First developed in 2018 in Newcastle University

* Lead programmer: Vladislav Iliev
* Art direction: V. Godsell
* Technical components
	* Graph library provided by: I. Gylaris
	* GPS library provided by: L. Stannard
* QA: B. Wilsher, I. Watt

### Ongoing
Maintained since by Vladislav Iliev
