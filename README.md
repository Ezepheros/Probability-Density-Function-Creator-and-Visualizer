# Probability Density Function Creator and Visualizer

## Project Overview
This application will allow a user to create their own **probability mass function**(*pmf*). After creating their *pmf*, users will be able to generate random numbers which follow their *pmf*. Later on, they will be able to draw their own pmf and generate points with their *pmfs*. Statistics enthusiasts, students and researchers may want to use this tool to visualize typical results from custom probability distributions. It can also be used by someone who wants to make a decision with an abnormal *pmf* to generate random numbers.I am personally interested in this type of tool because I am a stats enthusiast and I like playing around with probability related stuff to observe variance and typical behaviours. I am also interested in Machine Learning as a field and that uses a lot of statistics related methods.

## User Stories
- As a user I want to be able to assign probabilities to certain numeric values and add those pairs to a *pmf*
- As a user I want to generate random numbers which follow the *pmf* that I created
- As a user I want to generate random numbers using odds as well as probabilities
- As a user I want to be able to delete entries in my *pmf*

## Additional User Stories
- As a user I want to be able to save all the *pmfs* that I have made
- As a user I want to be able to load the *pmfs* that I have made and be able to generate random numbers from them

## EventLog Sample:
```
Fri Nov 26 10:56:20 PST 2021
Created new pmf chart
Fri Nov 26 10:56:25 PST 2021
added new pair: f(1.0) = 2.0
Fri Nov 26 10:56:27 PST 2021
added new pair: f(2.0) = 2.5
Fri Nov 26 10:56:32 PST 2021
added new pair: f(3.0) = 7.0
Fri Nov 26 10:56:37 PST 2021
added new pair: f(4.0) = 4.0
Fri Nov 26 10:56:39 PST 2021
Generated 500 random values
Fri Nov 26 10:56:40 PST 2021
Generated 500 random values
Fri Nov 26 10:56:41 PST 2021
Saved pmf chart
Fri Nov 26 10:56:44 PST 2021
Created new pmf chart
Fri Nov 26 10:56:49 PST 2021
Loaded pmf chart
Fri Nov 26 10:56:49 PST 2021
Generated 200 random values
```

## Phase 4: Task 3
- I would remove the ProbabilityPair class and use a HashMap<Double,Double> in pmf instead of List<ProbabilityPair>
  because ProbabilityPair is acting like a map
- I think I would like to connect the ProbabilityMassFunction class and the BarChart class because the way they're
  implemented, they are quite connected. So I would probably do it by creating an association where 
  ProbabilityMassFunction contains a List<BarChart> and passes its list of probability pairs/mappings to the BarCharts
  to change them. I would also use the observer pattern because every time the PMF changes, the bar chart changes so the pmf would be an observable while the barchart
  would be an observer.


