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
Check the [**config**](https://github.com/rradev/iosynth/tree/master/config) directory for example configuration files. 

To run the above command get the [**latest release of iosynth.jar**](https://github.com/rradev/iosynth/releases).
It is also assumed that you have  Java 1.7 installed.

If you want to build from sources:

```sh
git clone https://github.com/rradev/iosynth.git
cd iosynth
mvn package
```

The resulting *.jar file is created in "target" directory.
 


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
| `uri`   | **tcp://host[:port]**  - MQTT broker address |
| `topic`  | Prefix of the *MQTT topic* that together with the device *topic* create the full topic name |
| `clients` | Number of MQTT clients. Each client sends the data from selected devices only. | 
| `qos`      | Quality of Service: 0, 1, 2    |
| `seed` | Random Generator seed used to create reproducible scenarios. It can be omitted in other cases.|

### devices.json
devices.json - configuration is a json file containing list of device definitions:
```json
[
	{ "sdid":"device1", "type":"..."},
	{ "sdid":"device2", "type":"..."},
	{ "sdid":"device3", "type":"..."},

]
```
Each device may have UUID, IP or MAC address. They can be used as part of the MQTT topic or as part of the json message payload.
There are two types of sensors configuration. The first one result in simple json payload. 
The second one uses json template file and results in complex json payload.

Example of simple **devices.json** file:
```json
[
    {
        "type":"simple",
        "uuid":"dev%04d",
        "topic":"device/{$uuid}",
        "sampling":{"type":"fixed", "interval":10000},
        "copy":2,
        "sensors":[
            {"type":"dev.timestamp",    "name":"ts"},
            {"type":"dev.uuid",         "name":"uuid"},
            {"type":"double_walk", "name":"temp", "min":-15, "max":3}
        ]
    },
    {
        "type":"simple",
        "mac48":"",
        "topic":"device/{$mac48}",
        "sampling":{"type":"uniform", "min":3000, "max":6000},
        "copy":2,
        "sensors":[
            {"type":"dev.timestamp",    "name":"ts"},
            {"type":"dev.mac48",        "name":"mac48"},
            {"type":"string",       "name":"level", "random": ["a","b","c","d","e","f"]}
        ]
    }
]
```
This configuration defines two types of devices, each of them replicated 2 times.


First device:
  - The device samples data on fixed time interval 10s.
  - It defines "uuid" with pattern "devxxxx", where xxxx is increased number for each replica.
  - The device publishes data using topic "device/devxxxx".
  - The device have 3 sensor:the device timestamp, the device uuid and double random variable.
  

Second device is almost the same, except it samples data with variable interval and sets MAC address instead of UUID.



The resulting payload looks like this:
 
```sh
iosynth/device/AA:16:4C:49:00:1D {"ts":"2017-01-21T14:24:32.134+0200","mac48":"AA:16:4C:49:00:1D","level":"c"}
iosynth/device/AA:16:4C:49:00:1C {"ts":"2017-01-21T14:24:33.163+0200","mac48":"AA:16:4C:49:00:1C","level":"f"}
iosynth/device/dev0001 {"ts":"2017-01-21T14:24:33.186+0200","uuid":"dev0001","temp":-10.5036}
iosynth/device/AA:16:4C:49:00:1D {"ts":"2017-01-21T14:24:37.744+0200","mac48":"AA:16:4C:49:00:1D","level":"f"}
iosynth/device/AA:16:4C:49:00:1C {"ts":"2017-01-21T14:24:38.485+0200","mac48":"AA:16:4C:49:00:1C","level":"a"}
iosynth/device/dev0000 {"ts":"2017-01-21T14:24:39.079+0200","uuid":"dev0000","temp":-0.9713}
iosynth/device/AA:16:4C:49:00:1D {"ts":"2017-01-21T14:24:41.714+0200","mac48":"AA:16:4C:49:00:1D","level":"d"}
iosynth/device/AA:16:4C:49:00:1C {"ts":"2017-01-21T14:24:42.634+0200","mac48":"AA:16:4C:49:00:1C","level":"b"}
iosynth/device/dev0001 {"ts":"2017-01-21T14:24:43.186+0200","uuid":"dev0001","temp":-10.5112}
iosynth/device/AA:16:4C:49:00:1D {"ts":"2017-01-21T14:24:46.616+0200","mac48":"AA:16:4C:49:00:1D","level":"d"}
iosynth/device/AA:16:4C:49:00:1C {"ts":"2017-01-21T14:24:47.386+0200","mac48":"AA:16:4C:49:00:1C","level":"b"}
iosynth/device/dev0000 {"ts":"2017-01-21T14:24:49.078+0200","uuid":"dev0000","temp":-0.9029} 

```

|  Parameter    |  Description  |
| ------------:|:-------------|
|`type` | Device type. Currently all provided devices are of "simple" type.|
|`uuid`, `ipv4`, `mac48`, `mac64` | Device uuid, ipv4, mac addresses. (optional)|
|`topic`| String forming the MQTT topic name. May contain variables {$uuid}, {$ipv4}, ... that are replaced with the current device values.|
|`sampling` | Device data sampling interval. Time parameters are in milliseconds.|
|`copy` | number of replicas for each device.|
|`out_of_order`| Double number [0..1.0] that sets the probability for out-of-order messages. (optional)|
|`message_loss`| Double number [0..1.0] that sets the probability for message loss. (optional)|
|`sensors` | list of sensor definitions.|

The above device configuration provides simple payload of the form:
```sh
{ "a":a, "b":b, "c":c, .....}
```
The below device configuration **devices.json** defines complex json payload using additional template file:
```json
[
    {
        "type":"simple",
        "mac48":"",
        "topic":"device/{$mac48}/out/stream",
        "sampling":{"type":"Fixed", "interval":10000},
        "copy":10,
        "json_template":"template.json",
        "sensors":[
            {"type":"dev.mac48",   "name":"{$mac48}"},
            {"type":"IntWalk", "name":"{$light_value}",     "min":0,     "max":300000},
            {"type":"IntWalk", "name":"{$temp_value}",      "min":20000, "max":35000},
            {"type":"IntWalk", "name":"{$pressure_value}",  "min":30000, "max":110000},
            {"type":"IntWalk", "name":"{$humidity_value}",  "min":10,    "max":90}
        ]
    }
]
```
where the **template.json** file is:
```json
{
        "sn": "{$mac48}",
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

iosynth/device/AA:16:4C:49:00:23/out/stream


```json 
{
        "sn": "AA:16:4C:49:00:23",
        "data": {
                "light": {
                        "value": 152698,
                        "unit": "mLux"
                },
                "temp": {
                        "value": 26588,
                        "unit": "mCelsius"
                },
                "pressure": {
                        "value": 101330,
                        "unit": "Pascal"
                },
                "humidity": {
                        "value": 88,
                        "unit": "%rh"
                }
        }
}

```
### Device Identificators

|  Type    | Description  |
| ------------:|:-------------|
|`uuid`| Generates universally unique identifier for each device if empty string is provided "", or increasing identificator if format pattern is provided "xxxxx%06d" |
|`mac48`| Generates MAC address. Auto-incremented for each device. If string is not empty it is used as prefix: "EE:00:" |
|`mac48`| Generates MAC address. Auto-incremented for each device. If string is not empty it is used as prefix: "EE:00:" |
|`ipv4` | Generates ipv4 address. Auto-incremented for each device. If string is not empty it is used as prefix: "123.123." |

### Sampling types

|  Type        | Parameters | Description  |
| ------------:|-----------:|:-------------|
|`fixed`| interval | Fixed interval sampling in milliseconds |
|`uniform`| min, max | Sampling intervals with uniform distribution.|
|`normal`| mean, stdev | Sampling intervals with Normal distribution with mean and standard deviation in milliseconds. |
|`exp`| beta | Sampling intervals with Exponential distribution with beta in milliseconds. |

### Sensor types

All sensors have "name" parameter and some have optional "format" parameter. 
The "format" parameter defines value formatting according to java.lang.String.format rules or java.text.SimpleDateFormat rules.
Sensors starting with "dev." show current device state ("dev.uuid", "dev.timestamp", "dev.epoch", ...). 
The rest a value generator sensors ("uuid", "string", "double_normal", ...). 

**`dev.topic`**

This sensor shows the device topic.


**`dev.uuid`, `dev.ipv4`, `dev.mac48`, `dev.mac64`**

This sensor shows the device uuid, ipv4, mac48, mac64 identificators.


**`dev.epoch`**

This sensor shows the internal device epoch counter (increasing number from 1).

**`dev.timestamp`**

Current device timestamp. 
Optional parameter "format" specifies the date-time format.


Example:
```json
{"type":"dev.timestamp",   "name":"ts", "format":"yyyy-MM-dd'T'HH:mm:ss.SSSZ"}
```

**`uuid`**

This sensor generates random UUID.


Example:
```json
{"type":"uuid", "name":"UUID"}
```

**`ipv4`**

This sensor generates random IPv4 addresses.
Optional parameter "prefix" specifies fixed prefix.


Example:
```json
{"type":"ipv4", "name":"IP", "prefix":"222."}
```

**`mac48`**

This sensor generates random MAC48 addresses.
Optional parameter "prefix" specifies fixed prefix.


Example:
```json
{"type":"mac48", "name":"MAC", "prefix":"EE:00:"}
```

**`mac64`**

This sensor generates random MAC64 addresses.
Optional parameter "prefix" specifies fixed prefix.


Example:
```json
{"type":"mac64", "name":"MAC", "prefix":"EE:00:"}
```

**`string`**

This sensor generate strings from list of strings or alphabet.
  - "cycle" - Cycle the values from list of strings.
  - "random" - Selects random string from list of strings.
  - "min", "max", "alphabet" - generates random string with size between [min, max) using the "alphabet". 


Example:
```json
{"type":"string", "name":"string", "cycle":["aaa", "bbb", "ccc"]}
```

Result:
```sh
iosynth/dev00 {"string":"bbb"}
iosynth/dev00 {"string":"ccc"}
iosynth/dev00 {"string":"aaa"}
iosynth/dev00 {"string":"bbb"}
iosynth/dev00 {"string":"ccc"}
iosynth/dev00 {"string":"aaa"}

```

Example:
```json
{"type":"string", "name":"string", "random":["aaa", "bbb", "ccc"]}
```

Result:
```sh
iosynth/dev00 {"string":"aaa"}
iosynth/dev00 {"string":"aaa"}
iosynth/dev00 {"string":"ccc"}
iosynth/dev00 {"string":"bbb"}
iosynth/dev00 {"string":"aaa"}
iosynth/dev00 {"string":"ccc"}
iosynth/dev00 {"string":"aaa"}
```

Example:
```json
{"type":"string", "name":"string", "min":5, "max":8, "alphabet":"abcdefghijk"}
```

Result:
```sh
iosynth/dev00 {"string":"akjcg"}
iosynth/dev00 {"string":"fgbbke"}
iosynth/dev00 {"string":"fkehj"}
iosynth/dev00 {"string":"fkjdgh"}
iosynth/dev00 {"string":"dcggia"}
iosynth/dev00 {"string":"ebcefdb"}
iosynth/dev00 {"string":"djdeffe"}
```

**`timestamp`**

This sensor generates random timestamp.
Parameters:
  - "from" timestamp in ISO format "yyyy-MM-dd'T'HH:mm:ssZ".
  - "to" timestamp in ISO format "yyyy-MM-dd'T'HH:mm:ssZ". If not set, the device creation time is used.
  - "locale" use locale (optional) to format generated timestamps. One of the supported JVM locales.
  - "format" timestamp format in Java SimpleDateFormat. If "format" is "s" or "ms", returns timestamp in seconds or milliseconds since January 1, 1970 UTC. 
  
Example:
```json
{"type":"timestamp",  "name":"date", "from":"2000-01-01T11:50:23+0000", "to":"2016-01-01T11:50:23+0000", "locale":"ko_KR", "format":"E, yyyy MM d"}
```

Result:
```sh
iosynth/device {"date":"목, 2008 10 23"}
iosynth/device {"date":"목, 2009 08 6"}
iosynth/device {"date":"화, 2015 08 4"}
iosynth/device {"date":"월, 2004 12 6"}
iosynth/device {"date":"월, 2012 10 29"}
iosynth/device {"date":"수, 2013 09 25"}

```



**`boolean`**

This sensor generates random boolean value (true, false).
Optional parameter "success" - likelihood of success. (0.0 ... 1.0)
Example:
```json
{"type":"boolean", "name":"enabled", "success":0.1}
```



**`double_cycle`**

Cycle double "values" provided as array.

Example:
```json
{"type":"DoubleCycle", "values":[1,2,3,4,5]}
```

**`double_walk`**

Random walk between "min" and "max" with "step" and initial "state".

Example:
```json
{"type":"double_walk", "min":23, "max":34, "state":24, "step":1}
```

**`double_uniform`**

Values between "min" and "max" from Uniform random generator.

```json
{"type":"double_uniform", "min":23, "max":34}
```

**`double_normal`**

Values from Normal (Gaussian) random distribution with "mean" and "stdev" parameters. 

```json
{"type":"double_normal", "mean":10, "stdev":0.5}
```

**`double_exp`**

Values from Exponential random distribution with "beta" parameter

```json
{"type":"double_exp", "beta":10}
```

**`int_cycle`**

Cycle int (long) "values" provided as array.

Example:
```json
{"type":"int_cycle", "values":[1,2,3,4,5]}
```

**`int_walk`**

Random walk between "min" and "max" with "step" and initial "state".

Example:
```json
{"type":"int_walk", "min":23, "max":34, "state":24, "step":1}
```

**`int_uniform`**

Values between "min" and "max" from Uniform random generator.

```json
{"type":"int_uniform", "min":23, "max":34}
```





**`country`**

This sensor generates random country names. Accepts optional "locale" parameter with one of supported JVM locales.

Example:
```json
{"type":"country", "name":"country"},
{"type":"country", "name":"国家", "locale":"zh_CN"}
```
Result:
```sh
iosynth/device {"country":"Isle Of Man","国家":"阿根廷"}
iosynth/device {"country":"Romania","国家":"立陶宛"}
iosynth/device {"country":"Norway","国家":"圣巴泰勒米岛"}
iosynth/device {"country":"Trinidad and Tobago","国家":"南乔治亚岛和南桑德韦奇岛"}
iosynth/device {"country":"Belarus","国家":"洪都拉斯"}
iosynth/device {"country":"Equatorial Guinea","国家":"冰岛"}
iosynth/device {"country":"Guinea-Bissau","国家":"蒙古"}

```











