#Some Matlab code for ADC conversion of Accelerometers

# Introduction #

I just want to show some basic about the conversion process for analog acceleromters.
How do you calculate the actual g values from the 3 axis ADC?


# Matlab code #
```
The MCU works with a supply of 5V
Vcc=5
The gmax declared on the ADXL is +-2g
gMax=2
The ADC resolution of 10 bit gives us a quantization of:
adc_res=Vcc/1024;
The ADXL resolution is the Vcc divided by the max and min rage.
acc_res=Vcc/(2*gMax);

What we should expect from the ADXL ADC values:
xa=[512;512+1024/4;1024/4]
xa[1] is 0g
xa[2] is 1g
xa[3] is -1g
So we run our little function
[gval,voltage]=adc2g(xa,avg,adc_res,acc_res)


gval =

     0
     1
    -1


voltage =

         0
    1.2500
   -1.2500
```