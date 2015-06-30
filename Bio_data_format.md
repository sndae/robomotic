# Introduction #

This document describes the data format for biological signals
Terminology:
  1. EEG= Electroencephalography
  1. ECG= Electrocardiography
  1. MYO= Myography
  1. Polar= Polar rate signal
  1. EOG= electro-oculographic


# Data labeling #
Every recording session should contain:
  1. Subject name
  1. Type of recording
  1. Type of task
  1. Type of device used
  1. Properties of the device used like number of channels, sensibility etc.
  1. Timestamp and session duration
  1. Access token
  1. Link to related datasets
  1. Privacy agreement

# Details #
The data format for every channel follows:
```

struct BrainPacket
{
byte signal_strength,
byte attention,
byte meditation,
int delta,
int theta,
int low alpha, 
int high alpha,
int low beta, 
int high beta, 
int low gamma, 
int high gamma

}
```