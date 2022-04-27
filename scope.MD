# Back-End / Java Take-Home Assessment
Note: Complete this assessment within 3 business days.

We expect this assignment to take somewhere between 3 and 8 hours to complete. You are welcome to spend as much or as little time as you’d like on the assignment within the allotted time. Please focus your time building things that highlight your software development skills.

Reply to the email that sent this assessment with a ZIP File containing the contents of your project.  Alternatively, you may upload your code to a public code repository with clear instructions for downloading & running your project and respond to the email with a link to your public code repository.
Assessment Overview

# Overview
Use the OpenAQ-provided API to create a component that provides a heatmap of air quality data in your home country!

Much of the work performed by Opower product back-end engineering is building back-ends that create compelling and unique, data-driven insights.
We want to see what you can bring to our teams to help use data to create positive social change!  OpenAQ (https://openaq.org/) is a free resource that provides, via an OpenAPI specification (https://docs.openaq.org/#/), data points on air quality.  Using their data is a good baseline to build from, and a good representation of the kind of work you will be doing on a daily basis working with our teams.

# Main Task
Build a Java component that takes input and returns a data structure that can be consumed by a front-end team to build a Heat Map visualization of air quality in the given region (This website has some good examples of geographic heatmaps).  Your component should be able to handle the following inputs:

    Two-letter “Country Code” and Measured Parameter (pm25, co, no2, etc.)
    Decimal-Degree Coordinates & Radius and Measured Parameter (pm25, co, no2, etc.)

Based on the provided input, your component should be able to hit the live OpenAQ API and return a data structure that represents all the elements to construct a visual representation of the data in the form of a heatmap.  You will likely need to interact with the locations endpoint and / or the measurements endpoint (note that these are the v2 endpoints that we should use… please avoid the v1 endpoints).  Importantly, your code should return enough information to construct a color scale, and lay out the data on the map for the requested Measure Parameter.  To complete this assessment you will be expected to show:

    You can bootstrap a Java project that can be used to build running Java code
    You can properly handle and sanitize inputs to a system
    You can write code that interacts with a live API in a fault-tolerant manner
    You can properly parse data from a live API and transform it into a new data structure
    You can design a data structure with an eye to how a front-end component would consume the data to create a visual representation of the data

# Submission
Finally, because you are handing this component off to an independent user (the assessors in this case), we expect this component to be executable by a user of the application.  This is an exercise left to the author; you can build a CLI, wrap the component in a microservice with a clearly documented API, build a test harness with clear instructions on how to run it, or you can surprise us by doing something totally different!  Either way please be mindful that this is part of successfully completing the assessment; if we cannot run the code then we cannot judge the accuracy of your program.

You may find information on the data structure of openaq useful for this exercise.

After you’ve submitted your take-home, if you had pushed your changes to a github repo, please avoid rewriting git history of the master branch. This preserves the state of the code that’s submitted and reviewed by us.