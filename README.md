Conference planner
==================

About
-----
This project helps to plan conference schedules via [OptaPlanner](http://www.optaplanner.org/).


Installation
------------

Download a jar from releases, and execute as follows:

    java -jar planner-core-x.y.x.jar

Input data
----------

The input data is YAML of the following structure:

```yaml
capacity: 400
languages:
- name: ru
- name: en
rooms:
- name: 1
  capacity: 300
- name: 2
  capacity: 200
days:
- name: 1
  date: 2017-10-04
timeslots:
- name: 1
  day: 1
  start: 11:00
  duration: 50
- name: 2
  day: 1
  start: 14:00
  duration: 50
- name: 3
  day: 1
  start: 18:00
  duration: 50
topics:
- name: Case study
- name: Tricks
speakers:
- name: Speaker A
  arriveTime: 2017-10-04T12:00:00+03:00
- name: Speaker B
  leaveTime: 2017-10-04T16:00:00+03:00
talks:
- name: 'How to arrive late'
  language: en
  speakers: Speaker A
  topics: Tricks
- name: 'How to depart early'
  language: ru
  speakers: Speaker B
  topics: Tricks
- name: 'Coffee time'
  language: en
  speakers:
  - Speaker A
  - Speaker B
  topics:
  - Case study
  - Tricks
```

License
-------
Apache 2 License

Author
------
Vladimir Sitnikov <sitnikov.vladimir@gmail.com>
