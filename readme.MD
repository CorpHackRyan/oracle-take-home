# Back-End / Java Take-Home Assessment

# Table of contents
* [Overview](#overview)
* [Scope](#scope)
* [Setup](#setup)
* [Usage](#usage)
* [Output](#output)

# Overview
<img src="favicon.png"><br>
Simple Java app created using the Java Swing frame work that consumes data via an API endpoint from OpenAQ.org and 
converts that data to a  CSV format. This CSV is to be consumed by a front-end developer for a consumption into a heat 
map visualization. 

# Scope
Use the OpenAQ-provided API to create a component that provides a heatmap of air quality data in your home country!

OpenAQ (https://openaq.org/) is a free resource that provides, via an OpenAPI specification 
(https://docs.openaq.org/#/), data points on air quality. Using their data is a good baseline to build from, and a 
good representation of the kind of work you will be doing on a daily basis working with our teams.

Build a Java component that takes input and returns a data structure that can be consumed by a front-end team to build 
a Heat Map visualization of air quality in the given region. Your component should be able to handle the following 
inputs:

```Two-letter “Country Code” and Measured Parameter (pm25, co, no2, etc.)```

```Decimal-Degree Coordinates & Radius and Measured Parameter (pm25, co, no2, etc.)```

Based on the provided input, your component should be able to hit the live OpenAQ API and return a data structure 
that represents all the elements to construct a visual representation of the data in the form of a heatmap. You will 
likely need to interact with the locations endpoint and / or the measurements' endpoint (note that these are the v2 
endpoints that we should use… please avoid the v1 endpoints). Importantly, your code should return enough information 
to construct a color scale, and lay out the data on the map for the requested Measure Parameter. 


# Setup
Requires json simple 1.1.1

# Usage
If you wish to execute the stand-alone JAR file, you can download it [HERE.](https://github.com/CorpHackRyan/oracle-take-home/blob/master/out/artifacts/oracle_take_home_jar/oracle-take-home.jar)

# Output
<img src="assets/mainScreen.png" alt="Main screen">