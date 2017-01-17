# IoSynth  [![Build Status](https://travis-ci.org/rradev/iosynth.svg?branch=master)](https://travis-ci.org/rradev/iosynth) [![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg)](http://www.gnu.org/licenses/lgpl-3.0)

IoSynth is IoT device/sensor simulator and synthetic data generator.

What is it useful for:
-	You develop a product and have no real IoT devices to connect.
-	You present a product to customers and want to show realistically looking demos.
-	You measure the performance of a product and require large-scale real test bed.
-	You develop a tests cases that require reproducible tests scenarios.
-	You frequently change experiment configurations and want to do it in cost and time effective way. 

### Usage
Currently supported protocols:
  - MQTT 
  - RabbitMQ (AMQP)
  
```sh 
java -cp iosynth.jar net.iosynth.Mqtt -c config-mqtt.json -d devices.json
```
**config-mqtt.json** - this file contains the MQTT client configuration.
**devices.json** - this file contains configuration for individual devices and additional json template files. 
Check the [**config**](tree/master/config) directory for example configuration files. 

To run the above command get the [**latest release of iosynth.jar**](https://github.com/rradev/iosynth/releases).
It is also assumed that you have  Java 1.7 installed.


Example **config-mqtt.json** configuration:
```json
{
	"uri": "tcp://localhost:1883",
	"topic": "iosynth/",
	"qos": 2,
	"clients": 1,
	"seed": 123456
}
```

|  Parameter    |  Description  |
| ------------:|:-------------|
| uri   | **tcp://host[:port]**  - MQTT broker address |
| topic  | Prefix of the *MQTT topic* that together with the device *topic* create the full topic name |
| clients | Number of MQTT clients. Each client sends the data from selected devices only. | 
| qos      | Quality of Service: 0, 1, 2    |
| seed | Random Generator seed used to create reproducible scenarios. It can be omitted in other cases.|

### devices.json
devices.json - configuration is a json file containing list of device definitions:
```json
[
	{ "sdid":"device1", "type":"..."},
	{ "sdid":"device2", "type":"..."},
	{ "sdid":"device3", "type":"..."},

]
```
Devices configuration may have only list of sensors that result in simple payload or may have external json file that produces complex json payload.

Example **devices.json** file:
```sh
[
    {
        "type":"Simple",
        "sdid":{"type":"String", "value":"dev"},
        "topic":"device/{$sdid}",
        "sampling":{"type":"Fixed", "interval":10000},
        "copy":5,
        "sensors":[
            {"type":"Timestamp",    "name":"ts"},
            {"type":"sdid",         "name":"sdid"},
            {"type":"DoubleRandom", "name":"temp", "min":-15, "max":3}
        ]
    },
    {
        "type":"Simple",
        "sdid":{"type":"MAC48"},
        "topic":"device/{$sdid}",
        "sampling":{"type":"Uniform", "min":3000, "max":6000},
        "copy":5,
        "sensors":[
            {"type":"Timestamp",    "name":"ts"},
            {"type":"sdid",         "name":"sdid"},
            {"type":"StringRandom", "name":"level", "values": ["a","b","c","d","e","f"]}
        ]
    }
]
```
This file define two types of devices with the following topics and payloads 
```sh
iosynth/device/dev000002 
{"ts":"2017-01-17T20:12:47.876+0200","sdid":"dev000002","temp":-8.2032}

iosynth/device/46:FA:D5:7C:AB:E1
{"ts":"2017-01-17T20:12:45.498+0200","sdid":"46:FA:D5:7C:AB:E1","level":"b"}
```

|  Parameter    |  Description  |
| ------------:|:-------------|
|"type" | Device type. Currently all provided devices are of "Simple" type.|
|"sdid" | Device id.|
|"topic"| String forming the MQTT topic name. May contain {$sdid} that will be replaced with current device "sdid".|
|"sampling" | Device data sampling interval. Time parameters are in milliseconds.|
|"copy" | number of replicas of this device. The above configuration defines 5 + 5 = 10 device replicas.|
|"out_of_order"| Double number [0..1.0] that sets the probability for out-of-order messages.|
|"message_loss"| Double number [0..1.0] that sets the probability for message loss.|
|"sensors" | list of sensor configurations.|

The above device configuration provides simple payload of the form:
```sh
{ "a":a, "b":b, "c":c, .....}
```
The below device configuration **devices.json** defines complex json payload using additional template file:
```sh
[
    {
        "type":"Simple",
        "sdid":{"type":"MAC48"},
        "topic":"device/{$sdid}/out/stream",
        "sampling":{"type":"Fixed", "interval":10000},
        "copy":10,
        "json_template":"template.json",
        "sensors":[
            {"type":"sdid",      "name":"{$sdid}"},
            {"type":"IntRandom", "name":"{$light_value}",  "min":0, "max":300000},
            {"type":"IntRandom", "name":"{$temp_value}",  "min":20000, "max":35000},
            {"type":"IntRandom", "name":"{$pressure_value}",  "min":30000, "max":110000},
            {"type":"IntRandom", "name":"{$humidity_value}",  "min":10, "max":90}
        ]
    }
]
```
where the **template.json** file is:
```sh
{
        "sn": "{$sdid}",
        "data": {
                "light": {
                        "value": "{$light_value}",
                        "unit": "mLux"
                },
                "temp": {
                        "value": "{$temp_value}",
                        "unit": "mCelsius"
                },
                "pressure": {
                        "value": "{$pressure_value}",
                        "unit": "Pascal"
                },
                "humidity": {
                        "value": "{$humidity_value}",
                        "unit": "%rh"
                }
        }
}
```

The sensors in the devices.json use {$var} in name to indicate what variables are replaced in template.json.
Resulting topic and payload looks like this:
```sh
iosynth/device/46:FA:D5:7C:AB:E1/out/stream 
{
	"sn": "46:FA:D5:7C:AB:E1",
	"data": {
		"light": {
			"value": 254864,
			"unit": "mLux"
		},
		"temp": {
			"value": 30539,
			"unit": "mCelsius"
		},
		"pressure": {
			"value": 72157,
			"unit": "Pascal"
		},
		"humidity": {
			"value": 12,
			"unit": "%rh"
		}
	}
}

```

**sdid**

|  Type    | Parameters | Description  |
| ------------:|--------:|:-------------|
|String| value | Simple string value + auto-incremented number for each device replica|
|UUID| | Universally unique identifier|
|MAC48|| MAC address|
|MAC64|| MAC address|

**sampling**

|  Type    | Parameters | Description  |
| ------------:|--------:|:-------------|
|Fixed| interval | Fixed interval sampling in milliseconds |
|Uniform| min, max| Sampling intervals with uniform distribution.|

**Sensor types**

All sensors have "name" parameter and optional "format" parameter. The "format" parameter defines value formatting according to java.lang.String.format rules.

|  Type    | Parameters | Description  |
| ------------:|--------:|:-------------|
|sdid|| Shows the device "sdid"|
|String|value| Shows fixed string value|
|Timestamp|| Curent device timestamp|
|DoubleCycle|values| Cycle values from list|
|DoubleRandom|min, max| Random values between min and max|
|IntCycle|values|Cycle values from list|
|IntRandom|min, max|Random values between min and max|
|StringCycle|values|Cycle values from list|
|StringRandom|values|Random value from list|

