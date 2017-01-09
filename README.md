# IoSynth [![Build Status](https://travis-ci.org/rradev/iosynth.svg?branch=master)](https://travis-ci.org/rradev/iosynth)[![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg)](http://www.gnu.org/licenses/lgpl-3.0)

IoSynth is IoT device/sensor simulator and synthetic data generator.

What is it useful for:
-	You develop a product and have no real IoT devices to connect.
-	You present a product to customers and want to show realistically looking demos.
-	You measure the performance of a product and require large-scale real test bed.
-	You develop a tests cases that require reproducible tests scenarios.
-	You frequently change experiment configurations and want to do it in cost and time effective way. 

### Usage
The MQTT protocol is based on the principle of publishing messages and subscribing to topics using MQTT broker. The AppMqtt client application connects to MQTT broker and publishes device/sensor data. 
 ```sh
 java -cp iosynth.jar net.iosynth.AppMqtt -c config.json -d devices.json
 ```
To run the above command get the [**latest release of iosynth.jar**](https://github.com/rradev/iosynth/releases).
It is also assumed that you have mqtt-config.json and devices.json in the same directory and Java 1.7 installed. 

 
You can check the published data by subscribing to MQTT topics using the [Mosquitto](http://mosquitto.org) client:

```sh
mosquitto_sub -v -t "iosynth/#" -h localhost -p 1883
```
 
The result should be something like this:
```sh
iosynth/lkjhgfdsa/device-y-09 {"time":"2017-01-07 10:20:50.369","command":"Echo","state":1,"level":3,"switch":"on"}
iosynth/lkjhgfdsa/device-x-08 {"time":"2017-01-07 10:20:50.886","count":179,"temp":-13.2980,"level":1.1000}
iosynth/lkjhgfdsa/device-y-01 {"time":"2017-01-07 10:20:52.285","command":"Bravo","state":2,"level":3,"switch":"on"}
iosynth/lkjhgfdsa/device-y-05 {"time":"2017-01-07 10:20:52.328","command":"Delta","state":0,"level":8,"switch":"on"}
iosynth/lkjhgfdsa/device-x-09 {"time":"2017-01-07 10:20:53.915","count":79,"temp":-1.0600,"level":8.3000}
iosynth/lkjhgfdsa/device-y-07 {"time":"2017-01-07 10:20:54.486","command":"Delta","state":2,"level":8,"switch":"off"}
iosynth/lkjhgfdsa/device-x-03 {"time":"2017-01-07 10:20:55.074","count":131,"temp":-2.9136,"level":1.1000}
iosynth/lkjhgfdsa/device-y-03 {"time":"2017-01-07 10:20:56.150","command":"Alfa","state":4,"level":2,"switch":"on"}
```

**config.json** - Configuration for the MQTT connection and global parameters.
```json
{
  "topic":"iosynth/",
  "qos":2,
  "broker":"tcp://localhost:1883",
  "session":"mdejsighzne",
  "seed":123456
}
```


|  Parameter    |  Description  |
| ------------:|:-------------|
| topic  | Prefix of the *MQTT topic*. Session string and device/sensor names create the rest part of the topic name. |
| qos      | Quality of Service: 0, 1, 2    |
| broker   | MQTT broker address: *tcp://host:port*   |
| session  | Unique string representing the session name used to create unique topics. If this parameter is omitted, session string is generated automatically on each run.|
| seed | Random Generator seed used to create reproducible scenarios. It can be ommited in other cases.

**devices.json** - Example configuration describing the devices and sensors.
```json
[
    {
        "type":"DeviceSimple",
        "uuid":"device-x-",
        "arrival":{"type":"ArrivalFixed", "interval":10000},
        "copy":10,
        "sensors":[
            {"type": "SensorDefault", "name": "count"},
            {"type": "SensorRandomDouble", "name": "temp", "min":-15, "max":3},
            {"type": "SensorCycleDouble", "name": "level", "values": [1.1,3.2,8.3,9.4]}
        ]
    },
    {
        "type":"DeviceSimple",
        "uuid":"device-y-",
        "arrival":{"type":"ArrivalUniform", "min":10000, "max":30000},
        "copy":10,
        "sensors":[
            {"type": "SensorCycleString", "name":"command", "values":["Alfa","Bravo","Charlie","Delta","Echo","Foxtrot"]},
            {"type": "SensorRandomInt", "name": "state", "min":0, "max":5},
            {"type": "SensorCycleInt", "name": "level", "values": [1,2,8,9,11,2,3,4]},
            {"type": "SensorCycleString", "name":"switch", "values":["on", "off"]}
        ]
    }
]
```


### Device Configuration

Device configuration is a Json file containing list of device definitions:
```json
[
	{ "uuid":"device1", "type":"..."},
	{ "uuid":"device2", "type":"..."},
	{ "uuid":"device3", "type":"..."},

]
```

Each device definition contains set of parameters and list of sensors:
```json
{
	"uuid":"...",
	"type":"...",
	"arrival":{},
	"copy":10,
	"sensors":[
		{"name":"sensor1", "type":"..."},
		{"name":"sensor2", "type":"..."},
		{"name":"sensor3", "type":"..."},

	]
}
```

|  Parameter    |  Description  |
| ------------:|:-------------|
| uuid  | Unique ID for each device. If empty, it will be auto generated. |
| type | Device type: DeviceSimple, ...    |
| arrival | Inter-arrival time configuration. Defines arrival time for device data as fixed interval (in microseconds) or interval defined by inter-arrival time distribution.|
| copy | If used, multiple copies of the same device are created with different uuid and varying parameters. |
| sensors | List of sensor definitions. |

Sensor definition is different for different types of sensors. For example:
```json
{
	"name":"temperature", 
	"type":"SensorRandomDouble", 
	"min":15, 
	"max":25
}
```

```json
{
	"name":"state", 
	"type":"SensorCycleString", 
	"values":["alpha", "bravo", "charlie", "delta"]
}
```



