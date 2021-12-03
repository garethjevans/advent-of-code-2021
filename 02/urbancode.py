#!/usr/bin/env python
# -*- coding: utf-8 -*-

def load(file):
  f = open(file, "r")
  data = []
  for x in f:
    data[len(data):] = [x]
  return data

def getTotal(commands):
  depth = 0
  horiz = 0

  for d in commands:
    if d.startswith('forward'):
      horiz = horiz + int(d.split()[1])

    if d.startswith('down'):
      depth = depth + int(d.split()[1])

    if d.startswith('up'):
      depth = depth - int(d.split()[1])

  return depth * horiz

def getAimTotal(commands):
  depth = 0
  horiz = 0
  aim = 0

  for d in commands:
    if d.startswith('forward'):
      horiz = horiz + int(d.split()[1])
      depth = depth + (aim*int(d.split()[1]))

    if d.startswith('down'):
      aim = aim + int(d.split()[1])

    if d.startswith('up'):
      aim = aim - int(d.split()[1])

  return depth * horiz


print(getTotal(load('test-data.txt')))
print(getAimTotal(load('test-data.txt')))

print(getTotal(load('real-data.txt')))
print(getAimTotal(load('real-data.txt')))


