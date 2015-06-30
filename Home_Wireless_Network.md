# Introduction #

Data format for the house telemetry


# Details #

The data sample from a sensor node is composed by:
```
struct {
    byte ID;
    byte TYPE;
    payload;     
} packet;
```

Well there's also a byte for the group but that one is not required.
The type basically define the payload structure:
```
TYPE:
#define ALARM 0x01
#define TELEMETRY 0x02
#define INERTIAL 0x04
```
The full payload format for a telemetry node is structured as:

```
struct {
    byte light;     // light sensor SHT11
    byte moved :1;  // motion detector for the EPIR
    byte humi  :7;  // humidity from the SHT11
    int temp   :10; // temperature from the SHT11
    int pressure :32; //pressure from the bmp085
    byte lobat :1;  // supply voltage dropped under 3.1V
} payload;
```
Telemety nodes can update in quasi real time (consumes a lot of energy) or in diff mode (lowe power). The first mode is a periodic sampling whereas the second mode only send packets when one of the sensor has changed.
Also every node has a subset of the payload data but every node has a lobat indicator (1 bit).

An inertial node has a different payload:

```
struct {
  byte Xraw:10;
  byte Yraw:10;
  byte Zraw:10;
  byte RESOLUTION: 4;
  byte SAMP: 8;
} payload;
```
every axis is an 10 bit integer.

## JSON Format ##
We are using the JSON format interchange between the coordinator and the webservice.
There could be more than 1 coordinator for every network, therefore the field IDNetwork identifies from which network the data was coming from.
The timestamp is a unique increasing long int number.

```
{
timestamp: 1456789,
id: 1,
type: xbee

sensors: [

    {

        id: 12,
        type: 'temp',
        location: 'Room A',
        value: 23.45

    },

    {

        id: 12,
        type: 'press',
        location: 'Room A',
        value: 1.53

    },

    ...

]

}
```

## Coordinator-webservice interschange protocol ##

To allow a secure data exchange between each coordinator and the web application, each coordinator will
be supplied with a couple of public and secret keys, that will be hexadecimal strings. The public key
will be used to identify the coordinator and private one will be used to sign the request so that the
server can be sure of the data origin. The private key will NEVER be transmitted over the net.

The web application will be reacheable through HTTP request. The URL will be something like:

http://robomotic.server.com/robomotic/storeData.action

This URL will accept the following parameters:

  * **public**: The public key of the coordinator;
  * **nonce**: A progressive number (long) that identifies the request. Each request must have a nonce that is GREATER than the one of the prevoius request in order to avoid replay attacks;
  * **data**: The sensors data formatted as a JSON object according to the format defined in previuos section;
  * **sign**: The request sign created as _**MD5(public + nonce + data + secret)**_, where + means string concatanation.

The parameters can be passed with a POST or with a GET HTTP command, indifferently.

As the nonce changes between each call and must be an incremental number, each request will be different, preventing replay attacks, and it will have a different sign.

Moreover, the secret key, shared between the coordinator and the server, will never be clearly transmitted over the network, so an attacher can very hardly generate a valid sign for a malicious request and the server can be pretty sure that the a request with a correct sign is coming from the coordinator knowing the secret key.